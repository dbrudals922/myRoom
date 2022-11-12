package com.swh.receiver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

class FileReceiverN extends FileReceiver {

	public FileReceiverN(Socket socket) {
		super(socket);
		System.out.println("byte array 방식이다");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try {
			InputStream is;
			FileOutputStream outputStream = null;
			while(true){
				//버퍼 생성
				byte[] recvBuffer = new byte[4];
				//서버로부터 받기 위한 입력 스트림 뚫음
				is = socket.getInputStream();

				int b = is.read(recvBuffer);
				//버퍼(recvBuffer) 인자로 넣어서 받음. 반환 값은 받아온 size

				byte[] msg = new byte[Integer.parseInt(new String(recvBuffer, "UTF-8"))];
//				System.out.println(Integer.parseInt(new String(recvBuffer, "UTF-8")));

				is.read(msg);
				String data = new String(msg);
				
				if(data.substring(0, 1).equals("S")){
					outputStream = new FileOutputStream("C:/Users/ykm09/Desktop/coding/_receiveTestFile/"+data.substring(1) , true);
				}
				else if (data.substring(0, 1).equals("D")){
					outputStream.write(Arrays.copyOfRange(msg, 1, msg.length));
				}
				else if (data.substring(0, 1).equals("E")){
					outputStream.close();
//					System.out.println("끝났다");
					break;
				}
				//				System.out.println(" [x] Received Success  ");
			}

			is.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
