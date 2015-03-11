package voicePlusPlus.voicePlusPlus_sphinx4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Hashtable;
import java.util.List;


public class TaskManager {
	
	private static Hashtable<String, String> keywords;
	
	//Authorization Details for an instance of Google Calendar
	final static String clientId = "961199178603-hjng14f7mmlagofj23rnq9q6ql3ab4r5.apps.googleusercontent.com";
	final static String clientSecret = "vbZABAK8TVTv_SnzJtsptHKz";
	
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
			if (value == null)
				continue;
			switch (value) {
				case APIs.GOOGLE_CALENDAR:
					likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, likelihoodOfAPIs.get(APIs.GOOGLE_CALENDAR) + 1.0);
					break;
				case APIs.GOOGLE_SEARCH:
					likelihoodOfAPIs.put(APIs.GOOGLE_SEARCH, likelihoodOfAPIs.get(APIs.GOOGLE_SEARCH) + 1.0);
				case "DATE":
				case "TIME":
					likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, likelihoodOfAPIs.get(APIs.GOOGLE_CALENDAR) + 0.75);
					break;
				default:	
					break;
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
	
	public static String getValue(Object key){
		return keywords.get(key);
	}
	
	public static String ProcessCommand(String command){
		return command;	
	}
	
	public static List<String> FindKeywords(String command)
	{
		return null;
	}
	
	public static String DetermineAPI(String command){
		return null;
	}
	
	
	public static APICommand GetAPICommand(String API, List<String> keywords){
		
		return null;
	}
	
	public static String InvokeAPICommand(APICommand command){
		String feedback = null;
		switch(command.API){
			case APIs.GOOGLE_CALENDAR:
				System.out.println("Executing command: " + command.API);
			/*CREATE A GOOGLE CALENDAR INSTANCE*/
			try {
				GoogleCalendarInstantiator.setUp(clientId, clientSecret);
			} catch (IOException | GeneralSecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				//if command make new event
				GoogleCalendarInstantiator.quickAdd(command.command);
				GoogleCalendarInstantiator.update(command.command);
				//GoogleCalendarInstantiator.listEvents();
			
				break;
			case APIs.GOOGLE_SEARCH:
				
				break;
			
		}
		return feedback;
	}
	
	
}
