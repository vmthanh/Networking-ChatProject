package com.socket;

import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import javax.swing.JOptionPane;

import com.ui.ClientListFrame;
import com.ui.Frame;
import com.ui.LoginFrame;

public class SocketClient implements Runnable {
	public int port;
	public String serverAddress;
	public Socket clientSocket;
	public Frame ui;
	public ObjectInputStream In;
	public ObjectOutputStream Out;
	public History hist;

	public SocketClient(Frame frame) throws IOException {
		this.ui = frame;
		this.serverAddress = this.ui.serverAddr;
		this.port = this.ui.port;
		clientSocket = new Socket(InetAddress.getByName(serverAddress), port);
		Out = new ObjectOutputStream(clientSocket.getOutputStream());
		Out.flush();
		In = new ObjectInputStream(clientSocket.getInputStream());
		// hist = ui.hist;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean keepRunning = true;
		while (keepRunning) {
			try {

				// Message msg = new Message("test", "test", "test", "test");
				Message msg = (Message) In.readObject();
				System.out.println("Incoming : " + msg.toString());
				if (msg.type.equals("message")) {
					ClientListFrame clientListFrame = (ClientListFrame) ui;
					clientListFrame.chatArea.append("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
					String msgTime = (new Date()).toString();
					try {
						// clientListFrame.hist.addMessage(msg, msgTime);
					} catch (Exception e) {
						// TODO: handle exception
					}

				} else if (msg.type.equals("login")) {
					if (msg.content.equals("TRUE")) {

					} else {
						JOptionPane.showMessageDialog(null, "Login failed", "Error", JOptionPane.INFORMATION_MESSAGE);

					}

				} else if (msg.type.equals("test")) {
					LoginFrame loginFrame = (LoginFrame) ui;
					loginFrame.btnConnect.setEnabled(false);
					loginFrame.hostField.setEditable(false);
					loginFrame.portField.setEditable(false);
					System.out.println("Connect successfully");

				} else if (msg.type.equals("newuser")) {
					ClientListFrame clientListFrame = (ClientListFrame) ui;
					if (!msg.content.equals(clientListFrame.username)) {
						boolean exists = false;
						for (int i = 0; i < clientListFrame.model.getSize(); ++i) {
							if (clientListFrame.model.getElementAt(i).equals(msg.content)) {
								exists = true;
								break;
							}
						}
						if (!exists) {
							clientListFrame.model.addElement(msg.content);
						}
					}

				} else if (msg.type.equals("signup")) {
					LoginFrame loginFrame = (LoginFrame) ui;
					if (msg.content.equals("TRUE")) {
						JOptionPane.showMessageDialog(null, "Signup successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Username && password existed", "Signup failed", JOptionPane.INFORMATION_MESSAGE);
					}

				} else if (msg.type.equals("signout")) {
					ClientListFrame clientListFrame = (ClientListFrame) ui;
					if (msg.content.equals(clientListFrame.username)) {
						for (int i = 1; i < clientListFrame.model.size(); i++) {
							clientListFrame.model.removeElementAt(i);
						}

						clientListFrame.clientThread.stop();
					}else{
						 clientListFrame.model.removeElement(msg.content);
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				keepRunning = false;
				// code for UI
				ClientListFrame clientListFrame = (ClientListFrame) ui;
				for (int i = 1; i < clientListFrame.model.size(); i++) {
					clientListFrame.model.removeElementAt(i);
				}
				clientListFrame.clientThread.stop();
				System.out.println("Exception SocketClient run()");
				e.printStackTrace();
			}
		}

	}

	public void send(Message msg) {
		try {
			this.Out.writeObject(msg);
			this.Out.flush();

			System.out.println("Outgoing : " + msg.toString());
			if (msg.type.equals("message") && !msg.content.equals(".bye")) {
				String msgTime = (new Date()).toString();
				// Code for saving history chat
				try {
					// hist.addMessage(msg, msgTime);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception SocketClient send(msg)");

		}
	}

	public void closeThread(Thread t) {
		t = null;
	}

	public void changeFrame(Frame frame) {
		this.ui = frame;
	}

}
