package voicePlusPlus.Outbound;


/*
 * Copyright 2010 david varnes.

 *
 * Licensed under the Apache License, version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import org.freeswitch.esl.client.internal.AbstractEslClientHandler;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.handler.execution.ExecutionHandler;


public abstract class AbstractOutboundClientHandler extends AbstractEslClientHandler
{

    @Override
    public void channelConnected( ChannelHandlerContext ctx, ChannelStateEvent e ) throws Exception
    {
        // Have received a connection from FreeSWITCH server, send connect response
        log.debug( "Received new connection from server, sending connect message" );
        
        EslMessage response = sendSyncSingleLineCommand( ctx.getChannel(), "connect" );
        // The message decoder for outbound, treats most of this incoming message as an 'event' in 
        // message body, so it parse now
        EslEvent channelDataEvent = new EslEvent( response, true );
        // Let implementing sub classes choose what to do next
        handleConnectResponse( ctx, channelDataEvent );
    }

    protected abstract void handleConnectResponse( ChannelHandlerContext ctx, EslEvent event );

    @Override
    protected void handleAuthRequest( ChannelHandlerContext ctx )
    {
        // This should not happen in outbound mode
        log.warn( "Auth request received in outbound mode, ignoring" ); 
    }

    @Override
    protected void handleDisconnectionNotice()
    {
        log.debug( "Received disconnection notice" );
    }    
}