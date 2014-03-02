import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class RicartAgrawala {

	private static boolean requestingCS;
	private static Timestamp myTimeStamp;
//	MessageSender msgSender=new MessageSender();
	MessageSender msgSender;
	Timestamp guestTimestamp;
	static int permitreplies;
	static int cscount;
	static int noofrequests;
	static int id;
	
//	PriorityQueue<Timestamp> pq = new PriorityQueue<Timestamp>();
//	PriorityQ pq = new PriorityQ<>();
	

	public static Timestamp getMyTimeStamp() {
		return myTimeStamp;
	}

	public static void setMyTimeStamp(Timestamp myTimeStamp) {
		RicartAgrawala.myTimeStamp = myTimeStamp;
	}

	public synchronized static boolean isRequestingCS() {
		return requestingCS;
	}

	public synchronized static void setRequestingCS(boolean requestingCS) {
		RicartAgrawala.requestingCS = requestingCS;
	}

	public synchronized void determineCriticalSectionEntry(Socket s,List message,int id,int guestid){
		this.id=id;
		this.guestTimestamp = Timestamp.valueOf((String)message.get(1));
//		if I'm requesting for CS,let me compare timestamps to determine if I should permit the host or defer the reply
		if(RicartAgrawala.isRequestingCS()){
			msgSender = new MessageSender();
			if(myTimeStamp.compareTo(guestTimestamp)>0){  //guest timestamp has higher priority
				msgSender.sendMessage(s, id, Message.PERMIT);
//				System.out.println("--MyTimeStamp :   "+myTimeStamp);
//				System.out.println("--Requesting TS : "+guestTimestamp);
//				System.out.println("--                  PERMIT sent*");
				
			} 
			if(myTimeStamp.compareTo(guestTimestamp)<0){ //my timestamp has higher priority
				DeferredRequests.add(guestTimestamp, s);
//				System.out.println("MyTimeStamp :   "+myTimeStamp);
//				System.out.println("Requesting TS : "+guestTimestamp);
//				System.out.println(".........................Reply has been deffered...");
				
			}
			if(myTimeStamp.compareTo(guestTimestamp)==0){
//				System.out.println("[[[[[[[[[[[  we are into a complex situation ]]]]]]]] "+id);
//				System.out.println("]]]]]]       Guest id : "+guestid);
				if(guestid<id){
					msgSender.sendMessage(s, id, Message.PERMIT);
					System.out.println("########                  PERMIT sent*");
				}else{
					DeferredRequests.add(guestTimestamp, s);
//				System.out.println(".........................Reply has been deffered...");
				}
			}
		}else{
			msgSender = new MessageSender();
			msgSender.sendMessage(s, id, Message.PERMIT);
			
		}
		
	}
	
//	public void printQSize(){
//		System.out.println("####################### Queue size is : "+pqueue.getSize());
//	}
	
	public synchronized void criticalSection(){
		CriticalSectionRequests csr = new CriticalSectionRequests();
		if(true){
			System.out.println("******** Entered critical section!!!!!! ********* "+ ++cscount);
				for(int i=0;i<10000;i++){
					;
			} 
		}
		
		RicartAgrawala.setRequestingCS(false);
		TimeStamp.setInstancetoNull();
		sendReplyToDeferredRequests();
		if(noofrequests<100){
			++noofrequests;
			csr.sendCSRequests(id);
		}
		
	}
	
	public static void sendReplyToDeferredRequests(){
		MessageSender msgSender=new MessageSender();
		Iterator it = DeferredRequests.deferredlist.entrySet().iterator();
		while(it.hasNext()){
			System.out.println("Iterating deffered list......................");
			if(!isRequestingCS()){
//				System.out.println("Actually sending permits to deferred requests...!!!!!!!!");
			Map.Entry pairs = (Map.Entry) it.next();
//			msgSender.sendMessage((Socket)it.next(), 1, Message.PERMIT);
//			DeferredRequests.toRemove.add(pairs.getKey());
			msgSender.sendMessage((Socket)pairs.getValue(), id, Message.PERMIT);
//			System.out.println("***************PERMIT REPLY SENT**************** "+ ++permitreplies);
//			it.remove();
			}
		}
		DeferredRequests.deferredlist = new HashMap<Timestamp, Socket>();
		}
	
	public static void removeItems(List toRemove){
		Iterator it = DeferredRequests.deferredlist.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry) it.next();
	}
		
	}}
