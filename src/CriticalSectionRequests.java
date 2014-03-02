import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class CriticalSectionRequests extends Thread {
	 static int nodeid;
	/*public void sendRequests(int id){
		this.nodeid = id;
		MessageSender m = new MessageSender();
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% my id is : "+id);
		//List<Socket> connections = SocketConnections.getSocketConnections();
		Map<Integer,Socket> connections = SocketConnections.getSocketConnections();
		System.out.println("########### Total sockets present : "+connections.size());
		for(int i=0;i<connections.size();i++){
//			System.out.println("$#$#$#$ i is now : "+i);
//			System.out.println("|$%%$%$%$  and my id is "+id);
			System.out.println("^^^^Total sockets present in CSRequests ^^^^^"+connections.size());
			if(i == id){
				++i;
//				System.out.println("$$$$$$$$ i incremented $$$$$$$ "+i);
			}
			if(connections.get(i)==null){
				System.out.println("&&&&&&&&&&&&&& Found null.."+i);
				System.exit(0);
//			m.createOutputStream(connections.get(i), id)
			}
			else{
//				System.out.println("^%$^%$ creating output stream with "+connections.get(i).getPort());
				m.createOutputStream(connections.get(i),i);
			}
			
		}
		
		//printing only the hashmap that has connections
//		SocketConnections.displaySockets();
//		sendReadyStatusToNodeZero();
	}*/
	 
	 public void sendCSRequests(int id) {
		 int cscount=0;
		 
//		 while(cscount<20){
			 
			 MessageSender m = new MessageSender();
			 Iterator it = SocketConnections.sockets.entrySet().iterator();
			 while(it.hasNext()){
				 try {
					Thread.sleep(randInt(10, 100));
				} catch (InterruptedException e) {
					System.out.println("********** Interuppted in SendCSRequests method *********");
					e.printStackTrace();
				}
				 
				 Map.Entry pairs = (Map.Entry) it.next();
//				System.out.println("=== "+pairs.getKey()+" "+pairs.getValue()+" ===");
				 RicartAgrawala.setRequestingCS(true);
				 m.createOutputStream((Socket)pairs.getValue(),id,Message.REQUEST);
//				System.out.println(pairs.getKey() + " = " + pairs.getValue());
			 }
			 cscount++;
//			 System.out.println("Now cscount is : "+cscount);
		 }
		
//	 }
	
	public static void sendReadyStatusToNodeZero(int nodeid){
		Message msg = Message.READY;
		MessageSender m = new MessageSender(Message.READY.toString());
		if(nodeid!=0)
		m.sendMessage(SocketConnections.sockets.get(0), nodeid, msg);
		
	}
	
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
//	public void run(){
//		CriticalSectionRequests csrequest=new CriticalSectionRequests();
//		csrequest.sendCSRequests(0);
//		
//	}
}
