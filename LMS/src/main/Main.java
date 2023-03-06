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

import javax.swing.ImageIcon;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import java.awt.CardLayout;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;


public class Main extends JFrame {

	private JPanel mainPanel;
	private JPasswordField passwordField_1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

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
		setEnabled(false);
		setTitle("Library Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 685, 473);

		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0, 0, 0));
		mainPanel.setForeground(SystemColor.text);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
JPanel Login = new JPanel();
		Login.setBorder(new LineBorder(new Color(255, 163, 79), 0, true));
		Login.setBackground(new Color(0, 0, 0));
		Login.setForeground(new Color(128, 255, 0));
		mainPanel.add(Login, "name_66116889923900");
		Login.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(223, 156, 32), 3));
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(146, 209, 351, 158);
		Login.add(panel);
		panel.setLayout(null);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(127, 50, 146, 22);
		textArea_1.setLineWrap(true);
		panel.add(textArea_1);
		
		JLabel lblNewLabel_3 = new JLabel("LOGIN");
		lblNewLabel_3.setBounds(127, 0, 83, 55);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel.add(lblNewLabel_3);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(127, 83, 146, 20);
		panel.add(passwordField_1);
		
		JLabel lblNewLabel_4 = new JLabel("UserID");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(45, 55, 72, 17);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Password");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(45, 84, 83, 14);
		panel.add(lblNewLabel_5);
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBackground(new Color(223, 156, 32));
		btnNewButton_1.setBounds(121, 124, 89, 23);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\LANCER\\ECLIPSE\\Work Space\\LMS\\Logo\\LOGO.png"));
		lblNewLabel.setBounds(173, 56, 283, 129);
		Login.add(lblNewLabel);
		
		JPanel HomeScreen = new JPanel();
		HomeScreen.setBackground(new Color(128, 128, 128));
		mainPanel.add(HomeScreen, "name_66130146280600");
		HomeScreen.setLayout(null);
		
		JPanel RightContentPanel = new JPanel();
		RightContentPanel.setBackground(new Color(128, 128, 128));
		RightContentPanel.setBounds(0, 0, 195, 424);
		HomeScreen.add(RightContentPanel);
		RightContentPanel.setLayout(null);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Search / Rent");
		tglbtnNewToggleButton.setBounds(10, 11, 176, 53);
		tglbtnNewToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("Return");
		tglbtnNewToggleButton_1.setBounds(10, 70, 176, 47);
		tglbtnNewToggleButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton_1);
		
		JToggleButton tglbtnNewToggleButton_2 = new JToggleButton("User Information");
		tglbtnNewToggleButton_2.setBounds(10, 128, 176, 47);
		tglbtnNewToggleButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton_2);
		
		JToggleButton tglbtnNewToggleButton_3 = new JToggleButton("User Management");
		tglbtnNewToggleButton_3.setBounds(10, 186, 176, 47);
		tglbtnNewToggleButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton_3);
		
		JToggleButton tglbtnNewToggleButton_4 = new JToggleButton("Book Management");
		tglbtnNewToggleButton_4.setBounds(10, 244, 176, 53);
		tglbtnNewToggleButton_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton_4);
		
		JToggleButton tglbtnNewToggleButton_5 = new JToggleButton("Register");
		tglbtnNewToggleButton_5.setBounds(10, 308, 176, 47);
		tglbtnNewToggleButton_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton_5);
		
		JToggleButton tglbtnNewToggleButton_6 = new JToggleButton("Activity Logs");
		tglbtnNewToggleButton_6.setBounds(10, 366, 176, 47);
		tglbtnNewToggleButton_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		RightContentPanel.add(tglbtnNewToggleButton_6);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(199, 0, 460, 424);
		HomeScreen.add(panel_2);
		panel_2.setLayout(new CardLayout(0, 0));
		
		JPanel SearchRentPanel = new JPanel();
		SearchRentPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(SearchRentPanel, "name_72204072661900");
		
		JPanel ReturnPanel = new JPanel();
		ReturnPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(ReturnPanel, "name_72224483657400");
		
		JPanel UserInformationPanel = new JPanel();
		UserInformationPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(UserInformationPanel, "name_72227231251400");
		
		JPanel UserManagementPanel = new JPanel();
		UserManagementPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(UserManagementPanel, "name_72229635802400");
		
		JPanel BookManagementPanel = new JPanel();
		BookManagementPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(BookManagementPanel, "name_72232200085800");
		
		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(RegisterPanel, "name_72244918873200");
		RegisterPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("User Registration");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(134, 0, 176, 43);
		RegisterPanel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(134, 54, 278, 20);
		RegisterPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(134, 100, 278, 20);
		RegisterPanel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(134, 147, 278, 20);
		RegisterPanel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(134, 192, 86, 20);
		RegisterPanel.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(230, 192, 86, 20);
		RegisterPanel.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(326, 192, 86, 20);
		RegisterPanel.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(134, 264, 278, 20);
		RegisterPanel.add(textField_6);
		textField_6.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Male");
		rdbtnNewRadioButton.setBounds(134, 329, 59, 23);
		RegisterPanel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Female");
		rdbtnNewRadioButton_1.setBounds(195, 329, 59, 23);
		RegisterPanel.add(rdbtnNewRadioButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(39, 60, 96, 14);
		RegisterPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("Address");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(39, 106, 96, 14);
		RegisterPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Email");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(39, 153, 96, 14);
		RegisterPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Birthday");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setBounds(39, 198, 96, 14);
		RegisterPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Date");
		lblNewLabel_9.setBounds(134, 215, 46, 14);
		RegisterPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Month");
		lblNewLabel_10.setBounds(230, 215, 46, 14);
		RegisterPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Year");
		lblNewLabel_11.setBounds(326, 215, 46, 14);
		RegisterPanel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("Contact No.");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_12.setBounds(39, 270, 96, 14);
		RegisterPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Gender");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_13.setBounds(134, 308, 96, 14);
		RegisterPanel.add(lblNewLabel_13);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Admin");
		rdbtnNewRadioButton_2.setBounds(292, 329, 59, 23);
		RegisterPanel.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Client");
		rdbtnNewRadioButton_3.setBounds(353, 329, 59, 23);
		RegisterPanel.add(rdbtnNewRadioButton_3);
		
		JLabel lblNewLabel_14 = new JLabel("Role");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_14.setBounds(292, 307, 46, 14);
		RegisterPanel.add(lblNewLabel_14);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(194, 377, 103, 36);
		RegisterPanel.add(btnNewButton);
		
		JPanel ActivityLogsPanel = new JPanel();
		ActivityLogsPanel.setBackground(new Color(192, 192, 192));
		panel_2.add(ActivityLogsPanel, "name_72247065996200");
	}
}
