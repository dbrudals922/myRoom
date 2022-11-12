package km.sender;

//	파일 내용을 전송하는 클라이언트는
//	연결 시도
//	전송
//	연결 종료
public interface ISend{
	public void connect();
	public void send(String fileName);
	public void close();
	 
}
