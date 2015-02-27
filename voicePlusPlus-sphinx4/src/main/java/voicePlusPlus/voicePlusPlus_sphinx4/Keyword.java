package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Hashtable;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

public class Keyword {
	private static Hashtable<String, String> keywords;
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
	
	public static void main(String[] args) {
		String fileName;
		
		fileName = "./src/main/resources/keywords.txt";
		instantiateHashTable(fileName);
		
		Set<String> keys = keywords.keySet();
		for (String key : keys)
			System.out.println("Value of " + key + " is " + keywords.get(key));
		
		System.out.println(removeNonKeywords("Hello what's up dog invocabot schedule a meeting tomorrow at nine p m"));
	}
}
