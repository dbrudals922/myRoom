package com.swh.rabbitmq.project;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class Receiver {
	private final static String QUEUE_NAME = "ykm";
	private final String path = "C:/Users/ykm09/Desktop/coding/_receiveTestFile/";
	
	public void saveObject(FileData data) throws IOException {
		FileOutputStream outputStream = new FileOutputStream("C:/Users/ykm09/Desktop/coding/_receiveTestFile/"+data.getFileName(), true);
		outputStream.write(data.getData2(), 0, data.getData());
		outputStream.close();
	}

	public static void main(String[] argv) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("183.99.87.90");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		byte[] b = new byte[16384];
		
		Consumer consumer = new DefaultConsumer(channel) { // DefaultConsumer클래스 안에 있는 메소드 하나를 오버라이딩 하는 문법
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body) throws IOException {
				FileData data = (FileData) SerializationUtils.deserialize(body);
				Receiver receiver = new Receiver();
				receiver.saveObject(data);
				System.out.println(" [x] Received Success  ");
				//				String message = new String(body, "UTF-8");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}