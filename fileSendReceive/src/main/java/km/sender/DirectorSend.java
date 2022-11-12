package km.sender;

//	lombok이 필요한가?
//	lombok이 getter/setter만 만들어주는 것이 아니고 어노테이션을 추가하면 생성자 등도 만들어주긴 한다. 지속적 업데이트 되기에 확인해야 함.
public class DirectorSend {
	private ISend a;
	
//	선택된 Builder
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
