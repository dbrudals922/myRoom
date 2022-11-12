package km.sender;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import km.project.GetByteArray;

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
//			버퍼에 데이터가 쌓여있다면 함께 버퍼를 비우면서 write
			os.flush();
//			출력 스트림인 os는 이미 위에서 null이었다면 아래 catch에서 잡혔을 것이기에 그냥 close()만해도 된다.
			os.close();
			inputStream.close();
			System.out.println(" [x] Sent Success  ");

			Files.delete(Paths.get(filepath));
			
//			socket으로 부터 얻어진 입력 또는 출력 스트림을 모두 close한다고해도 socket 역시 close하는게 좋다
//			입력 / 출력 스트림을 먼저 close하고 마지막에 socket close하는게 낫다.
//			소켓 close는 
//			if(socket != null && !socket.isClosed()) socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}