package voicePlusPlus.voicePlusPlus_sphinx4;

import edu.cmu.sphinx.api.SpeechResult;

public class App {

	public static void main(String[] args) {
//		SphinxManager sphinxManager = new SphinxManager();
//		sphinxManager.StartRecognizingAudio();
		SpeechResult result;
		APICommand command = new APICommand();
		
		String fileName;
		fileName = "./src/main/resources/keywords.txt";
		TaskManager.instantiateHashTable(fileName);
		
		//RECOGNIZING SPEECH
//		while ((result = sphinxManager.GetSpeechResult()) != null)
//		{
			String utterance = "meeting";
//			String utterance = sphinxManager.GetUtterance(result);
//			System.out.println(utterance);
 
			command.API = TaskManager.determineAPI(utterance);
			command.command = utterance;
			
			TaskManager.InvokeAPICommand(command);
//		}
//		sphinxManager.StopRecognizingAudio();
	}
}
