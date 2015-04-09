package voicePlusPlus.Outbound;

import org.junit.Test;
import static org.junit.Assert.*;


public class App {
	public static void main(String[] args) {
		int port = 500;
		
		ChannelPipelineFactory pf = AbstractOutboundPipelineFactory;
		
		SocketClient sc = SocketClient(port, AbstractOutboundPipelineFactory.getPipeline());
	}
}
