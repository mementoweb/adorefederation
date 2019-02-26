package gov.lanl.permalink;



import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StressTestNetkernel { 
    /**
     * @param args
     */
    static int  srvnum=10;
    public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub
	
        StressTestNetkernel t = new StressTestNetkernel();
	BlockingQueue<String> q = new ArrayBlockingQueue<String>(1000);
	BufferedReader in = new BufferedReader(new FileReader(args[0]));
	ExecutorService es = Executors.newCachedThreadPool();
	ExecutorService ps = Executors.newSingleThreadExecutor();
	IdProducer p= t.new IdProducer(q,in);
	ps.execute(p);
	
        
        for (int i = 0; i < srvnum; i++) {
            es.execute(new RecordPuller("th"+i,q));
        }
        
        es.shutdown();
        ps.shutdown();

    }
    
    public class IdProducer  extends Thread {
	 private BlockingQueue<String> q;
	 private boolean done=false;
	 BufferedReader in;
	 IdProducer(BlockingQueue<String> q,BufferedReader in){
	     this.q=q;
	     this.in=in;
	 }
	 public void run() {
	     try {  while (!done) {
		 //System.out.println("here");
		   readId();
	        }
	     }
	     catch(Exception e) { }
	     System.out.println();
	     }
	 
	 public void readId() throws Exception{
	     String line= in.readLine();
	     if (line!=null){
		String u = "http://penguin.lanl.gov:8080/adore-openurl-resolver-frontend?";
		u=u+ "url_ver=Z39.88-2004&svc_id=info:lanl-repo/svc/getMARCXML&rft_id=";
		java.net.URLEncoder.encode(line, "UTF8");
		//System.out.println(u);
		 q.put(u+line);
	     }
	     else {
		 done=true;
		 for (int i=0;i<srvnum;i++) {
		     {q.put("done");}
		 }
	     }
	 }
    } //inner class
    } //class


