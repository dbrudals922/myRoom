package com.ykm.portfolio.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykm.portfolio.ResultRepository;
import com.ykm.portfolio.ws.KeyVO.WEBSOCKET_RESPONSE;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LinkWebSocketHandler extends TextWebSocketHandler {

	private final Logger log = LoggerFactory.getLogger(LinkWebSocketHandler.class);

	private final Object lock = new Object();
	private ObjectMapper objectMapper = new ObjectMapper();

	private final ResultRepository resultRepository;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
//		log.info("payload : " + payload);
		
		
		List<Long> a = new ArrayList<>(Arrays.asList(Long.parseLong(payload), (long)1));
		
        broadcast(WEBSOCKET_RESPONSE.FLOWSUCCESS, a);
	}

	public void broadcast(WEBSOCKET_RESPONSE status, Object code) {
		synchronized (lock) {
			ApiWebSocketObject obj = new ApiWebSocketObject(status, code);
			for (Iterator<WebSocketSession> itr = WebSocketFactory.getList().iterator(); itr.hasNext();) {
				WebSocketSession session = itr.next();
				try {
					if (session.isOpen())
						session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(obj)));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//    				e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		synchronized (lock) {

			WebSocketFactory.add(session);
			log.info(session + " 클라이언트 접속");

			List<Object> re = resultRepository.selectResult();
			
			for(Object r : re) {
				broadcast(WEBSOCKET_RESPONSE.FLOWSUCCESS, r); // 전송
			}
			
//			List<ResultEntity> a = resultRepository.selectResult();
			
			this.getData();
			
//			for(List<Long> r: this.getData2()) {
//				broadcast(WEBSOCKET_RESPONSE.FLOWSUCCESS, r); // 전송
//			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		synchronized (lock) {
			log.info(session + " 클라이언트 접속 해제");
			WebSocketFactory.remove(session);
		}
	}

	public List<List> getData2() throws Exception {

		Random rand = new Random();
		List<List> list = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long millis = System.currentTimeMillis();
		Date date1 = sdf.parse("2023-12-01");

		for (int i = 60; i > 0; i--) {
//			date1.setMinutes(i)

			List<Long> a = new ArrayList<>();

			int n = rand.nextInt(20);

			long date = date1.getTime();
			a.add(millis + i * 1000);
			a.add((long) n);

			list.add(a);
		}

		return list;
	}

	public void getData() throws Exception {
		List<Object> re = resultRepository.selectResult();
		
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		long millis = System.currentTimeMillis();
//
//		for (ResultEntity r : re) {
//			Date date1 = sdf.parse();
//			System.out.println(date1.getTime());
//		}
	}

}