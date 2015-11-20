package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import com.socket.Message;
import com.socket.SocketClient;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientListFrame extends Frame {

	public JPanel contentPane;
	public DefaultListModel model;
	public JList listChatLst;
	public JTextField textField;
	public JTextArea chatArea;
	private JMenuBar menuBar;
	private JMenu mnAccount;
	private JMenuItem mntmExit;

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

	public void sendMessage(Message msg) {
		client.send(msg);
	}

	private void initComponent() {

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				client.send(new Message("message", username, ".bye", "SERVER"));
				clientThread.stop();
				LoginFrame loginFrame;
				try {
					loginFrame = new LoginFrame();
					loginFrame.show();
					dispose();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 342);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);

		mntmExit = new JMenuItem("Logout ");
		mntmExit.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				LogOutAccount(e);

			}
		});
		mnAccount.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		listChatLst = new JList();
		listChatLst.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				// chatArea.setText("");
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

	private void LogOutAccount(MouseEvent arg0) {
		client.send(new Message("message", username, ".bye", "SERVER"));
		clientThread.stop();
		LoginFrame loginFrame;
		try {
			loginFrame = new LoginFrame();
			loginFrame.show();
			this.dispose();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
