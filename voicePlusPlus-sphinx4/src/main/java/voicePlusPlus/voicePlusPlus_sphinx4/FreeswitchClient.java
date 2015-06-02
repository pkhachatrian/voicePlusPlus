package voicePlusPlus.voicePlusPlus_sphinx4;

import java.util.Map.Entry;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.freeswitch.esl.client.transport.message.EslHeaders.Name;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreeswitchClient {
    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    private String host = "localhost";
    private int port = 8021;
    private String password = "ClueCon"; 
    private int timeoutSeconds = 60;
    private Client client;
    
    public void ConnectToServer(){
    	client = new Client();
    	try {
			client.connect(host, port, password, timeoutSeconds);
			//client.sendAsyncApiCommand("divert_events", "on");
		} catch (InboundConnectionFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void AddEventListeners() {
        client.addEventListener( new IEslEventListener()
        {
            public void eventReceived( EslEvent event )
            {
                //log.info( "{}", event.getEventBodyLines() );
                if (event.getEventBodyLines().size() > 0){
                	String command = event.getEventBodyLines().get(0);
                    String arr[] = command.split(" ", 2);
                    String uuid = arr[0];   //the
                    command = arr[1];  
                	System.out.println(command);
                	App.processUtterance(command.toLowerCase());
                }
                if (event.getEventName() == "PLAYBACK_STOP"){
                	client.sendAsyncApiCommand("global_getvar", "command");
                }
            }
            public void backgroundJobResultReceived( EslEvent event )
            {
                //log.info( "Background job result received [{}]", event.getEventBodyLines() );
            }
            
            
        } );
        
        client.setEventSubscriptions("plain", "ALL");
        client.addEventFilter("Event-Name", "CUSTOM");
//        client.addEventFilter("Event-Name", "PLAYBACK_STOP");
//        client.addEventFilter("Event'Name", "")
//        client.sendAsyncApiCommand("eval", "divert_events on");
//        client.addEventFilter("Event-Name", "API");
        //client.addEventFilter("Speech_Type", "detected" );
    }
    
    public void InitiatePhoneCall(String sourcePhoneNumber){
    	client.sendAsyncApiCommand("originate", "sofia/gateway/gw_outbound/" + sourcePhoneNumber + " &javascript(invocabot_new.js)" );

    }
    
    public void InitiatePhoneCall(String sourcePhoneNumber, String destinationPhoneNumber){
    	client.sendAsyncApiCommand("originate", "sofia/gateway/gw_outbound/" + sourcePhoneNumber + " &bridge(sofia/gateway/gw_outbound/" + destinationPhoneNumber + ")");
    }
        
    public void do_connect() throws InterruptedException
    {
        Client client = new Client();
     
        client.addEventListener( new IEslEventListener()
        {
            public void eventReceived( EslEvent event )
            {
                log.info( "Event received [{}]", event );
            }
            public void backgroundJobResultReceived( EslEvent event )
            {
                log.info( "Background job result received [{}]", event );
            }
            
        } );
        
        log.info( "Client connecting .." );
        try
        {
            client.connect( host, port, password, 2 );
        }
        catch ( InboundConnectionFailure e )
        {
            log.error( "Connect failed", e );
            return;
        }
        log.info( "Client connected .." );
        
//      client.setEventSubscriptions( "plain", "heartbeat CHANNEL_CREATE CHANNEL_DESTROY BACKGROUND_JOB" );
        client.setEventSubscriptions( "plain", "all" );
        client.addEventFilter( "Event-Name", "heartbeat" );
        client.cancelEventSubscriptions();
        client.setEventSubscriptions( "plain", "all" );
        client.addEventFilter( "Event-Name", "heartbeat" );
        client.addEventFilter( "Event-Name", "channel_create" );
        client.addEventFilter( "Event-Name", "background_job" );
        client.sendSyncApiCommand( "echo", "Foo foo bar" );

//        client.sendSyncCommand( "originate", "sofia/internal/101@192.168.100.201! sofia/internal/102@192.168.100.201!" );
        
//        client.sendSyncApiCommand( "sofia status", "" );
        String jobId = client.sendAsyncApiCommand( "status", "" );
        log.info( "Job id [{}] for [status]", jobId );
        client.sendSyncApiCommand( "version", "" );
//        client.sendAsyncApiCommand( "status", "" );
//        client.sendSyncApiCommand( "sofia status", "" );
//        client.sendAsyncApiCommand( "status", "" );
        EslMessage response = client.sendSyncApiCommand( "sofia status", "" );
        log.info( "sofia status = [{}]", response.getBodyLines().get( 3 ) );
        
        // wait to see the heartbeat events arrive
        Thread.sleep( 25000 );
        client.close();
    }

    
    @Test
    public void sofia_contact()
    {
        Client client = new Client();
        try
        {
            client.connect( host, port, password, 2 );
        }
        catch ( InboundConnectionFailure e )
        {
            log.error( "Connect failed", e );
            return;
        }
        
        EslMessage response = client.sendSyncApiCommand( "sofia_contact", "internal/102@192.168.100.201" );

        log.info( "Response to 'sofia_contact': [{}]", response );
        for ( Entry<Name, String> header : response.getHeaders().entrySet() )
        {
            log.info( " * header [{}]", header );
        }
        for ( String bodyLine : response.getBodyLines() )
        {
            log.info( " * body [{}]", bodyLine );
        }
        client.close();
    }
}
