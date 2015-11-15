This implementation of Server is built to run on command line. 

Source Code includes: 
	+ Database.java: handlling signing up, checking User's account
	+ Message.java: represent Data Segment
	+ SocketServer.java: implementing activities of Server (2-way handshake, open connection socket, handle Message,...) 
	+ Server.java: containing main method, allow user to select Port Number, Data file, start Server
	
How to run? 
	run server.jar on command line.
	User should provide program with port number on that server run
	User should provide program with a filepath of Data xml file which store User's accounts 
		(use forward-slash (/) instead of backslash (\) in filepath)		
	
	