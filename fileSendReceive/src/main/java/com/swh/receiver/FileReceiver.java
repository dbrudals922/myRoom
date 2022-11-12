package com.swh.receiver;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.swh.project.FileData;

public abstract class FileReceiver extends Thread {
	protected Socket socket;
//	private DataInputStream dis;
//	private FileOutputStream fos;
//	private BufferedOutputStream bos;
//	private String filename;

	public FileReceiver(Socket socket) {
		this.socket = socket;
	}

	public static void saveObject(FileData data) throws IOException {
		FileOutputStream outputStream = new FileOutputStream("C:/Users/ykm09/Desktop/coding/_receiveTestFile/"+data.getFileName(), true);
		outputStream.write(data.getData2(), 0, data.getData());
		outputStream.close();
	}

	public static FileData toObject (byte[] bytes, Class<FileData> type)
	{
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
	
	public abstract void run();
	
}
