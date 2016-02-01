import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class FileRecieve implements Runnable{
	
  private String server;
  private String fileName;;

public FileRecieve(String server, String fileName){
	  this.server = server;
	  this.fileName = fileName;
  }
  
	public void run(){
	  try{
      Socket socket = null;

      socket = new Socket(server, 4444);

	FileOutputStream fos = new FileOutputStream(fileName);
     
      byte[] buffer = new byte[1024];
      int count;
      InputStream in = socket.getInputStream();
      while((count=in.read(buffer)) >=0){
    	  fos.write(buffer, 0, count);
      }
      fos.close();
      socket.close();
      
      in.close();
	  }catch(Exception e){}
	  }
  }
