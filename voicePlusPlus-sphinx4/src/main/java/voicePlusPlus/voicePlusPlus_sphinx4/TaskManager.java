package voicePlusPlus.voicePlusPlus_sphinx4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;




public class TaskManager {
	
	private static Hashtable<String, String> keywords;
	
	//Authorization Details for an instance of Google Calendar
	final static String clientId = "961199178603-hjng14f7mmlagofj23rnq9q6ql3ab4r5.apps.googleusercontent.com";
	final static String clientSecret = "vbZABAK8TVTv_SnzJtsptHKz";
	
	//if 0, call googlecalsetup(), if 1, then skip calling googlecalsetup()
	private static int googleCalSetup = 0;
	
	
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
//		if(command.contains("bob") || command.contains("henry") || command.contains("tim"))
//			return APIs.GOOGLE_CALENDAR;
		if (command.contains("scratch that") || command.contains("delete") || command.contains("remove")) 
			return APIs.DELETE;
		if (command.contains("list"))
			return APIs.GOOGLE_CALENDAR_LIST;
		if (command.contains("print") || command.contains("show"))
			return APIs.PRINT;
		
		Hashtable<String, Double> likelihoodOfAPIs = new Hashtable<String, Double>();
		String[] words = command.split("\\s");
		
		likelihoodOfAPIs.put(APIs.GOOGLE_CALENDAR, 0.0);
		likelihoodOfAPIs.put(APIs.GOOGLE_SEARCH, 0.0);
		
		for (String word : words) {
			String value = keywords.get(word);
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
		
		double max = -1;
		String API = "";
		for (String key : likelihoodOfAPIs.keySet()) {
			double val = likelihoodOfAPIs.get(key);
			if (val > max) {
				max = val;
				API = key;
			}
		}
		if(command.contains("schedule") || command.contains("dinner") || command.contains("meeting")) {
			if (command.contains("henry") || command.contains("bob") || command.contains("tim")){
				return API;
			}
		}
		
		//System.out.println("returning null from determine");
		return null;
	}
	
	public static String invokeAPICommand(APICommand command) { 
		String feedback = null;
		 
		System.out.println("Executing command: " + command.getAPI());
		switch(command.getAPI()) {
			case APIs.GOOGLE_CALENDAR:
				// Only call setup if its the first time receiving a calendar command.
				if (googleCalSetup == 0) {
					try {
						GoogleCalendarInstantiator.setUp(clientId, clientSecret);
						googleCalSetup = 1;
					} catch (IOException | GeneralSecurityException e1) {
						e1.printStackTrace();
					}
				}
				
				//TODO: Figure out when to make new event or list events
				//Refactor code
				//make eventID more independant --entire calendar api only works if quickAdd() is ran
				//maybe if command contains "list" do list, else add event?
				//differentiate between question/command? check for: "what","where","when"
				
				//if command make new event
				String eventId = GoogleCalendarInstantiator.quickAdd(command.getCommand());
				GoogleCalendarInstantiator.update(command.getCommand());
				
				
				
				APICommandGoogleCalendar commandCalendar = new APICommandGoogleCalendar(command.getAPI(), command.getCommand(), eventId);
				App.commands.add(commandCalendar);
				
				break;
				
			case APIs.GOOGLE_CALENDAR_LIST:
				try {
					GoogleCalendarInstantiator.setUp(clientId, clientSecret);
					googleCalSetup = 1;
				} catch (IOException | GeneralSecurityException e1) {
					e1.printStackTrace();
				}
				//Test listing events
				//System.out.println("Listing events: ");
				GoogleCalendarInstantiator.listEvents(command.getCommand());
				break;
				
			case APIs.GOOGLE_SEARCH:
				break;
			case APIs.DELETE:
				int size = App.commands.size();
				if (size > 0) {
					APICommand lastCommand = App.commands.get(size - 1);
					if (lastCommand.getAPI() == APIs.GOOGLE_CALENDAR) {
						eventId = ((APICommandGoogleCalendar) lastCommand).eventId;
						GoogleCalendarInstantiator.DeleteEvent(eventId);
					}
					App.commands.remove(App.commands.size() - 1);
				}
				break;
			case APIs.PRINT:
				App.printAllCommands();
				break;
		}
		return feedback;
	}	
}