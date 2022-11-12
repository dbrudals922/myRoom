package km.receiver;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import km.project.FileData;


//	FileReceiverH, FileReceiverN 자식클래스를 만든 건 GOOD
public class FileReceiver extends Thread {
	Socket socket;
	DataInputStream dis;
	FileOutputStream fos;
	BufferedOutputStream bos;
	String filename;
	long start;

	public FileReceiver(Socket socket, long starttime) {
		this.socket = socket;
		this.start = starttime;
	}
	
/*

//	FileReceiverH 에서만 사용하는 걸 굳이 부모에 선언을?
//	FileReceiverN에서 사용 못하게 하는게 낫다.
//	static X
	public void saveObject(FileData data) throws IOException {
		FileOutputStream outputStream = new FileOutputStream("C:/Users/ykm09/Desktop/coding/_receiveTestFile/"+data.getFileName(), true);
		outputStream.write(data.getData2(), 0, data.getData());
		outputStream.close();
	}

//	FileReceiverH 에서만 사용하는 걸 굳이 부모에 선언을?
//	FileReceiverN에서 사용 못하게 하는게 낫다.
//	static X
	public FileData toObject (byte[] bytes, Class<FileData> type) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		}
		catch (IOException ex) {
			ex.printStackTrace();
			//TODO: Handle the exception
		}
		catch (ClassNotFoundException ex) {
			//TODO: Handle the exception
		}

		return (FileData) obj;	

	}
	*/
//	Thread를 상속하는 바람에 이래된 것 같은데?
//	1. 아래 함수를 지우는 방법 - 부모 run함수가 동작하여 에러는 안나지만, 가급적이면 추상클래스로 하는게 낫다.
//	2. Thread 대신 Runnable인터페이스를 구현해도 된다. 
	@Override
	public void run(){
		
	}

}
