package org.nico.speech;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SpeechUtil {

    private final static String[] SINGLENUMBERS = {"null", "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben",
            "acht", "neun", "zehn", "elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn"};

    private final static String[] COMBONUMBERS = {"ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben",
            "acht", "neun"};

    private final static String[] TENS = {"zwanzig", "dreißig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig"};

    public static String convertNumbers(String text) {
        StringBuilder sentenceBuilder = new StringBuilder();
        String[] words = text.split(" ");
        if (words.length > 0) {
            Queue<String> queue = new LinkedList<>(Arrays.asList(words));
            while (!queue.isEmpty()) {
                String word = queue.poll();
                boolean foundNumber = false;
                // [1,2,3,4,5,6,7,8,9,21,22,...,99]
                combos:
                for (int i = 0; i < COMBONUMBERS.length; i++) {
                    if (COMBONUMBERS[i].equals(word)) {
                        String nextWord = queue.poll();
                        if ("und".equals(nextWord)) {
                            String contextWord = queue.poll();
                            for (int j = 0; j < TENS.length; j++) {
                                if (TENS[j].equals(contextWord)) {
                                    sentenceBuilder.append((j + 2) * 10 + (i + 1)).append(" ");
                                    foundNumber = true;
                                    break combos;
                                }
                            }
                        } else {
                            sentenceBuilder.append(i + 1).append(" ").append(nextWord);
                            foundNumber = true;
                            break;
                        }
                    }
                }
                // [20,30,40,50,60,70,80,90]
                if (!foundNumber) {
                    for (int i = 0; i < TENS.length; i++) {
                        if (TENS[i].equals(word)) {
                            sentenceBuilder.append((i + 2) * 10).append(" ");
                            foundNumber = true;
                            break;
                        }
                    }
                }
                // [0,10,11,12,13,14,15,16,17,18,19]
                if (!foundNumber) {
                    for (int i = 0; i < SINGLENUMBERS.length; i++) {
                        if (SINGLENUMBERS[i].equals(word)) {
                            sentenceBuilder.append(i).append(" ");
                            foundNumber = true;
                            break;
                        }
                    }
                }
                if (!foundNumber) {
                    if ("hundert".equals(word)) {
                        sentenceBuilder.append(100).append(" ");
                        foundNumber = true;
                    }
                }
                if (!foundNumber) {
                    sentenceBuilder.append(word).append(" ");
                }
            }
        }
        return sentenceBuilder.toString().trim();
    }
}
