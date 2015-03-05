package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Hashtable;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;


public class Keyword {
	public static Hashtable<String, String> keywords;
	/**
	 * Instantiates the Hashtable of keywords from the filename.
	 * The keys are all of the possible words used in voice commands.
	 * The values are the categories of these keywords.
	 * <p>
	 * Note: Filename should be formatted as newline, category, keyword(s), newline, category, keyword(s)... 
	 * </p>
	 * 
	 * @param fileName the name of the file to read from
	 */
	public static void instantiateHashTable(String fileName) {
		keywords = new Hashtable<String, String>();
		BufferedReader br = null;
		String line;
		String keywordCategory;
		Boolean isKeyword;
		
		try {			
			br = new BufferedReader(new FileReader(fileName));
			isKeyword = false;
			keywordCategory = "";
			
			while ((line = br.readLine()) != null) {
				if (line.equals("")) {
					isKeyword = false;
					continue;
				}
				
				if (isKeyword) {
					keywords.put(line, keywordCategory);
				}
				else {
					keywordCategory = line;
					isKeyword = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes in a string and removes the words that aren't in the hashtable from it (the non keywords).
	 * 
	 * @param command the command that will have the non keywords removed
	 * @return the command with only the keywords
	 */
	public static String removeNonKeywords(String command) {
		String[] words;
		StringBuilder sbKeywordsOnly;
		String word;
		
		sbKeywordsOnly = new StringBuilder();
		words = command.split("\\s");
		
		for (int i=0; i<words.length; i++) {
			word = words[i];
			
			if (word.equals("p") || word.equals("a")) {
				if (i < words.length - 1) {
					if (words[i+1].equals("m")) {
						sbKeywordsOnly.append(word + "m ");
						i = i++;
						continue;
					}
				}
			}
			
			if (keywords.get(word) != null) {
				sbKeywordsOnly.append(word + " ");
			}
		}
		sbKeywordsOnly.deleteCharAt(sbKeywordsOnly.length() - 1);
		
		return sbKeywordsOnly.toString();
	}
	
	/**
	 * Determines which API the program will use given the command string.
	 * The order of the APIs (in APIs.java) will determine how the array and ordering works.
	 * 
	 * @param command the command with only keywords
	 * @return the API as a string
	 */
	public static String determineAPI(String command) {
		double max;
		double val;
		String[] words;
		String value;
		String API;
		Hashtable<String, Double> likelihoodOfAPIs;
		
		likelihoodOfAPIs = new Hashtable<String, Double>();
		words = command.split("\\s");
		
		likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, 0.0);
		likelihoodOfAPIs.put(APIs.GOOGLE_SEARCH, 0.0);
		
		for (String word : words) {
			value = keywords.get(word);
			if (value.equals(APIs.GOOGLE_CALENDAR)) {
				likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, likelihoodOfAPIs.get(APIs.GOOGLE_CALENDAR) + 1.0);
			}
			else if (value.equals(APIs.GOOGLE_SEARCH)) {
				likelihoodOfAPIs.put(APIs.GOOGLE_SEARCH, likelihoodOfAPIs.get(APIs.GOOGLE_SEARCH) + 1.0);
			}
			else if (value.equals("DATE") || value.equals("TIME")) {
				likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, likelihoodOfAPIs.get(APIs.GOOGLE_CALENDAR) + 0.75);
			}
		}
		
		max = -1;
		API = "";
		for (String key : likelihoodOfAPIs.keySet()) {
			val = likelihoodOfAPIs.get(key);
			if (val > max) {
				max = val;
				API = key;
			}
		}
		
		return API;
	}
	
	public static String Get(Object key){
		return keywords.get(key);
	}
	
	public static void main(String[] args) {
		String fileName;
		 
		fileName = "./src/main/resources/keywords.txt";
		instantiateHashTable(fileName);
		
//		Set<String> keys = keywords.keySet();
//		for (String key : keys)
//			System.out.println("Value of " + key + " is " + keywords.get(key));
		
		//System.out.println(determineAPI("w"));
		
		//added this
		String chosenApi = determineAPI("schedule meeting tomorrow nine");
		APICommand command = new APICommand(chosenApi);
		System.out.print(command.API);
		TaskManager.InvokeAPICommand(command);
		
		
		
		
	
		
	}
}