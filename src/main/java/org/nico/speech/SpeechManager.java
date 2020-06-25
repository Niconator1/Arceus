package org.nico.speech;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.nico.Arceus;

import java.io.IOException;
import java.util.Objects;

public class SpeechManager implements Runnable {

    private LiveSpeechRecognizer recognizer;

    public SpeechManager() {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/grammars/model_parameters/voxforge.cd_cont_6000");
        configuration.setDictionaryPath("resource:/grammars/custom.dic");

        configuration.setGrammarPath("resource:/grammars");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);

        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        recognizer.startRecognition(true);
        while (true) {
            SpeechResult speechResult = recognizer.getResult();
            if (Objects.nonNull(speechResult)) {
                String speechRecognitionResult = speechResult.getHypothesis();
                System.out.println(speechRecognitionResult);
                if (speechRecognitionResult.startsWith("jarvis ")) {
                    String text = speechRecognitionResult.substring(7);
                    String numberText = SpeechUtil.convertNumbers(text);
                    System.out.println(numberText);
                    Arceus.getInstance().handleRequest(numberText);
                }
            }
        }
    }
}
