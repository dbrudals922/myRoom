package com.swh.sender;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.swh.project.GetByteArray;

class FileSenderN extends FileSender {

	public FileSenderN(Socket socket, String filestr) {
		super(socket, filestr);
		System.out.println("byte array 방식이다");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {

		byte[] bytes = null;
		try {
			FileInputStream inputStream = new FileInputStream(this.filepath);

			int d = -1;
			byte[] da = new byte[1024];

			//서버로 내보내기 위한 출력 스트림 뚫음
			OutputStream os = socket.getOutputStream();

			GetByteArray getByte = new GetByteArray();
			bytes = getByte.title(this.filename);
//			System.out.println("제목");

			os.write(bytes);
			os.flush();

			while((d=inputStream.read(da))!=-1){

				//				System.out.println(bytes.length);
				//출력 스트림에 데이터 씀
				bytes = getByte.data(da);
				os.write(bytes);
				//보냄
				os.flush();
			}

			bytes = getByte.end();
			os.write(bytes);
			os.flush();
			os.close();
			inputStream.close();
			System.out.println(" [x] Sent Success  ");

			Files.delete(Paths.get(filepath));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}