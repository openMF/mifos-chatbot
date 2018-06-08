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
		File fout = new File("nlp/src/main/resources/TrainingDataFinance-1.txt");
		try {
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for(int i = 0 ; i < 20000 ; i++) {
                Random random = new Random();
                String content = generateData(random.nextInt(2), random.nextInt(2), random.nextInt(5));
                bw.write(content);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
		    System.out.println("Cannot write to this file");
        }
	}

	private static String generateData(int firstIdx, int secondIdx, int thirdIdx) {
        String[] verbChoices = {"look", "load", "What is"};
        String[] conjChoices = {"my", "the"};
        String[] nounChoices = {"status of loan", "interest", "oustanding principal", "next due day", "due principal"};
        StringBuffer sb = new StringBuffer();
        sb.append(verbChoices[firstIdx]);
        sb.append(" ");
        sb.append(conjChoices[secondIdx]);
        sb.append(" <START:finance> ");
        sb.append(nounChoices[thirdIdx]);
        sb.append(" <END>");

        return sb.toString();
    }
}