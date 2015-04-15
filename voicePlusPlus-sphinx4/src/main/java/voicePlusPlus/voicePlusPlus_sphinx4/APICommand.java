package voicePlusPlus.voicePlusPlus_sphinx4;

public class APICommand {
	private String API;
	private String command;
	
	public APICommand() {
		this.API = "";
		this.command = "";
	}

	public APICommand(String API, String command) {
		this.API = API;
		this.command = command;
	}

	public String getAPI() {
		return API;
	}

	public void setAPI(String aPI) {
		API = aPI;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
