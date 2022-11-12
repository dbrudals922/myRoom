package km.main;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import km.sender.DirectorSend;
import km.yaml.YRead;

//	Client GOOD
public class WatchDirectory{

	private String fileName;

	public static void main(String[] args) throws Exception {

		WatchService watchService
		= FileSystems.getDefault().newWatchService();

		Path path = Paths.get("C:/Users/ykm09/Desktop/coding/_publishTestFile");

		path.register(
				watchService, 
				StandardWatchEventKinds.ENTRY_CREATE, 
				StandardWatchEventKinds.ENTRY_DELETE, 
				StandardWatchEventKinds.ENTRY_MODIFY);
		
		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				if(StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())){
					System.out.println("파일이 감지되었습니다.");

					DirectorSend sendDirector = new DirectorSend(YRead.getSendC());
					sendDirector.construct(event.context().toString());
				}
			}
			key.reset();
		}
	}
}