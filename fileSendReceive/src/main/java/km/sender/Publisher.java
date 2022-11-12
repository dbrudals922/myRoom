package km.sender;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import km.project.GetByteArray;

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

//	깔끔 GOOD
public class Publisher implements ISend {

	private final static String QUEUE_NAME = "ykm";
	private Channel channel;
	private Connection connection;
	private String filepath;
	private int i = 0;

	@Override
	public void connect(){
		try{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("183.99.87.90");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void send(String filename){
		byte[] bytes = null;
		try{
			byte[] b = new byte[1024];
			filepath = "C:/Users/ykm09/Desktop/coding/_publishTestFile/"+filename;

			FileInputStream inputStream = new FileInputStream(filepath);

			int d = -1;
			byte[] da = new byte[1024];

			GetByteArray getByte = new GetByteArray();
			bytes = getByte.title(filename);
			//					System.out.println("제목");
			channel.basicPublish("", QUEUE_NAME, null, bytes);

			int data = -1;
			while((data=inputStream.read(b))!=-1){
				bytes = getByte.data(b);
				channel.basicPublish("", QUEUE_NAME, null, bytes);
			}

			//		channel.basicPublish("", QUEUE_NAME, null, data2);
			inputStream.close();
			System.out.println(" [x] Sent Success  ");

			bytes = getByte.end();
			channel.basicPublish("", QUEUE_NAME, null, bytes);

			Files.delete(Paths.get(filepath));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void close(){
		try{
			channel.close();
			connection.close();
		}catch(Exception e){
			e.printStackTrace();

		}
	}
}