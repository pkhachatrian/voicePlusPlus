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
	Configuration config;
	LiveSpeechRecognizer recognizer;
	
	public SphinxManager(){
		config = new Configuration();
    	config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//    	config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//    	config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
    	
    	//You will need to change the paths to match your computer
    	config.setDictionaryPath("./src/main/resources/1905.dic");
    	config.setLanguageModelPath("./src/main/resources/1905.lm");
    	try {
			recognizer = new LiveSpeechRecognizer(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SphinxManager(Configuration configuration){
		config = configuration;
    	try {
			recognizer = new LiveSpeechRecognizer(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void StartRecognizingAudio(){
        recognizer.startRecognition(true);
	}
	
	public void StopRecognizingAudio(){
		recognizer.stopRecognition();
	}
	
    public SpeechResult GetSpeechResult(){
    	String utterance = null;
        SpeechResult result;
		result = recognizer.getResult();
		return result;
    }
    
    public String GetUtterance(SpeechResult result){
		String utterance = result.getHypothesis(); 
        return utterance;
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
    	//if invocabot is not in string
    	if (index == -1) return null;
    	
    	String output;
    	//Check if invocabot exists twice
    	int endIndex = string.indexOf("invocabot", index + 1);
    	int truncationIndex = index + "invocabot".length() + 1;
    	
    	//If invocabot exists once, truncate until end of string
    	if (endIndex == -1) {
    		output = string.substring(truncationIndex);
    	} 
    	//if invocabot exists more than once, truncate at the last word before the next invocabot
    	else {
    		output = string.substring(truncationIndex, endIndex - 1);
    	}
    	return output;
    }
}
