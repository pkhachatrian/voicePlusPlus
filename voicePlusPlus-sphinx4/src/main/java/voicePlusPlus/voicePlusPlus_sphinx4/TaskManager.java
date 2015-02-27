package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.List;

public class TaskManager {
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
			case "GoogleCalendar":
				
				break;
			case "GoogleSearch":
				
				break;
			
		}
		return feedback;
	}
	
	
}
