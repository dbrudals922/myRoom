package km.sender;

import java.io.IOException;
import java.net.Socket;


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
//		close 실종. FileSenderN에서 소켓 close해주낭?
		
	}
}

