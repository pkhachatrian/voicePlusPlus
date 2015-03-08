package voicePlusPlus.voicePlusPlus_sphinx4;

public class APICommand {
	public String API;
	public String command;
	
	public APICommand() {
		this.API = "";
		this.command = "";
	}
	
	public APICommand(String api, String command){
		this.API = api;
		this.command = command;
	}
}
