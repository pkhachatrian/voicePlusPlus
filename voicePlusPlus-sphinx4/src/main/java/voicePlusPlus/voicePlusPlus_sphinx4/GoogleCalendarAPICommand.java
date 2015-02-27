package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Date;

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

}
