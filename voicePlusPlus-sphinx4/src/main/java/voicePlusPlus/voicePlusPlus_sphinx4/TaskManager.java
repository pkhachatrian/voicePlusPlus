package voicePlusPlus.voicePlusPlus_sphinx4;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


public class TaskManager {
	
	
	//Authorization Details for an instance of Google Calendar
	final static String clientId = "961199178603-hjng14f7mmlagofj23rnq9q6ql3ab4r5.apps.googleusercontent.com";
	final static String clientSecret = "vbZABAK8TVTv_SnzJtsptHKz";
	
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
	
	public static String InvokeAPICommand(APICommand apiCommand){
		String feedback = null;
		switch(apiCommand.API){
			case "GOOGLE CALENDAR":
				GoogleCalendarInstantiator googleCal = new GoogleCalendarInstantiator();
				
				//if command make new event
				//googleCal.newEvent(/*event details*/);
				
			try {
				googleCal.setUp(clientId, clientSecret);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				break;
			case "GOOGLE SEARCH":
				
				break;
			
		}
		return feedback;
	}
	
	
}
