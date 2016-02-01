import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileSend{
	
	//public FileSend(){
	public static void main(String[] args){
    ServerSocket serverSocket = null;
    Socket socket = null;
    OutputStream out = null;

    try {
        serverSocket = new ServerSocket(4444);
        File myFile = new File("send.jpg");
        System.out.println("server up");
        socket = serverSocket.accept();
        System.out.println("connection up");
   

    byte[] buffer = new byte[16*1024];
    
    out = socket.getOutputStream();
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(myFile));
    int count;
	while ((count = in.read(buffer)) >= 0) {
         out.write(buffer, 0, count);
         out.flush();
    }
    socket.close();
    System.out.println("done");
    out.close();
    in.close();
    socket.close();
    serverSocket.close();
    
    } catch (Exception e){}

	}	
}


