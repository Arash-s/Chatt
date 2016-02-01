import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class clientThread extends Thread{

	private Socket client;
	private chatserver server;
	private String name;

	PrintWriter out;
	
	public clientThread(Socket client, chatserver server){
		this.client = client;
		this.server = server;
	}
	
	
	@Override
	public void run() {
		try{

			OutputStream os= client.getOutputStream();
			out= new PrintWriter (os, true);
	
			InputStream is= client.getInputStream();
			BufferedReader in= new BufferedReader(new InputStreamReader(is));
			
			do{
			sendmsg("Enter your name:");
			name= in.readLine();}
			while(!server.addUser(name, this));
			
			String[] msg;
			String s;
			
			while(true){
				s= in.readLine();				
				if(s.contains("/w")){
					msg = s.split(" ",3);
					System.out.println(msg[0]+msg[1]+msg[2]);
					server.whisper(name, msg[1], msg[2]);
				}
				else if(s.contains("/Exit"))
					server.removeUser(name);
				else if(s.contains("/File")){
					msg = s.split(" ");
					server.fileTransfer(name,msg[1], client.getInetAddress().getHostAddress(), msg[2]);
				}
				else 
					server.broadcast(name, s);
			}
		}catch(Exception e){}

	}
	
	
	public boolean sendmsg(String s){
		try{
			out.println(s);
		}
		catch(Exception e){
			System.out.println("nothing happened");
			return false;
		}
		return true;
		}
}
