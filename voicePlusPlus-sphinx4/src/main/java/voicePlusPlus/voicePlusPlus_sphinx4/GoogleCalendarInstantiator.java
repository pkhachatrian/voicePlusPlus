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
import java.util.ArrayList;
import java.util.Collections;
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
	    
	    
	    /*COMMENT OUT BELOW IF NEED TO REUSE CODE*/
	    // Step 1: Authorize
	    String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build();

	    // Point or redirect your user to the authorizationUrl.
	    System.out.println("Go to the following link in your browser:");
	    System.out.println(authorizationUrl);

	    // Read the authorization code from the standard input stream.
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("What is the authorization code?");
	    
	    
	    
	    String code = in.readLine();
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

	public static void listEvents(){
		
		//create a reference to current instantaneous time
		DateTime now = new DateTime(System.currentTimeMillis());
		
		// Iterate over the events in the specified calendar
		//String pageToken = null;  <-- not used for whatever reason
		//do {
		  Events events = null;
		try {
			//Original
			//events = service.events().list("primary").setPageToken(pageToken).execute();
			//Testing
			events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		  java.util.List<Event> items = events.getItems();
		  
		  //Goal: 
		  //import date get date function
		  //if date == today then classify as today or tomorrow
		  
		  if (items.size() == 0) {
	            System.out.println("No upcoming events found.");
	        } else {
	            System.out.println("Upcoming events");
	            for (Event event : items) {
	                DateTime start = event.getStart().getDateTime();
	                if (start == null) {
	                    start = event.getStart().getDate();
	                }
	                System.out.printf("%s (%s)\n", event.getSummary(), start);
	            }
	        }
		  
		  
		  
//		  for (Event event : items) {
//			String month = event.getStart().toString().substring(19, 20);
//			String day = event.getStart().toString().substring(21, 22);
//			
//			System.out.println("\n" + "Month: " + month + " Day: " + day + " --- ");
//			System.out.print(event.getSummary());
//		
//		  }
		  
//		  for (Event event : items) {
//				System.out.println("The full way: ");
//				System.out.println(event.getStart() + ": ");
//			    System.out.print(event.getSummary());
//			  }
	//	  pageToken = events.getNextPageToken();
		//} while (pageToken != null);
		

	}
	
	
	
}