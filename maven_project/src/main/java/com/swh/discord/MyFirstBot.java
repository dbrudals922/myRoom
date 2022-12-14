package com.swh.discord;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class MyFirstBot extends ListenerAdapter {

	public static void main(String[] args) throws LoginException {

		// 기본 jda를 만들고
		JDA jda = JDABuilder.createDefault("ODY2MTk3OTU2NDIyOTI2Mzc2.YPPDtQ.Wm1u0SZK3JG6_Z6G4dZnjEx6jMU").build();
		
//		
//		ODY2MTk3OTU2NDIyOTI2Mzc2.YPPDtQ.Wm1u0SZK3JG6_Z6G4dZnjEx6jMU

		// jda에 이벤트를 감지하는 리스너를 넣는다.
		jda.addEventListener(new MyFirstBot());
		;
		MyFirstBot myFirstBot = new MyFirstBot();
		myFirstBot.sendMessage(jda.getTextChannelById("880387990344585276"), "아농아농");
		
	}
	
	void sendMessage(TextChannel ch, String msg) {
	    ch.sendMessage(msg).queue();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		// 받은 메세지 내용이 !ping이라면
		if (event.getMessage().getContentRaw().equals("!ping")) {
			// pong라는 내용을 보낸다.
			event.getChannel().sendMessage("pong!").queue();
		}
	}
	
	
}