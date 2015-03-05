package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Date;

import com.google.api.client.http.*;
//import com.google.api.services.calendar.*;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.Calendar;
import com.google.api.client.util.DateTime;


public class GoogleCalendarAPICommand extends APICommand {

	public String EventDescription;
	public Date StartDate;
	public Date EndDate;
	
	public GoogleCalendarAPICommand(){
		super();
		this.EventDescription = "";
		this.StartDate = null;
		this.EndDate = null;
	}
	
	public GoogleCalendarAPICommand(String api) {
		super(api);
		this.EventDescription = "";
		this.StartDate = null;
		this.EndDate = null;
	}
	
	
	public GoogleCalendarAPICommand(String api, String eventDescription, Date startDate, Date endDate)
	{
		super(api);
		this.EventDescription = eventDescription;
		this.StartDate = startDate;
		this.EndDate = endDate;	
	}
	
	public static Date FindStartDate(String[] keywords){
		for (String keyword : keywords)
		{
			String category = Keyword.Get(keyword);
			if (category == "DATE" || category == "TIME")
			{
				System.out.println(keyword);
			}
		}
		return null;
	}
	
	public static Date FindEndDate(String[] keywords){
		return null;
	}

}