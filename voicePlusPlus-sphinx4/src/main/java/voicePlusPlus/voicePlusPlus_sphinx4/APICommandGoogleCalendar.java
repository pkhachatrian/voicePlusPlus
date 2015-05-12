package voicePlusPlus.voicePlusPlus_sphinx4;

public class APICommandGoogleCalendar extends APICommand {
	public String eventId;
	
	public APICommandGoogleCalendar() {
		super();
		this.eventId = "";
	}
	
	public APICommandGoogleCalendar(String API, String command, String eventId) {
		super(API, command);
		this.eventId = eventId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}


}