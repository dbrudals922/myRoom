package km.sender;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

class FileSender extends Thread {
	Socket socket;
	DataOutputStream dos;
	FileInputStream fis;
	BufferedInputStream bis;
	protected String filename;
	protected String filepath;

	public FileSender(Socket socket, String filestr) {
		this.socket = socket;
		this.filename = filestr;
		this.filepath = "C:/Users/ykm09/Desktop/coding/_publishTestFile/"+filestr;
		try {
			// 데이터 전송용 스트림 생성
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*

//	FileSenderH 에서만 사용하는 걸 굳이 부모에 선언을?
//	FileSenderN에서 사용 못하게 하는게 낫다.
//	static X
	public byte[] toByteArray (Object obj)
	{
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			bos.close();
			bytes = bos.toByteArray();
		}
		catch (IOException ex) {
			//TODO: Handle the exception
		}
		return bytes;
	}
*/
	
//	Thread를 상속하는 바람에 이래된 것 같은데?
//	1. 아래 함수를 지우는 방법 - 부모 run함수가 동작하여 에러는 안나지만, 가급적이면 추상클래스로 하는게 낫다.
//	2. Thread 대신 Runnable인터페이스를 구현해도 된다. 
	@Override
	public void run() {
		
	}

}
