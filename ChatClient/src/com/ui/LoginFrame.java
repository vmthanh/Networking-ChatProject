package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.socket.History;
import com.socket.Message;
import com.socket.SocketClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFrame extends Frame {

	public JPanel contentPane;
	public JTextField usernameField;
	public JPasswordField passwordField;
	public JTextField hostField;
	public JTextField portField;
	public JButton btnLogin;
	public JButton btnSignUp;
	public JButton btnConnect;
	private MessageDigest md;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() throws NoSuchAlgorithmException {
		// model.addElement("All");
		iniComponent();
		this.setTitle("Welcome page");
		this.hostField.setText("localhost");
		this.portField.setText("13000");
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.send(new Message("message", username, ".bye", "SERVER"));
					clientThread.stop();
				} catch (Exception ex) {
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
		//this.hist = new History(historyFile);
		md = MessageDigest.getInstance("MD5");
	}

	private void iniComponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Username");

		usernameField = new JTextField();
		usernameField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Password");

		passwordField = new JPasswordField();

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginAction(arg0);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SignupAction(arg0);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JLabel lblHostAddress = new JLabel("Host Address");

		hostField = new JTextField();
		hostField.setColumns(10);

		JLabel lblHostPost = new JLabel("Host Port");

		portField = new JTextField();
		portField.setColumns(10);

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectAction(arg0);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblHostAddress)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(
										gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														gl_contentPane.createSequentialGroup()
																.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_contentPane
																.createSequentialGroup()
																.addComponent(hostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(
																		gl_contentPane
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblNewLabel)
																				.addGroup(
																						gl_contentPane
																								.createSequentialGroup()
																								.addComponent(lblHostPost)
																								.addPreferredGap(ComponentPlacement.UNRELATED)
																								.addComponent(portField, GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(ComponentPlacement.UNRELATED)
																								.addComponent(btnConnect)))))
								.addContainerGap(26, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(176).addComponent(lblNewLabel_1).addContainerGap(202, Short.MAX_VALUE))
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addGap(147)
								.addGroup(
										gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(passwordField, Alignment.TRAILING)
												.addComponent(usernameField, Alignment.TRAILING, 114, 114, Short.MAX_VALUE))
								.addContainerGap(163, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_contentPane
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(hostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblHostPost)
														.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE).addComponent(btnConnect)).addComponent(lblHostAddress))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblNewLabel).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_1).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnLogin).addComponent(btnSignUp))
						.addContainerGap(74, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	private void ConnectAction(ActionEvent evt) {

		serverAddr = hostField.getText();
		port = Integer.parseInt(portField.getText());
		if (!serverAddr.isEmpty() && !portField.getText().isEmpty()) {
			try {
				client = new SocketClient(this);
				clientThread = new Thread(client);
				clientThread.start();
				client.send(new Message("test", "testUser", "testContent", "SERVER"));
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Server not found", "Error: Server", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	private void LoginAction(ActionEvent evt) throws UnsupportedEncodingException {
		this.username = usernameField.getText();
		this.password = passwordField.getText();
		if (!username.isEmpty() && !password.isEmpty()) {
			
			ClientListFrame clientListFrame = new ClientListFrame(client, clientThread);
			clientListFrame.username = username;
			clientListFrame.password = password;
			clientListFrame.port = port;
			clientListFrame.serverAddr = serverAddr;
			//clientListFrame.hist = hist;
			clientListFrame.show();
			
			String hashUsername = hashMD5(username);
			String hashPassword = hashMD5(password);
			client.send(new Message("login",hashUsername,hashPassword, "SERVER"));
			//client.send(new Message("login",username,password, "SERVER"));
			this.dispose();
			
		}
	}

	private void SignupAction(ActionEvent evt) throws UnsupportedEncodingException {
		username = usernameField.getText();
		password = passwordField.getText();
		if (!username.isEmpty() && !password.isEmpty()) {
			String hashUsername = hashMD5(username);
			String hashPassword = hashMD5(password);
			client.send(new Message("signup", hashUsername, hashPassword, "SERVER"));
			usernameField.setText("");
			passwordField.setText("");
		}
	}
	
	public String hashMD5(String input){
		MessageDigest m;
		String hashtext = "";
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(input.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashtext;
		
	}

}
