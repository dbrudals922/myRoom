package com.swh.receiver;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.swh.project.FileData;

class FileReceiverH extends FileReceiver {

	public FileReceiverH(Socket socket) {
		super(socket);
		System.out.println("class 방식이다");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		try {

			//수신 버퍼의 최대 사이즈 지정
			int maxBufferSize = 3000;
			//버퍼 생성
			byte[] recvBuffer = new byte[maxBufferSize];
			//서버로부터 받기 위한 입력 스트림 뚫음
			InputStream is = socket.getInputStream();

			byte[] msg = new byte[3000];
			
			while(true){
				//버퍼(recvBuffer) 인자로 넣어서 받음. 반환 값은 받아온 size
				int a = is.read(msg);
				if(a>0){
					FileData data = toObject(msg, FileData.class);
					saveObject(data); // 저장
					System.out.println(" [x] Received Success  ");
				}
				else break;
			}

			is.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
