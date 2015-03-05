package voicePlusPlus.voicePlusPlus_sphinx4;

import edu.cmu.sphinx.api.SpeechResult;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SphinxManager sphinxManager = new SphinxManager();
		//TaskManager taskManager = new TaskManager();
//		sphinxManager.StartRecognizingAudio();
//		SpeechResult result;
//		
//		//RECOGNIZING SPEECH
//		while ((result = sphinxManager.GetSpeechResult()) != null)
//		{
//			String utterance = sphinxManager.GetUtterance(result);
//			System.out.println(utterance);
//		}
		
		String fileName;
		fileName = "./src/main/resources/keywords.txt";
		TaskManager.instantiateHashTable(fileName);
		
		
		GoogleCalendarAPICommand command = new GoogleCalendarAPICommand();
		command.EventDescription = "schedule meeting today nine";
		command.API = TaskManager.determineAPI(command.EventDescription);
		
		TaskManager.InvokeAPICommand(command);
		//sphinxManager.StopRecognizingAudio();
	}

}
