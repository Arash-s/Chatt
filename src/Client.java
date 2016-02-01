import java.net.*;
import java.io.*;

class Client extends Thread{

	PrintWriter out;
	GUI ui;
	Socket server;
	
	public Client(GUI ui)
	{
		this.ui = ui;
	}
		
	@Override
	public void run() {
			try{
				System.out.println("trying");
				 server= new Socket("localhost", 1201);
				OutputStream os= server.getOutputStream();
				out= new PrintWriter (os, true);

				InputStream is= server.getInputStream();
				BufferedReader in= new BufferedReader(new InputStreamReader(is));

				while(true){
					String s= in.readLine();
					if(s.contains("/UserList")){
						String[] list = s.split(" ");
						ui.userList(list);				
					}
					else if(s.contains("/File")){
						System.out.println("recieved file req");
						String[] list = s.split(" ");
						ui.fileTransfer(list[1],list[2]);				
					}
					else
						ui.addmsg(s);
				}

			}
			catch(Exception e) {}

	}

	public void sendmsg(String s){
		try{
			out.println(s);
		}
		catch(Exception e){}

		}

	public void exit(){
		try {
			out.println("/Exit");
			server.close();
            System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void exec(){
		this.start();
	}


}