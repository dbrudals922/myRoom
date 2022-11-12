package km.yaml;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import km.receiver.FileReceiver;
import km.receiver.IReceive;
import km.sender.ISend;

public class YRead {
	
	private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

	public static ISend getSendC() throws Exception {
		Ysr ysr = mapper.readValue(new File("src/main/resources/name.yml"), Ysr.class);
		Class<?> finedClass = Class.forName(ysr.getSend());
		ISend sender = (ISend) finedClass.newInstance(); // 생성자 호출
		return sender;
	}

	public static IReceive getReceiveC() throws Exception {
		Ysr ysr = mapper.readValue(new File("src/main/resources/name.yml"), Ysr.class);
		Class<?> finedClass = Class.forName(ysr.getReceive());
		IReceive receiver = (IReceive) finedClass.newInstance();
		return receiver;
	}

	public static FileReceiver getTypeC() throws Exception {
		Ysr ysr = mapper.readValue(new File("src/main/resources/name.yml"), Ysr.class);
		Class<?> finedClass = Class.forName(ysr.getReceive());
		Constructor<?> ctor = finedClass.getConstructor();
		FileReceiver type = (FileReceiver) ctor.newInstance();
		return type;
	}

}
