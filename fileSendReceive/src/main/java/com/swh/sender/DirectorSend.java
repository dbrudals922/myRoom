package com.swh.sender;

import lombok.Data;

@Data
public class DirectorSend {
	private ISend a;
	
	public DirectorSend(ISend a) {
		// TODO Auto-generated constructor stub
		this.a = a;
	}

	public boolean construct(String fileName){
		try{
		this.a.connect();
		this.a.send(fileName);
		this.a.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
