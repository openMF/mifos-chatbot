package org.mifos.chatbot.nlp;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class TrainingDataGeneration{
	public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
		dataFileGeneration();
	}

	private static void dataFileGeneration() {
		File fout = new File("nlp/src/main/resources/TrainingDataFinance-2.txt");
		try {
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for(int i = 0 ; i < 20000 ; i++) {
                Random random = new Random();
                String content = generateData(random.nextInt(6), random.nextInt(3), random.nextInt(5));
                bw.write(content);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
		    System.out.println("Cannot write to this file");
        }
	}

    /**
     * This function is to generate mock user input
     * @param firstIdx
     * @param secondIdx
     * @param thirdIdx
     * @return It will returns the training data with respective tags
     *
     * @author Dingfan
     */
	private static String generateData(int firstIdx, int secondIdx, int thirdIdx) {
        String[] verbChoices = {"look", "load", "What is", "write to", "how", "update"};
        String[] conjChoices = {"my", "the", "the other user's"};
        String[] nounChoices = {"status of loan", "interest", "outstanding principal", "next due day", "due principal"};
        StringBuffer sb = new StringBuffer();
        sb.append(generateVerbTag(verbChoices[firstIdx]));
        sb.append(" ");
        sb.append(generateContextTag(conjChoices[secondIdx]));
        sb.append(generateCategoryTag(nounChoices[thirdIdx]));

        return sb.toString();
    }

    // Here left a question: I noticed that there are approximately two types of actions, read and write.
    // Do we recognize these two actions as one type or two different tags?

    // OpenNLP is used for entity extraction,
    private static String generateVerbTag(String verb) {
	    StringBuffer sb = new StringBuffer();
	    sb.append(" <START:action> ");
	    sb.append(verb);
	    sb.append(" <END>");

	    return sb.toString();
    }

    private static String generateContextTag(String context) {
        StringBuffer sb = new StringBuffer();
        sb.append(" <START:context> ");
        sb.append(context);
        sb.append(" <END>");

        return sb.toString();
    }

    // For this category issue, it does not need to recognize the day, because it is handled by the Mifos API
    private static String generateCategoryTag(String category) {
        StringBuffer sb = new StringBuffer();
        sb.append(" <START:category> ");
        sb.append(category);
        sb.append(" <END>");
        if(sb.indexOf("day") != -1) {
            sb.insert(sb.indexOf("day"), " <END> <START:date> ");
        }

        return sb.toString();
    }
}