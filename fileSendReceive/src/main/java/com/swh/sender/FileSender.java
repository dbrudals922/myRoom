package com.swh.sender;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class FileSender extends Thread {
	protected Socket socket;
	private DataOutputStream dos;
//	private FileInputStream fis;
//	private BufferedInputStream bis;
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

	public static byte[] toByteArray (Object obj)
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

	public abstract void run();

}
