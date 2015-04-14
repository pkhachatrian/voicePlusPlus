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
import com.google.gdata.client.*;
import com.google.gdata.client.contacts.*;
import com.google.gdata.data.*;
import com.google.gdata.data.contacts.*;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.URL;

public class GoogleContactsInstantiator {	
	private static HttpTransport httpTransport;
	private static JacksonFactory jsonFactory;
	private static Credential credential;
	private static Calendar service;
	private static GoogleTokenResponse response = null;
	static Event createdEvent = null;
		
	public static void setUp(String clientId, String clientSecret) throws IOException, GeneralSecurityException{
		
		
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    jsonFactory = JacksonFactory.getDefaultInstance();

	    // The clientId and clientSecret can be found in Google Developers Console

	    // Or your redirect URL for web based applications.
	    String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
	    String scope = "https://www.googleapis.com/auth/contacts";

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
	
	public static void printAllContacts(ContactsService myService)
		    throws ServiceException, IOException {
		  // Request the feed
		  URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
		  ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
		  // Print the results
		  System.out.println(resultFeed.getTitle().getPlainText());
		  for (ContactEntry entry : resultFeed.getEntries()) {
		    if (entry.hasName()) {
		      Name name = entry.getName();
		      if (name.hasFullName()) {
		        String fullNameToDisplay = name.getFullName().getValue();
		        if (name.getFullName().hasYomi()) {
		          fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
		        }
		      System.out.println("\\\t\\\t" + fullNameToDisplay);
		      } else {
		        System.out.println("\\\t\\\t (no full name found)");
		      }
		      if (name.hasNamePrefix()) {
		        System.out.println("\\\t\\\t" + name.getNamePrefix().getValue());
		      } else {
		        System.out.println("\\\t\\\t (no name prefix found)");
		      }
		      if (name.hasGivenName()) {
		        String givenNameToDisplay = name.getGivenName().getValue();
		        if (name.getGivenName().hasYomi()) {
		          givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
		        }
		        System.out.println("\\\t\\\t" + givenNameToDisplay);
		      } else {
		        System.out.println("\\\t\\\t (no given name found)");
		      }
		      if (name.hasAdditionalName()) {
		        String additionalNameToDisplay = name.getAdditionalName().getValue();
		        if (name.getAdditionalName().hasYomi()) {
		          additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
		        }
		        System.out.println("\\\t\\\t" + additionalNameToDisplay);
		      } else {
		        System.out.println("\\\t\\\t (no additional name found)");
		      }
		      if (name.hasFamilyName()) {
		        String familyNameToDisplay = name.getFamilyName().getValue();
		        if (name.getFamilyName().hasYomi()) {
		          familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
		        }
		        System.out.println("\\\t\\\t" + familyNameToDisplay);
		      } else {
		        System.out.println("\\\t\\\t (no family name found)");
		      }
		      if (name.hasNameSuffix()) {
		        System.out.println("\\\t\\\t" + name.getNameSuffix().getValue());
		      } else {
		        System.out.println("\\\t\\\t (no name suffix found)");
		      }
		    } else {
		      System.out.println("\t (no name found)");
		    }
		    System.out.println("Email addresses:");
		    for (Email email : entry.getEmailAddresses()) {
		      System.out.print(" " + email.getAddress());
		      if (email.getRel() != null) {
		        System.out.print(" rel:" + email.getRel());
		      }
		      if (email.getLabel() != null) {
		        System.out.print(" label:" + email.getLabel());
		      }
		      if (email.getPrimary()) {
		        System.out.print(" (primary) ");
		      }
		      System.out.print("\n");
		    }
		    System.out.println("IM addresses:");
		    for (Im im : entry.getImAddresses()) {
		      System.out.print(" " + im.getAddress());
		      if (im.getLabel() != null) {
		        System.out.print(" label:" + im.getLabel());
		      }
		      if (im.getRel() != null) {
		        System.out.print(" rel:" + im.getRel());
		      }
		      if (im.getProtocol() != null) {
		        System.out.print(" protocol:" + im.getProtocol());
		      }
		      if (im.getPrimary()) {
		        System.out.print(" (primary) ");
		      }
		      System.out.print("\n");
		    }
		    System.out.println("Groups:");
		    for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
		      String groupHref = group.getHref();
		      System.out.println("  Id: " + groupHref);
		    }
		    System.out.println("Extended Properties:");
		    for (ExtendedProperty property : entry.getExtendedProperties()) {
		      if (property.getValue() != null) {
		        System.out.println("  " + property.getName() + "(value) = " +
		            property.getValue());
		      } else if (property.getXmlBlob() != null) {
		        System.out.println("  " + property.getName() + "(xmlBlob)= " +
		            property.getXmlBlob().getBlob());
		      }
		    }
		    Link photoLink = entry.getContactPhotoLink();
		    String photoLinkHref = photoLink.getHref();
		    System.out.println("Photo Link: " + photoLinkHref);
		    if (photoLink.getEtag() != null) {
		      System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
		    }
		    System.out.println("Contact's ETag: " + entry.getEtag());
		  }
		}
	
	
	
}