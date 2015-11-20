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
		// this.hist = new History(historyFile);

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
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHostAddress)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(usernameField, Alignment.LEADING)
						.addComponent(hostField, Alignment.LEADING))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHostPost)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(passwordField))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnConnect)
					.addContainerGap(142, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(hostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblHostPost)
							.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnConnect))
						.addComponent(lblHostAddress))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(28)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnLogin)
								.addComponent(btnSignUp)))
						.addComponent(lblNewLabel))
					.addContainerGap(161, Short.MAX_VALUE))
		);
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
			clientListFrame.show();
			String hashPassword = hashMD5(password);
			client.send(new Message("login", username, hashPassword, "SERVER"));
			this.dispose();

		}
	}

	private void SignupAction(ActionEvent evt) throws UnsupportedEncodingException {
		username = usernameField.getText();
		password = passwordField.getText();
		if (!username.isEmpty() && !password.isEmpty()) {

			String hashPassword = hashMD5(password);
			client.send(new Message("signup", username, hashPassword, "SERVER"));
			usernameField.setText("");
			passwordField.setText("");

		}
	}

	public String hashMD5(String input) {
		MessageDigest m;
		String hashtext = "";
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(input.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashtext;

	}

}
