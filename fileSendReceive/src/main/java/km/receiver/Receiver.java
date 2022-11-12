package km.receiver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class Receiver implements IReceive{
	private final static String QUEUE_NAME = "ykm";
	private static final String path = "C:/Users/ykm09/Desktop/coding/_receiveTestFile/";
	private static FileOutputStream outputStream = null;

//	여기서만 쓴다면 public static 대신 private
	public static void processResult(byte[] body){
		byte[] len = new byte[4];
		System.arraycopy(body, 0, len, 0, 4);
		byte[] data;
		try {
			data = new byte[Integer.parseInt(new String(len, "UTF-8"))];

			System.arraycopy(body, 4, data, 0, data.length);
			String strData = new String(data);

			if(strData.substring(0, 1).equals("S")){
				outputStream = new FileOutputStream(path+strData.substring(1) , true);
			}
			else if (strData.substring(0, 1).equals("D")){
				outputStream.write(Arrays.copyOfRange(data, 1, data.length));
			}
			else if (strData.substring(0, 1).equals("E")){
				outputStream.close();
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void receive() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("183.99.87.90");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		byte[] b = new byte[1024];

		Consumer consumer = new DefaultConsumer(channel) { // DefaultConsumer클래스 안에 있는 메소드 하나를 오버라이딩 하는 문법
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body) throws IOException {
			
				Receiver.processResult(body);
				//				receiver.saveObject(data);
				System.out.println(" [x] Received Success  ");
				//				String message = new String(body, "UTF-8");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}