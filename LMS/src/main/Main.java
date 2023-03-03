package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Main extends JFrame {

	private JPanel mainPanel;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Library Management System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 461);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0, 128, 0));
		mainPanel.setForeground(SystemColor.text);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(142, 138, 405, 257);
		panel.setBackground(new Color(128, 255, 128));
		panel.setForeground(new Color(128, 255, 0));
		mainPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lbl_Login = new JLabel("Login");
		lbl_Login.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_Login.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Login.setForeground(new Color(0, 128, 0));
		lbl_Login.setBackground(new Color(255, 255, 255));
		lbl_Login.setBounds(76, 23, 241, 42);
		panel.add(lbl_Login);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(127, 76, 190, 22);
		panel.add(textArea);
		
		JLabel lblNewLabel = new JLabel("UserID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(36, 71, 67, 30);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(22, 129, 81, 30);
		panel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(127, 129, 190, 30);
		panel.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(162, 194, 89, 23);
		panel.add(btnNewButton);
	}
}