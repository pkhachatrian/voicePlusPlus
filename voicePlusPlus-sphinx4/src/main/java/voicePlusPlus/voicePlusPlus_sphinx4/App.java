package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.HashSet;
import java.util.Set;

import edu.cmu.sphinx.api.SpeechResult;

public class App {

	public static void main(String[] args) {
		SphinxManager sphinxManager = new SphinxManager();
		sphinxManager.StartRecognizingAudio();
		SpeechResult result;
		APICommand command = new APICommand();
		
		String fileName;
		fileName = "./src/main/resources/keywords.txt";
		TaskManager.instantiateHashTable(fileName);
		
		//RECOGNIZING SPEECH
		while ((result = sphinxManager.GetSpeechResult()) != null)
		{	

			String utterance = sphinxManager.GetUtterance(result);
			utterance = formatString(utterance);
			String commandString = SphinxManager.GetCommand(utterance);
			if (commandString == null || commandString.equals("")) {
				continue;
			}
			
//			System.out.println("You said : " + utterance);
 
			command.API = TaskManager.determineAPI(commandString);
			command.command = commandString;

			TaskManager.InvokeAPICommand(command);
		}
		sphinxManager.StopRecognizingAudio();
	}
	
	public static String formatString(String eventText) {
		Set set = new HashSet();
		
		set.add("one");
		set.add("two");
		set.add("three");
		set.add("four");
		set.add("five");
		set.add("six");
		set.add("seven");
		set.add("eight");
		set.add("nine");
		
		String words[] = eventText.split(" ");
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<words.length; i++) {
			if (!set.contains(words[i]))
				sb.append(words[i] + " ");
			else {
				switch (words[i]) {
					case "one":
						sb.append("1 ");
						break;
					case "two":
						sb.append("2 ");
						break;
					case "three":
						sb.append("3 ");
						break;
					case "four":
						sb.append("4 ");
						break;
					case "five":
						sb.append("5 ");
						break;
					case "six":
						sb.append("6 ");
						break;
					case "seven":
						sb.append("7 ");
						break;
					case "eight":
						sb.append("8 ");
						break;
					case "nine":
						sb.append("9 ");
						break;
				}
			}
		}
		
		return sb.toString();
	}
}
