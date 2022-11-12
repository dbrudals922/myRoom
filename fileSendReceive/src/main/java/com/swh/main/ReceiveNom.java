package com.swh.main;

import com.swh.receiver.DirectorReceive;
import com.swh.yaml.YRead;

//	서버 GOOD
public class ReceiveNom {
	public static void main(String[] args) throws Exception{
		YRead yread = new YRead();
		DirectorReceive receiveDirector = new DirectorReceive(YRead.getReceiveC());
		receiveDirector.construct();
	}
}