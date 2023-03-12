package com.ykm.portfolio.ws;

import java.io.Serializable;
import java.util.List;

import com.ykm.portfolio.ws.KeyVO.WEBSOCKET_RESPONSE;


public class ApiWebSocketObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WEBSOCKET_RESPONSE status;	// WEBSOCKET_RESPONSE 은 열거형(enum)
	private Object code;		// WEBSOCKET_CODE 은 열거형(enum)
	
	public ApiWebSocketObject(WEBSOCKET_RESPONSE status, Object code) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.code = code;
	}
	public WEBSOCKET_RESPONSE getStatus() {
		return status;
	}
	public void setStatus(WEBSOCKET_RESPONSE status) {
		this.status = status;
	}
	public Object getCode() {
		return code;
	}
	public void setCode(Object message) {
		this.code = message;
	}
}
