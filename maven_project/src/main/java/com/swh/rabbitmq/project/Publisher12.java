package com.swh.rabbitmq.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <pre>
 * kr.co.swh.lecture.opensource.rabbitmq 
 * Publisher.java
 *
 * 설명 :
 * </pre>
 * 
 * @since : 2020. 6. 14.
 * @author : tobby48
 * @version : v1.0
 */
public class Publisher12 {
	
	private final static String QUEUE_NAME = "ykm";
	
	public void publish(String fileName) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("183.99.87.90");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		byte[] arr = new byte[500];
		byte[] b = new byte[16384];
		String sPath = "C:/Users/ykm09/Desktop/coding/_publishTestFile/"+fileName;
		
		FileInputStream inputStream = new FileInputStream(sPath);
		FileData d = new FileData(fileName);
		
		int data = -1;
		while((data=inputStream.read(b))!=-1){
			d.setData(data);
			d.setData2(b);
//			outputStream.write(d.getData());
			byte[] data2 = SerializationUtils.serialize(d);
			channel.basicPublish("", QUEUE_NAME, null, data2);
			Arrays.fill(arr, (byte)0);
		}

//		channel.basicPublish("", QUEUE_NAME, null, data2);
		inputStream.close();
		System.out.println(" [x] Sent Success  ");

		Files.delete(Paths.get(sPath));
		
		
		channel.close();
		connection.close();
	}
}