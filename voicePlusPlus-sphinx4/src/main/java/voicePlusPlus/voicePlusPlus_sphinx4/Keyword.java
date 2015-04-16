package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Hashtable;
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
		try {			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			keywords = new Hashtable<String, String>();
			boolean isKeyword = false;
			String keywordCategory = "";
			
			String line;
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
			br.close();
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
		StringBuilder sbKeywordsOnly = new StringBuilder();
		String[] words = command.split("\\s");
		
		for (int i=0; i<words.length; i++) {
			String word = words[i];
			
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
		Hashtable<String, Double> likelihoodOfAPIs = new Hashtable<String, Double>();
		String[] words = command.split("\\s");
		
		likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, 0.0);
		likelihoodOfAPIs.put(APIs.GOOGLE_SEARCH, 0.0);
		
		for (String word : words) {
			String val = keywords.get(word);
			if (val.equals(APIs.GOOGLE_CALENDAR)) {
				likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, likelihoodOfAPIs.get(APIs.GOOGLE_CALENDAR) + 1.0);
			}
			else if (val.equals(APIs.GOOGLE_SEARCH)) {
				likelihoodOfAPIs.put(APIs.GOOGLE_SEARCH, likelihoodOfAPIs.get(APIs.GOOGLE_SEARCH) + 1.0);
			}
			else if (val.equals("DATE") || val.equals("TIME")) {
				likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, likelihoodOfAPIs.get(APIs.GOOGLE_CALENDAR) + 0.75);
			}
		}
		
		double max = -1;
		String API = "";
		for (String key : likelihoodOfAPIs.keySet()) {
			double val = likelihoodOfAPIs.get(key);
			if (val > max) {
				max = val;
				API = key;
			}
		}
		
		return API;
	}
}