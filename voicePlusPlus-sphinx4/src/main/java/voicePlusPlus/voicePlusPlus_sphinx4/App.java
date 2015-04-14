package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.HashSet;
import java.util.Scanner;

import edu.cmu.sphinx.api.SpeechResult;

import org.freeswitch.esl.client.inbound.*;
import org.junit.Test;

import java.io.Console;

import static org.junit.Assert.*;


public class App {
	public static void main(String[] args) {
		Client c = new Client();
		
		String host = "54.69.110.102"; // remember to keep changing this
		int port = 10630;
		int timeoutSeconds = 10;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the FreeSwitch server password: ");
        String password = sc.next();
        sc.close();
		
		try {
			System.out.println("Attempting to connect to FreeSwitch server");
			c.connect(host, port, password, timeoutSeconds);
			
			while (true) {
				String command = "";
				String arg = "";
				System.out.println(c.sendAsyncApiCommand(command, arg));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//SphinxManager sphinxManager = new SphinxManager();
		//sphinxManager.StartRecognizingAudio();
//		SpeechResult result;
//		APICommand command = new APICommand();
//		
//		String fileName;
//		fileName = "./src/main/resources/keywords.txt";
//		TaskManager.instantiateHashTable(fileName);
//		
////		//RECOGNIZING SPEECH
////		while ((result = sphinxManager.GetSpeechResult()) != null)
////		{
//			//String utterance = sphinxManager.GetUtterance(result);
//			String utterance = "invocabot schedule a meeting today";
//			System.out.println(utterance);
//			String commandString = SphinxManager.GetCommand(utterance);
//			System.out.println(commandString);
////			if (commandString == null || commandString.equals("")) {
////				continue;
////			}
// 
//			command.API = TaskManager.determineAPI(commandString);
//			
//			if (command.API == APIs.GOOGLE_CALENDAR) {
//				utterance = convertNumbersAsTextToDigits(utterance);	
//			}
//			
//			command.command = commandString;
//
//			TaskManager.InvokeAPICommand(command);
////		}
//		sphinxManager.StopRecognizingAudio();
	}
	
	public static String convertNumbersAsTextToDigits(String eventText) {
		HashSet<String> setOnes = new HashSet<String>();
		HashSet<String> setTeens = new HashSet<String>();
		HashSet<String> setDecades = new HashSet<String>();
		
		setOnes.add("one");
		setOnes.add("two");
		setOnes.add("three");
		setOnes.add("four");
		setOnes.add("five");
		setOnes.add("six");
		setOnes.add("seven");
		setOnes.add("eight");
		setOnes.add("nine");
		setTeens.add("ten");
		setTeens.add("eleven");
		setTeens.add("twelve");
		setTeens.add("thirteen");
		setTeens.add("fourteen");
		setTeens.add("fifteen");
		setTeens.add("sixteen");
		setTeens.add("seventeen");
		setTeens.add("eighteen");
		setTeens.add("nineteen");
		setDecades.add("twenty");
		setDecades.add("thirty");
		setDecades.add("forty");
		setDecades.add("fifty");
		
		String words[] = eventText.split(" ");
		StringBuilder sb = new StringBuilder();
		
		boolean notLastWord = true;
		boolean colon = false;
		
		for (int i=0; i<words.length; i++) {
			colon = false;
			
			if (i == words.length - 1) {
				notLastWord = false;
			}
			
			if (setTeens.contains(words[i])) {
				switch (words[i]) {
					case "ten":
						sb.append("10");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "eleven":
						sb.append("11");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) { 
							sb.append(":");
						}
						break;
					case "twelve":
						sb.append("12");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "thirteen":
						sb.append("13");
						break;
					case "fourteen":
						sb.append("14");
						break;
					case "fifteen":
						sb.append("15");
						break;
					case "sixteen":
						sb.append("16");
						break;
					case "seventeen":
						sb.append("17");
						break;
					case "eighteen":
						sb.append("18");
						break;
					case "nineteen":
						sb.append("19");
						break;
				}
			}
			else if (setDecades.contains(words[i])) {
				int value = 0;
				switch (words[i]) {
					case "twenty":
						value = 20;
						break;
					case "thirty":
						value = 30;
						break;
					case "forty":
						value = 40;
						break;
					case "fifty":
						value = 50;
						break;
				}
				if (notLastWord && setOnes.contains(words[i+1])) {
					switch (words[i+1]) {
						case "one":
							value += 1;
							break;
						case "two":
							value += 2;
							break;
						case "three":
							value += 3;
							break;
						case "four":
							value += 4;
							break;
						case "five":
							value += 5;
							break;
						case "six":
							value += 6;
							break;
						case "seven":
							value += 7;
							break;
						case "eight":
							value += 8;
							break;
						case "nine":
							value += 9;
							break;
					}
					i++;
					if (i == words.length - 1) {
						notLastWord = false;
					}
				}
				sb.append(value);
			}
			else if (setOnes.contains(words[i])) {
				switch (words[i]) {
					case "one":
						sb.append("1");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "two":
						sb.append("2");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "three":
						sb.append("3");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "four":
						sb.append("4");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "five":
						sb.append("5");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "six":
						sb.append("6");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "seven":
						sb.append("7");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "eight":
						sb.append("8");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
					case "nine":
						sb.append("9");
						if (notLastWord && (setOnes.contains(words[i+1]) || setTeens.contains(words[i+1]) || setDecades.contains(words[i+1]))) {
							sb.append(":");
						}
						break;
				}
				colon = true;
			}
			else {
				sb.append(words[i]);
			}
			
			if (notLastWord && !colon) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}
	
	@Test
	public static void testConvertNumbersAsTextToDigits() {
		String s1 = "twenty";
		String s2 = "forty one";
		String s3 = "one forty";
		String s4 = "schedule a meeting at one forty pm";
		String s5 = "";
		String s6 = "one forty five";
		String s7 = "two twelve";
		
		assertEquals("20", convertNumbersAsTextToDigits(s1));
		assertEquals("41", convertNumbersAsTextToDigits(s2));
		assertEquals("1:40", convertNumbersAsTextToDigits(s3));
		assertEquals("schedule a meeting at 1:40 pm", convertNumbersAsTextToDigits(s4));
		assertEquals("", convertNumbersAsTextToDigits(s5));
		assertEquals("1:45", convertNumbersAsTextToDigits(s6));
		assertEquals("2:12", convertNumbersAsTextToDigits(s7));
	}
}