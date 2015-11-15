package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;

import java.awt.ScrollPane;

import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import com.socket.Message;
import com.socket.SocketClient;

public class ClientListFrame extends Frame {

	public JPanel contentPane;
	public DefaultListModel model;
	public JList listChatLst;
	public JTextField textField;
	public JTextArea chatArea;

	public ClientListFrame() {
		initComponent();
		this.setTitle("Client List Page");
	}

	public ClientListFrame(SocketClient socketClient, Thread clientThread) {
		initComponent();
		this.setTitle("Client List Page");
		this.client = socketClient;
		this.clientThread = clientThread;
		client.changeFrame(this);
		model.addElement("All");
	}

	private void initComponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		listChatLst = new JList();
		listChatLst.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				chatArea.setText("");
			}
		});
		model = new DefaultListModel();
		listChatLst.setModel(model);

		chatArea = new JTextArea();

		JButton btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage(arg0);
			}
		});
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 11));

		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
										.addComponent(listChatLst, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(listChatLst, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(chatArea, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSend, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)).addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}

	private void sendMessage(ActionEvent evt) {
		String msg = textField.getText();
		String recipient = listChatLst.getSelectedValue().toString();

		if (!msg.isEmpty() && !recipient.isEmpty()) {
			textField.setText("");
			this.client.send(new Message("message", username, msg, recipient));
		}
	}
}
