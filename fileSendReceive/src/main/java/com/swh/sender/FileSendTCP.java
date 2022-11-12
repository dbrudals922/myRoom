package com.swh.sender;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang3.ObjectUtils;


public class FileSendTCP implements ISend{
	
	Socket socket = null;
	
	@Override
	public void connect(){
		String serverIp = "127.0.0.1";
		int serverPort = 8081;
		try {
			// 서버 연결
			socket = new Socket(serverIp, serverPort); // socket(),connect();
			System.out.println("서버에 연결되었습니다.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(String fileName){
//		FileSender f = new FileSenderH(socket,fileName); // class
		FileSender fs = new FileSenderN(socket,fileName); // 배열
		fs.start();
	}
	
	@Override
	public void close(){
		
	}
}

