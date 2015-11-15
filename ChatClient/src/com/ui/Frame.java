package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.socket.History;
import com.socket.SocketClient;

public class Frame extends JFrame {
	public Frame() {
	}

	public SocketClient client;
	public int port;
	public String serverAddr;
	public Thread clientThread;
	public String username;
	public String password;
	public History hist;
	public String historyFile = "D:/History.xml";

}
