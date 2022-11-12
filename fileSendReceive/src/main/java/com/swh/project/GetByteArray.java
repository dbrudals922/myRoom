package com.swh.project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class GetByteArray {
	public byte[] title(String filename) throws IOException{
		// 제목
		byte[] filenameByte = filename.getBytes();

		ByteArrayOutputStream stream = new ByteArrayOutputStream(); //BYTES_BODY는 메세지의 전체 길이(bytes)이다.
		String fileNameLen = String.format("%04d", "S".getBytes().length + filenameByte.length); // left padding 0 (4자리)
		// 알아서 뒤로 값을 붙혀줌.
		stream.write(Arrays.copyOfRange(fileNameLen.getBytes(), 0, 4));
		stream.write(Arrays.copyOfRange("S".getBytes(), 0, "S".getBytes().length));
		stream.write(Arrays.copyOfRange(filenameByte, 0, filenameByte.length));	

		byte[] bytes = stream.toByteArray();

		return bytes;
	}

	public byte[] data(byte[] data) throws IOException{
		ByteArrayOutputStream stream = new ByteArrayOutputStream(); //BYTES_BODY는 메세지의 전체 길이(bytes)이다.

		// data를 보냄
		String fileNameLen = String.format("%04d", "D".getBytes().length + data.length); // left padding 0 (4자리)
		stream.write(Arrays.copyOfRange(fileNameLen.getBytes(), 0, fileNameLen.getBytes().length));
		stream.write(Arrays.copyOfRange("D".getBytes(), 0, "D".getBytes().length));
		stream.write(Arrays.copyOfRange(data, 0, data.length));

		byte[] bytes = stream.toByteArray();

		return bytes;
	}
	public byte[] end() throws IOException{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		// end를 보냄
		String fileNameLen = String.format("%04d", "E".getBytes().length); // left padding 0 (4자리)
		stream.write(Arrays.copyOfRange(fileNameLen.getBytes(), 0, fileNameLen.getBytes().length));
		stream.write(Arrays.copyOfRange("E".getBytes(), 0, "E".getBytes().length));

		byte[] bytes = stream.toByteArray();
		return bytes;

	}
}
