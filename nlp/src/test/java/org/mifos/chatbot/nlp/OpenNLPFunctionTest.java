/**
 * Copyright 2018 Dingfan Zhao
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mifos.chatbot.nlp;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import org.junit.Test;
import org.junit.Assert;

import java.io.*;

@Slf4j
public class OpenNLPFunctionTest {
    @Test
    public void detectSentenceTest() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/models/en-sent.bin");
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String input = "Hi. How are you? This is mike. ";
        String sentences[] = sdetector.sentDetect(input);
        for(String sentence : sentences) {
            log.info(sentence);
        }
        is.close();

        Assert.assertEquals(sentences[0], "Hi. How are you?");
        Assert.assertEquals(sentences[1], "This is mike.");
    }

    @Test
    public void tokenizeTest() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);

        String input = "Hi. How are you? This is mike. ";
        String tokens[] = tokenizer.tokenize(input);
        for (String a : tokens)
            log.info(a);

        is.close();

        Assert.assertTrue("Hi".equals(tokens[0]));
    }

    /*
     *   CC Coordinating conjunction
     *   CD Cardinal number
     *   DT Determiner
     *   EX Existential there
     *   FW Foreign word
     *   IN Preposition or subordinating conjunction
     *   JJ Adjective
     *   JJR Adjective, comparative
     *   JJS Adjective, superlative
     *   LS List item marker
     *   MD Modal
     *   NN Noun, singular or mass
     *   NNS Noun, plural
     *   NNP Proper noun, singular
     *   NNPS Proper noun, plural
     *   PDT Predeterminer
     *   POS Possessive ending
     *   PRP Personal pronoun
     *   PRP$ Possessive pronoun
     *   RB Adverb
     *   RBR Adverb, comparative
     *   RBS Adverb, superlative
     *   RP Particle
     *   SYM Symbol
     *   TO to
     *   UH Interjection
     *   VB Verb, base form
     *   VBD Verb, past tense
     *   VBG Verb, gerund or present participle
     *   VBN Verb, past participle
     *   VBP Verb, non??3rd person singular present
     *   VBZ Verb, 3rd person singular present
     *   WDT Wh??determiner
     *   WP Wh??pronoun
     *   WP$ Possessive wh??pronoun
     *   WRB Wh??adverb
     */
    @Test
    public void posTagTest() throws Exception {
        POSModel model = new POSModelLoader()
                .load(new File("src/test/resources/models/en-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        String input = "Hi. How are you? This is Mike.";
        ObjectStream<String> lineStream = new PlainTextByLineStream(new InputStreamFactory() {
            @Override
            public InputStream createInputStream() throws IOException {
                return new ByteArrayInputStream(input.getBytes());
            }
        }, "UTF-8");

        perfMon.start();
        String line;
        while ((line = lineStream.read()) != null) {

            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            log.info(sample.toString());

            perfMon.incrementCounter();
        }
        perfMon.stopAndPrintFinalResult();
    }

    @Test
    public void findNameTest() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/models/en-ner-second-try.bin");

        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();

        NameFinderME nameFinder = new NameFinderME(model);

        String []sentence = new String[]{
//                "Check", "my", "penalty", "fee"
                "Check", "my", "other","principal", "day"
        };

        Span nameSpans[] = nameFinder.find(sentence);
        log.info("SPAN LENGTH: {}", nameSpans.length);

        for(Span s: nameSpans)
            log.info(s.toString());

        log.info(sentence[nameSpans[0].getStart()]);
        log.info("END: {}", nameSpans[0].getEnd()-1);
    }

    @Test
    public void parserTest() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/models/en-parser-chunking.bin");

        ParserModel model = new ParserModel(is);

        Parser parser = ParserFactory.create(model);

        String sentence = "check my status of loan";
        Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

        for (Parse p : topParses)
            p.show();

        is.close();
    }
}


