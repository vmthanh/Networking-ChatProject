package com.socket;

import java.io.*;
import java.net.*;







class ServerThread extends Thread{
	public SocketServer server = null;
	public Socket socket = null;
	public int id = -1;
	public String userName = "";
	public ObjectInputStream streamIn = null;
	public ObjectOutputStream streamOut = null;
	
	public ServerThread(SocketServer _server, Socket _socket){
		super();
		server = _server;
		socket = _socket;
		id = socket.getPort();		
	}
	
	public int getID(){
		return id;
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		while (true){
			System.out.println("Server Thread " + id + " running.");
			try{				
				Message msg = (Message) streamIn.readObject();
				server.handle(id,msg);
			}
			catch(Exception e){
				System.out.println(id + " ERROR reading: " + e.getMessage());
				server.remove(id);
				stop();
			}
		}
	}
	
	public void send(Message msg){
		try{
			streamOut.writeObject(msg);
			streamOut.flush();
		}
		catch(IOException ioe){
			System.out.println("[Exception [SocketClient: send(...)]");
		}
	}
	
	public void open() throws IOException{
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}
	
	public void close() throws IOException {  
    	if (socket != null)    socket.close();
        if (streamIn != null)  streamIn.close();
        if (streamOut != null) streamOut.close();
    }
}






public class SocketServer implements Runnable {
	public ServerSocket serverSocket = null;
	public int port = 0;
	public ServerThread	clients[];
	public int clientCount;	
	public Thread thread = null;
	public Database db;
	
	// open a server Socket  with a specific port
	public SocketServer(int _port, String _filepath){
		clients = new ServerThread[100];
		port = _port;				
		db = new Database(_filepath);
		try{
			serverSocket = new ServerSocket(port);
			port = serverSocket.getLocalPort();
			System.out.println("Server startet. IP : " + InetAddress.getLocalHost() 
								+ ", Port : " + serverSocket.getLocalPort()
							   );
			start();
		}
		catch(IOException ioe){		
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}
		
	// start listening-socket of server
	public void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	// stop listening-socket of server 
	@SuppressWarnings("deprecation")
	public void stop(){
		if (thread != null){
			thread.stop();
			thread = null;
		}
	}
		
	// run , waiting for a client 
	public void run(){
		while (thread != null){
			try{
				System.out.println("Waiting for a client...");
				addThread(serverSocket.accept());
			}
			catch(IOException ioe){
				System.out.println("Server accept error: " +ioe);				
			}
		}
	}
	
	// find a client that has id as ID
	public int findClient(int id){
		for (int i=0; i<clientCount; i++){
			if (clients[i].getID() == id){
				return i;
			}
		}
		return -1;
	}
	
	//find the thread of the user has name username
	public ServerThread findUserThread(String username){
		for(int i = 0; i<clientCount; i++){
			if (clients[i].userName.equals(username)){
				return clients[i];
			}
		}
		return null;
	}
	
	//send the list of users to a new user
	public void sendUserList(String toUser){
		for (int i=0; i<clientCount; i++){
			findUserThread(toUser).send(new Message("newuser", "SERVER", clients[i].userName, toUser));
		}
	}
	
	
	// Announce
    public void Announce(String type, String sender, String content){
        Message msg = new Message(type, sender, content, "All");
        for(int i = 0; i < clientCount; i++){
            clients[i].send(msg);
        }
    }
	
	// handle message
	public synchronized void handle(int ID, Message msg) {
		if (msg.content.equals(".bye")) {
			Announce("signout", "SERVER", msg.sender);
			remove(ID);
		} else {
			if (msg.type.equals("login")) {
				System.out.println(msg.toString());
				if (findUserThread(msg.sender) == null) {
					if (db.checkLogin(msg.sender, msg.content)) {
						clients[findClient(ID)].userName = msg.sender;
						clients[findClient(ID)].send(new Message("login",
								"SERVER", "TRUE", msg.sender));
						Announce("newuser", "SERVER", msg.sender);
						sendUserList(msg.sender);
					} else {
						clients[findClient(ID)].send(new Message("login",
								"SERVER", "FALSE", msg.sender));
					}
				} else {
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"FALSE", msg.sender));
				}
			} else if (msg.type.equals("message")) {
				if (msg.recipient.equals("All")) {
					Announce("message", msg.sender, msg.content);
				} else {
					findUserThread(msg.recipient).send(
							new Message(msg.type, msg.sender, msg.content,
									msg.recipient));
					clients[findClient(ID)].send(new Message(msg.type,
							msg.sender, msg.content, msg.recipient));
				}
			} else if (msg.type.equals("test")) {
				clients[findClient(ID)].send(new Message("test", "SERVER",
						"OK", msg.sender));
			} else if (msg.type.equals("signup")) {
				if (findUserThread(msg.sender) == null) {
					if (!db.userExists(msg.sender)) {
						db.addUser(msg.sender, msg.content);
						clients[findClient(ID)].userName = msg.sender;
						clients[findClient(ID)].send(new Message("signup",
								"SERVER", "TRUE", msg.sender));
						clients[findClient(ID)].send(new Message("login",
								"SERVER", "TRUE", msg.sender));
						Announce("newuser", "SERVER", msg.sender);
						sendUserList(msg.sender);
					} else {
						clients[findClient(ID)].send(new Message("signup",
								"SERVER", "FALSE", msg.sender));
					}
				} else {
					clients[findClient(ID)].send(new Message("signup",
							"SERVER", "FALSE", msg.sender));
				}
			} else if (msg.type.equals("upload_req")) {
				if (msg.recipient.equals("All")) {
					clients[findClient(ID)].send(new Message("message",
							"SERVER", "Uploading to 'All' forbidden",
							msg.sender));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_req", msg.sender, msg.content,
									msg.recipient));
				}
			} else if (msg.type.equals("upload_res")) {
				if (!msg.content.equals("NO")) {
					String IP = findUserThread(msg.sender).socket
							.getInetAddress().getHostAddress();
					findUserThread(msg.recipient).send(
							new Message("upload_res", IP, msg.content,
									msg.recipient));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_res", msg.sender, msg.content,
									msg.recipient));
				}
			}
		}
	}
	

	// remove a client, close thread 
	@SuppressWarnings("deprecation")
	public synchronized void remove(int id){
		int pos = findClient(id);
		if (pos >= 0){  
            ServerThread toTerminate = clients[pos];          
            if (pos < clientCount-1){
                for (int i = pos+1; i < clientCount; i++){
                    clients[i-1] = clients[i];
	        }
	    }
	    clientCount--;
	    try{  
	      	toTerminate.close(); 
	    }
	    catch(IOException ioe){  
	    	System.out.println("\nError closing thread: " + ioe);	      	
	    }
	    toTerminate.stop(); 
	}
	}
	
	
	// add a connection socket to connect with a client 
	private void addThread(Socket socket){
		if (clientCount < clients.length){		
			System.out.println("Client accepted: "+socket);
			clients[clientCount] = new ServerThread(this,socket);
			try{
				System.out.println("create connection socket successfully");
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			}
			catch(IOException ioe){
				System.out.println("Error Opening Thread: " + ioe );
			}
		}
		else{
			System.out.println("Client refused: maximum "+clients.length +" reached");
		}
	}
}
