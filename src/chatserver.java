import java.net.*;
import java.util.HashMap;
import java.io.*;

class chatserver{

	HashMap<String, clientThread> clients;
	public static void main(String[] args){

		chatserver prog= new chatserver();
		Socket client = null;
		ServerSocket ss = null;
		
		prog.clients = new HashMap<String, clientThread>();
		
			try{
				ss = new ServerSocket(1201);
			} catch(Exception e) {}
			
			while(true){
				try{
					client = ss.accept();
					System.out.println("accepted");
				} catch (IOException e) {
	                System.out.println("I/O error: " + e);}
	                
	            new clientThread(client, prog).start();
	            client= null;
			}
			

			
		
	}

	public boolean addUser(String name, clientThread clientThread) {
		if(clients.containsKey(name))
			return false;
		clients.put(name, clientThread);
		clients.get(name).sendmsg("-------------------------------------" + "\n" +
									"Welcome to the chatroom, "+ name  + "!\n" +
									"-------------------------------------");
		updateUserLists();
		return true;
	}
	
	public void removeUser(String name){
		clients.remove(name);
		updateUserLists();
	}
	
	private void updateUserLists() {
		StringBuilder sb = new StringBuilder();
		sb.append("/UserList ");
		for(String client : clients.keySet()){
			sb.append(client + " ");
		}
		broadcast("server", sb.toString());		
	}

	public void broadcast(String sender, String s){
		for(String client : clients.keySet()){
			/*if(sender.equals(client))
				continue;*/
			clients.get(client).sendmsg(sender+" says: "+ s);
		}
	}
	

	public void whisper(String sender, String reciever, String s) {
		if(clients.containsKey(reciever)){
			clients.get(reciever).sendmsg(sender+ " whispers: "+s);
			clients.get(sender).sendmsg("you whispered to "+ reciever + ": " + s);
		}	
		else{
			clients.get(sender).sendmsg(reciever + " is not online.");
		}
		
	}

	public void fileTransfer(String name, String target, String ip, String fileName) {
		clients.get(target).sendmsg("/File "+ name  + " " + ip + " " + fileName);
	}
	


}