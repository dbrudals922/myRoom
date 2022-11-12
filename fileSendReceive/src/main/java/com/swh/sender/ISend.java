package com.swh.sender;

public interface ISend{
	public void connect();
	public void send(String fileName);
	public void close();
	 
}
