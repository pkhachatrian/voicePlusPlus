package voicePlusPlus.voicePlusPlus_sphinx4;

import java.io.IOException;

public class SphinxManager  {
//	private Configuration config;
//	private LiveSpeechRecognizer recognizer;
//	
//	public SphinxManager(){
//		config = new Configuration();
//    	config.setAcousticModelPath("../sphinx4-data/resources/edu/cmu/sphinx/models/en-us/en-us");
////    	config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
////    	config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
//    	
//    	config.setDictionaryPath("./src/main/resources/1905.dic");
//    	config.setLanguageModelPath("./src/main/resources/1905.lm");
//    	
//    	try {
//			recognizer = new LiveSpeechRecognizer(config);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public SphinxManager(Configuration configuration){
//		config = configuration;
//    	try {
//			recognizer = new LiveSpeechRecognizer(config);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void startRecognizingAudio(){
//        recognizer.startRecognition(true);
//	}
//	
//	public void stopRecognizingAudio(){
//		recognizer.stopRecognition();
//	}
//	
//    public SpeechResult getSpeechResult() {
//        SpeechResult result;
//		result = recognizer.getResult();
//		return result;
//    }
//    
//    public String getUtterance(SpeechResult result){
//		String utterance = result.getHypothesis(); 
//        return utterance;
//    }
	
    /**
     * Searches for the word "invocabot" in the string and returns the words that follow the word invocabot
     * @param string
     * @return The truncated version of the string. 
     * Input: "What time is it? Invocabot schedule an appointment with Chandra at 12 pm tomorrow" 
     * Output: "schedule an appointment with Chandra at 12 pm tomorrow"
     */
    public static String getCommand(String string){
    	int index = string.indexOf("invocabot");
    	
    	// If "invocabot" is not in string.
    	if (index == -1) return null;
    	
    	// If "invocabot" is last word in string.
    	String words[] = string.split(" ");
    	if (words[words.length - 1].equals("invocabot"))
    		return "";
    	
    	// If string is just "invocabot".
    	if (string.equalsIgnoreCase("invocabot")) return "";
    	
    	String output;
    	// Check if "invocabot" exists twice.
    	int endIndex = string.indexOf("invocabot", index + 1);
    	int truncationIndex = index + "invocabot".length() + 1;
    	
    	// If "invocabot" exists once, truncate until end of string.
    	if (endIndex == -1) {
    		output = string.substring(truncationIndex);
    	} 
    	// If "invocabot" exists more than once, truncate at the last word before the next "invocabot".
    	else {
    		output = string.substring(truncationIndex, endIndex - 1);
    	}
    	
    	return output;
    }
}