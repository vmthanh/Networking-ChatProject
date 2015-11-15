package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Server {

	public static void main(String[] args) {			
						
		BufferedReader br = new BufferedReader(new
                InputStreamReader(System.in));
		
		//read port number
		System.out.print("Input Port number: ");	
		int port = 13000;
		try {
			port = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//read filepath of Data file
		System.out.print("Enter Database filepath (e.g. C:/Data.xml): ");
		String filepath = "";				
		try {
			filepath = (String) br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		SocketServer socketServer = new SocketServer(port, filepath); 
		//socketServer.run();
	}		
}


