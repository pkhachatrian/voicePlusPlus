package voicePlusPlus.voicePlusPlus_sphinx4;

import edu.cmu.sphinx.api.SpeechResult;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SphinxManager sphinxManager = new SphinxManager();
		TaskManager taskManager = new TaskManager();
		sphinxManager.StartRecognizingAudio();
		SpeechResult result;
		while ((result = sphinxManager.GetSpeechResult()) != null)
		{
			String utterance = sphinxManager.GetUtterance(result);
			System.out.println(utterance);
		}
	}

}
