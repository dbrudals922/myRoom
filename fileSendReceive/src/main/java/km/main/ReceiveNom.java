package km.main;

import km.receiver.DirectorReceive;
import km.yaml.YRead;

//	서버 GOOD
public class ReceiveNom {
	public static void main(String[] args) throws Exception{
		YRead yread = new YRead();
		DirectorReceive receiveDirector = new DirectorReceive(YRead.getReceiveC());
		receiveDirector.construct();
	}
}