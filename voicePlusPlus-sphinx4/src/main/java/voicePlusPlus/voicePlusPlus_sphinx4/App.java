package voicePlusPlus.voicePlusPlus_sphinx4;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Configuration config = new Configuration();
    	config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    	config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    	config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
    	
        try {
    		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
    	    InputStream stream = new FileInputStream("../sphinx4-core/target/test-classes/edu/cmu/sphinx/tools/bandwidth/10001-90210-01803.wav");

    	    recognizer.startRecognition(stream);
            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                System.out.format("Hypothesis: %s\n", result.getHypothesis());

                System.out.println("List of recognized words and their times:");
                for (WordResult r : result.getWords()) {
                    System.out.println(r);
                }

                System.out.println("Best 3 hypothesis:");
                for (String s : result.getNbest(3))
                    System.out.println(s);

            }
            recognizer.stopRecognition();
    	} catch (IOException e) {
    		System.err.println(e.getMessage());
    		e.printStackTrace();
    	}
    }
}
