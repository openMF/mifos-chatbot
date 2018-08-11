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

import opennlp.tools.namefind.*;
import opennlp.tools.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * This class is used to train the named entity recognition, which is the model related to financial terminologies
 * Ideas come from https://www.tutorialkart.com/opennlp/ner-training-in-opennlp-with-name-finder-training-java-example/
 */
public class OpenNLPModelTrainer {

    private final Logger logger = LoggerFactory.getLogger(OpenNLPModelTrainer.class);

    // about 15,000 sentences will be enough for the model to output satisfactory named entity.
    public Boolean train() {

        // Step 1: read the training data
        InputStreamFactory in = null;
        try {
            in = new MarkableFileInputStreamFactory(new File("src/main/resources/TrainingDataFinance-3.txt"));
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException Step 1 : ", e);
            return false;
        }

        ObjectStream inputStream = null;
        try {
            inputStream = new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("IOException Step 2 : ", e);
            return false;
        }

        // Step 2: prepare training parameters
        TrainingParameters parameters = new TrainingParameters();
        parameters.put(TrainingParameters.ITERATIONS_PARAM, 100);
        parameters.put(TrainingParameters.CUTOFF_PARAM, 1);

        // Step 3: train the model
        TokenNameFinderModel nameFinderModel = null;
        try {
            nameFinderModel = NameFinderME.train("en", null, inputStream, parameters,
                    TokenNameFinderFactory.create(
                            null,
                            null,
                            Collections.emptyMap(),
                            new BioCodec()
                    )
            );
        } catch (IOException e) {
            logger.error("IOException Step 3 : ", e);
            return false;
        }

        // Step 4: save the model to a file
        File output = new File("src/main/resources/models/en-ner-third-try.bin");
        try {
             FileOutputStream outputStream = new FileOutputStream(output);
            nameFinderModel.serialize(outputStream);
        } catch (IOException e) {
            logger.error("IOException Step 4 : ", e);
            return false;
        }

        return true;
    }
}
