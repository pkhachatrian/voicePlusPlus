package voicePlusPlus.voicePlusPlus_sphinx4;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
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
public class SphinxManager 
{
    public static void main( String[] args )
    {
    	// Set up Configuration object.
    	Configuration config = new Configuration();
    	config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//    	config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//    	config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
    	
    	//You will need to change the paths to match your computer
    	config.setDictionaryPath("./src/main/resources/1905.dic");
    	config.setLanguageModelPath("./src/main/resources/1905.lm");
    	
        try {

        	LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
        	recognizer.startRecognition(true);
    		
//        	// Create Recognizer and stream to be an audio file.
//        	StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
//    	    InputStream stream = new FileInputStream("../sphinx4-core/target/test-classes/edu/cmu/sphinx/tools/bandwidth/10001-90210-01803.wav");
//
//    	    // Start recognizing. While there is something to recognize, print out what has been recognized as words.
//    	    recognizer.startRecognition(stream);
        	
        	
            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                System.out.format("Hypothesis: %s\n", result.getHypothesis());
//
//                System.out.println("List of recognized words and their times:");
//                for (WordResult r : result.getWords()) {
//                    System.out.println(r);
//                }

//                System.out.println("Best 3 hypothesis:");
//                for (String s : result.getNbest(3))
//                    System.out.println(s);

            }
            recognizer.stopRecognition();
        	
    	} catch (IOException e) {
    		System.err.println(e.getMessage());
    		e.printStackTrace();
    	}
    }
    
    /**
     * Searches for the word "invocabot" in the string and returns the words that follow the word invocabot
     * @param string
     * @return The truncated version of the string. 
     * Input: "What time is it? Invocabot schedule an appointment with Chandra at 12 pm tomorrow" 
     * Output: "schedule an appointment with Chandra at 12 pm tomorrow"
     */
    public static String GetCommand(String string){
    	int index = string.indexOf("invocabot");
    	int truncationIndex = index + "invocabot".length() + 1;
    	System.out.println(string.substring(truncationIndex));
    	return string.substring(truncationIndex);
    }
}
