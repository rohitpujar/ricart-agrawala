import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeferredRequests {

	static Map<Timestamp,Socket> deferredlist = new HashMap<Timestamp, Socket>();
	static List toRemove=new ArrayList<>();
	
	
	public static void add(Timestamp ts,Socket s){
		deferredlist.put(ts, s);
	}
	
	public static int getSize(){
		return deferredlist.size();
	}
	
}
