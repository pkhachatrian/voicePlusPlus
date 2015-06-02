package voicePlusPlus.voicePlusPlus_sphinx4;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Date;



//Event libraries
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.client.util.DateTime;

public class GoogleCalendarInstantiator {	
	private static HttpTransport httpTransport;
	private static JacksonFactory jsonFactory;
	private static Credential credential;
	private static Calendar service;
	private static GoogleTokenResponse response = null;
	private static Event createdEvent = null;
		
	public static void setUp(String clientId, String clientSecret) throws IOException, GeneralSecurityException{
		
		
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    jsonFactory = JacksonFactory.getDefaultInstance();

	    // The clientId and clientSecret can be found in Google Developers Console

	    // Or your redirect URL for web based applications.
	    String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
	    String scope = "https://www.googleapis.com/auth/calendar";

	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(
	        httpTransport, jsonFactory, clientId, clientSecret, Collections.singleton(scope));
	    
	    
	    /*COMMENT OUT BELOW IF NEED TO REUSE AUTH CODE*/
	    /*-------------------------------------------------------------------------------------*/
	     //Step 1: Authorize
	    String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build();

	    // Point or redirect your user to the authorizationUrl.
	    System.out.println("Go to the following link in your browser:");
	    System.out.println(authorizationUrl);
	    /*-------------------------------------------------------------------------------------*/
	    
	    // Read the authorization code from the standard input stream.
	    
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("What is the authorization code?");
	    
	    
	  //code goes here if auth code is hard-coded
	    String code = "4/BmIz3iBzKM8vRFY5gJJVlPhyz0wCRoW6PWKl9X1vL_4.oilbS7AxaHwc3nHq-8bbp1vASANsmwI";//in.readLine();
	    // End of Step 1

	    // Step 2: Exchange
	    if (response == null){
	    	response = flow.newTokenRequest(code).setRedirectUri(redirectUrl).execute();
	    }
	    
	    // End of Step 2

	    credential = new GoogleCredential.Builder()
	        .setTransport(httpTransport)
	        .setJsonFactory(jsonFactory)
	        .setClientSecrets(clientId, clientSecret)
	        .build().setFromTokenResponse(response);

	     service = new Calendar.Builder(httpTransport, jsonFactory, credential)
	        .setApplicationName("voicePlusPlus").build();
			
	}
	
	public static void newEvent(String command) {
		/*Currently makes an event with hard-coded settings*/
		
		/*doesn't do anything with command yet. maybe we won't need to.*/
		

		// Create and initialize a new event
		Event event = new Event();
		event.setSummary("Appointment");
		event.setLocation("UCSB");

		ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
		attendees.add(new EventAttendee().setEmail("belsinb@gmail.com"));
		
		event.setAttendees(attendees);

		Date startDate = new Date();
		Date endDate = new Date(startDate.getTime() + 3600000);
		DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
		event.setEnd(new EventDateTime().setDateTime(end));

		// Insert the new event
		//Event createdEvent = null;
		try {
			createdEvent = service.events().insert("primary", event).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
	}
	
	public static String quickAdd(String eventText) {
		// Quick-add an event
		try {
			createdEvent = service.events().quickAdd("primary", eventText).setText(eventText).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return createdEvent.getId();
	}
	
	public static void update(String eventText) {
		
		//Create empty list of attendees
		ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
		
		//Split eventText into words to check invitee names
		String whosComing[] = eventText.split(" ");
		
		//Check if any of the words are people to invite
		for(int i = 0; i < whosComing.length; i++){
			//add more if statements for any invitees
			if(whosComing[i].equals("chris")){
				attendees.add(new EventAttendee().setEmail("chris378335632@gmail.com"));
			}
			if(whosComing[i].equals("belsin")){
				attendees.add(new EventAttendee().setEmail("belsin2524662@gmail.com"));
			}
			if(whosComing[i].equals("sheng")){
				attendees.add(new EventAttendee().setEmail("sheng3826452@gmail.com"));
			}
			if(whosComing[i].equals("petros")){
				attendees.add(new EventAttendee().setEmail("petros32324352@gmail.com"));
			}
			if(whosComing[i].equals("julio")){
				attendees.add(new EventAttendee().setEmail("julio2383324@gmail.com"));
			}
			if(whosComing[i].equals("bob")){
				attendees.add(new EventAttendee().setEmail("bob86332446@gmail.com"));
			}
			if(whosComing[i].equals("chandra")){
				attendees.add(new EventAttendee().setEmail("chandra386255226@gmail.com"));
			}
				
		}
		

		
		// Retrieve the event from the API
		Event event = null;
		try {
			event = service.events().get("primary", createdEvent.getId()).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Make a change
		
		
//		eventText = eventText.substring(21, eventText.length());
		event.setSummary(eventText);
		event.setAttendees(attendees);
		// Update the event
		Event updatedEvent = null;
		try {
			updatedEvent = service.events().update("primary", event.getId(), event).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Created event: " + eventText);
	}
	
	public static void DeleteEvent(String eventId) {
		try {
			service.events().delete("primary", eventId ).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	public static void listEvents(String eventText){
		
		//create a reference to current instantaneous time
		DateTime now = new DateTime(System.currentTimeMillis());
		
		//create a reference for today's date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date date = new Date();
		
		//variable that decides what events to list
		int toList = 0;
		
		//create dictionary to map numbers to months
		Dictionary d1 = new Hashtable();
		
		d1.put("01", "January");
		d1.put("02", "February");
		d1.put("03","March");
		d1.put("04", "April");
		d1.put("05", "May");
		d1.put("06","June");
		d1.put("07", "July");
		d1.put("08", "August");
		d1.put("09", "September");
		d1.put("10","October");
		d1.put("11","November");
		d1.put("12","December");
		
		Dictionary d2 = new Hashtable();
		
		d2.put("01", "first");
		d2.put("02", "second");
		d2.put("03","third");
		d2.put("04", "fourth");
		d2.put("05", "fifth");
		d2.put("06","sixth");
		d2.put("07", "seventh");
		d2.put("08", "eight");
		d2.put("09", "ninth");
		d2.put("10","tenth");
		d2.put("11","eleventh");
		d2.put("12","twelfth");
		d2.put("13","thirteenth");
		d2.put("14","fourteenth");
		d2.put("15","fifteenth");
		d2.put("16","sixteenth");
		d2.put("17","seventeenth");
		d2.put("18","eighteenth");
		d2.put("19","nineteenth");
		d2.put("20","twentieth");
		d2.put("21","twenty first");
		d2.put("22","twenty second");
		d2.put("23","twenty third");
		d2.put("24","twenty fourth");
		d2.put("25", "twenty fifth");
		d2.put("26","twenty sixth");
		d2.put("27", "twenty seventh");
		d2.put("28", "twenty eighth");
		d2.put("29", "twenty ninth");	
		d2.put("30", "thirtieth");
		d2.put("31", "thirty first");
		
		
		Events events = null;
		try {
			//Original
			//events = service.events().list("primary").setPageToken(pageToken).execute();
			//Testing
			//events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();
			events = service.events().list("primary").setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		  java.util.List<Event> items = events.getItems();
			  
		  //prints out full date and then today's month and day
		  String thisMonth = dateFormat.format(date).substring(3,5);
		  //System.out.println("The full date is: " + dateFormat.format(date).toString());
		  //System.out.println("This month is: " + thisMonth);
		  String today = dateFormat.format(date).substring(0,2);
		  
		  if (items.size() == 0) {
	            System.out.println("No upcoming events found.");
	        } else {
	            System.out.println("Upcoming events:");
	            for (Event event : items) {
	            	
	            	//if >=13  minus 12 and to pm
	            	
	            	//setup the event's time
	            	String theTime = event.getStart().getDateTime().toString().substring(11,16);
	            	String theHour = theTime.substring(0,2);
	            	String theMinute = theTime.substring(3,5);
	            	
	            	int theNumberHour = Integer.parseInt(theHour);
	            	String morningAfternoon = "am";
	            	
	            	//if hour is 13 or higher
	            	if(theNumberHour >= 13)
	            	{
	            		//subtract twelve and set flag to make it pm
	            		theNumberHour= theNumberHour - 12;
	            		morningAfternoon = "pm";
	            	}
	            	
	            	
                	//regularly print all events
                	//System.out.println("The full time date is: " + event.getStart().getDateTime());
                	System.out.printf("%s on %s %s at %s:%s %s\n", 
	                   event.getSummary(),
	                   d1.get(thisMonth),
	                   d2.get(today),
	                   theNumberHour,theMinute,morningAfternoon);
	                   //event.getStart().getDateTime().toString().substring(11,16));
	                	                
	            }
	        }		  
	}
}