import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class FileRecieve{
	
  //public FileRecieve(String server){
	
	public static void main(String[] args){
	String	server = "localhost";
	  try{
      Socket socket = null;

      socket = new Socket(server, 4444);

      FileOutputStream fos = new FileOutputStream("recieved.jpg");
     
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
