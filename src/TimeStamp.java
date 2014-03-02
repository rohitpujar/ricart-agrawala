import java.sql.Timestamp;
import java.util.Date;


public class TimeStamp {
    private static Timestamp instance;

    public static synchronized Timestamp getInstance() {
        if (instance == null) {
            instance = new Timestamp(new Date().getTime());
        }
        return instance;
    }
    
    public static void setInstancetoNull(){
    	instance=null;
    }
}
