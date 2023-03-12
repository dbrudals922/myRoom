package com.ykm.portfolio.ws;

public class KeyVO {

	public enum WEBSOCKET_RESPONSE {
		FLOWSUCCESS, 
		FLOWFAIL
	}
	public enum WEBSOCKET_CODE {
		FLOWDOWN,
		FLOWSETTING,
		FLOWRUNNING,
		FLOWRUNNED,
		
		FLOWERROR,
	}
}
