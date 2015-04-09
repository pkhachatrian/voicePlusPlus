package voicePlusPlus.Outbound;

import org.freeswitch.esl.client.outbound.AbstractOutboundPipelineFactory;

public class App {
	public static void main(String[] args) {
		int port = 10630;
		AbstractOutboundPipelineFactory pf = new SimpleHangupPipelineFactory();
		
		SocketClient sc = new SocketClient(port, pf);
		
		sc.start();
		System.out.println("in between start and stop");
		sc.stop();
	}
}
