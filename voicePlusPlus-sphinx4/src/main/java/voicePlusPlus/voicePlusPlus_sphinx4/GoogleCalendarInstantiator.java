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
import java.util.Collections;



//Event libraries
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.client.util.DateTime;
import java.util.Date;

public class GoogleCalendarInstantiator {

	
	public void setUp(String clientId, String clientSecret) throws IOException, GeneralSecurityException{
		
		
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	    // The clientId and clientSecret can be found in Google Developers Console

	    // Or your redirect URL for web based applications.
	    String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
	    String scope = "https://www.googleapis.com/auth/calendar";

	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(
	        httpTransport, jsonFactory, clientId, clientSecret, Collections.singleton(scope));
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
	    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUrl)
	        .execute();
	    // End of Step 2

	    Credential credential = new GoogleCredential.Builder()
	        .setTransport(httpTransport)
	        .setJsonFactory(jsonFactory)
	        .setClientSecrets(clientId, clientSecret)
	        .build().setFromTokenResponse(response);

	    Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credential)
	        .setApplicationName("voicePlusPlus").build();
			
	}
	
	public void newEvent(/*event details who/what/when*/){
		/*DOES NOTHING YET*/
		
		// Initialize Calendar service with valid OAuth credentials
		Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
		    .setApplicationName("voicePlusPlus").build();

		// Create and initialize a new event
		Event event = new Event();
		event.setSummary("Appointment");
		event.setLocation("Somewhere");

		ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
		attendees.add(new EventAttendee().setEmail("attendeeEmail"));
		// ...
		event.setAttendees(attendees);

		Date startDate = new Date();
		Date endDate = new Date(startDate.getTime() + 3600000);
		DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
		event.setEnd(new EventDateTime().setDateTime(end));

		// Insert the new event
		Event createdEvent = service.events().insert('primary', event).execute();

		System.out.println(createdEvent.getId());
	
	}
}