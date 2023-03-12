package com.ykm.portfolio.ws;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;


public class WebSocketFactory {
	private static WebSocketFactory instance = new WebSocketFactory();
	
	private Map<String, WebSocketSession> sessionList = new HashMap<>();
	public Map<String, WebSocketSession> getSessionList() {
		return sessionList;
	}
	private void addSession(WebSocketSession session) {
		sessionList.put(session.getId(), session);
	}
	private void removeSession(WebSocketSession session) {
		sessionList.remove(session.getId());
	}

	private static WebSocketFactory get() {
		// TODO Auto-generated constructor stub
		if(instance == null) instance = new WebSocketFactory();
		return instance;
	}
	public static Collection<WebSocketSession> getList() {
		return get().getSessionList().values();
	}
	public static void add(WebSocketSession session) {
		get().addSession(session);
	}
	public static void remove(WebSocketSession session) {
		get().removeSession(session);
	}
}
