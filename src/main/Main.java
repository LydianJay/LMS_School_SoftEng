package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main extends JFrame implements ActionListener {
	
	private static final int PNL_SEARCH = 0, PNL_RETURN = 1, PNL_USER_INFO = 2, PNL_USER_MNG = 3, PNL_BOOK_MNG = 4, PNL_REGIS = 5, PNL_LOG = 6;
	
	private static String dtbUrl = "jdbc:mysql://localhost:3306/lms_database", dtbUsername = "root", dtbPassword = "";
	private static Connection dtbConn;
	private static CardLayout crdMain = new CardLayout();
	
	private static int currentHomeCntIdx = 0; // current panel number for the homepage
	private JButton btnLogin;
	private JPasswordField pfieldPassword;
	private JTextField tfieldUserID;
	private JPanel mainPanel;
	private JButton btnRegisterSelection;
	private JPanel rightContentPanel;
	private JPanel HomeScreen;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnAdmin;
	private JRadioButton rdbtnClient;
	private JButton btnRegistration;
	private JPanel RegisterPanel;
	private JTextField tFieldNameRegis;
	private JTextField tFieldAddrRegis;
	private JTextField tFieldEmailRegis;
	private JTextField tFieldDateRegis;
	private JTextField tFieldMonthRegis;
	private JTextField tFieldYearRegis;
	private JTextField tFieldConRegis;
	private JTextField tfieldPassRegis;
	
	
	
	
	
	private void movePanel(int idx) {
		
		
		CardLayout c = (CardLayout)(this.rightContentPanel.getLayout());
		c.first(rightContentPanel);
		for(int i = 0; i < idx; i++) c.next(rightContentPanel);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub0
		JButton b = (JButton)(e.getSource());
		if(b == btnLogin) {
			
			
			int userId = Integer.valueOf(tfieldUserID.getText());
			String userPass = new String(pfieldPassword.getPassword());
		
			try {
				Statement st = dtbConn.createStatement();
				String str = "SELECT * FROM `userinfo` WHERE userID = " + String.valueOf(userId);
				ResultSet r = st.executeQuery(str);
				if(r.next()) {
					String userName = r.getString("userName");
					String userCorrectPass = r.getString("userPass");
					
					if(userPass.compareTo(userCorrectPass) == 0) {
						CardLayout c = (CardLayout)(mainPanel.getLayout());
						c.next(this.getContentPane());
					}
					else {
						JOptionPane.showMessageDialog(this, "UserID or password Incorrect!");
						pfieldPassword.setText("");
					}
					
					
					
				}
				else {
					
					JOptionPane.showMessageDialog(this, "UserID or password Incorrect!");
					pfieldPassword.setText("");
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
		else if(b == this.btnRegisterSelection) {
			
			CardLayout c = (CardLayout)(this.rightContentPanel.getLayout());
			
			movePanel(PNL_REGIS);
			currentHomeCntIdx = 5;
			
			
		}
		
		
		else if(b == this.btnRegistration) {
			
			String userName, address, email,bday,conNum, password, gender, role;
			
			
			
			
			userName = "'" + this.tFieldNameRegis.getText() + "'";
			address = "'" + this.tFieldAddrRegis.getText() + "'";
			email = "'" + this.tFieldEmailRegis.getText() + "'";
			bday = this.tFieldDateRegis.getText() + this.tFieldMonthRegis.getText() + this.tFieldYearRegis.getText();
			conNum = "'" + this.tFieldConRegis.getText() + "'";
			password = "'" + this.tfieldPassRegis.getText() + "'";
			gender = this.rdbtnMale.isSelected() ? "1" : "0";
			gender = this.rdbtnFemale.isSelected() ? "0" : "1";
			role = this.rdbtnAdmin.isSelected() ? "1" : "0";
			role = this.rdbtnClient.isSelected() ? "0" : "1";
			
			boolean isValid = userName.isBlank() && address.isBlank() && email.isBlank() && bday.isBlank() && conNum.isBlank() && password.isBlank() && gender.isBlank() && role.isBlank();
			if(isValid) {
				
				
				
				String userID = bday;
				boolean isSuccess = true;
				try {
					Statement st = dtbConn.createStatement();
					String str = "INSERT INTO userinfo VALUES (" + userName + "," + address + "," + email + "," + bday + "," + conNum + "," + gender + "," + role + "," + userID + "," + password + ");";
					st.executeUpdate(str);
					
					
				} catch (SQLException e1) {
					isSuccess = false;
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "Failed");
					
				}
				if(isSuccess) {
					JOptionPane.showMessageDialog(this, "Registration Success!");
					movePanel(PNL_SEARCH);
				}
				
			}
			else {
				JOptionPane.showMessageDialog(this, "Empty Fields Detected!!!");
			}
			
			
			
			
		}
		
		
		
		
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		
		try {
			
			dtbConn = DriverManager.getConnection(dtbUrl, dtbUsername, dtbPassword);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
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
		setResizable(false);
		setEnabled(true);
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
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(127, 0, 83, 55);
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel.add(lblLogin);
		
		pfieldPassword = new JPasswordField();
		pfieldPassword.setBounds(127, 83, 146, 20);
		panel.add(pfieldPassword);
		
		JLabel lblUserID = new JLabel("UserID");
		lblUserID.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUserID.setBounds(45, 55, 72, 17);
		panel.add(lblUserID);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(45, 84, 83, 14);
		panel.add(lblPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(new Color(223, 156, 32));
		btnLogin.setBounds(121, 124, 89, 23);
		btnLogin.addActionListener(this);
		
		panel.add(btnLogin);
		
		tfieldUserID = new JTextField();
		tfieldUserID.setBounds(127, 55, 146, 20);
		panel.add(tfieldUserID);
		tfieldUserID.setColumns(10);
		
		HomeScreen = new JPanel();
		HomeScreen.setBackground(new Color(128, 128, 128));
		mainPanel.add(HomeScreen, "name_66130146280600");
		HomeScreen.setLayout(null);
		
		JPanel leftContentPanel = new JPanel();
		leftContentPanel.setBackground(new Color(128, 128, 128));
		leftContentPanel.setBounds(0, 0, 195, 424);
		HomeScreen.add(leftContentPanel);
		leftContentPanel.setLayout(null);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Search / Rent");
		tglbtnNewToggleButton.setBounds(10, 11, 176, 53);
		tglbtnNewToggleButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("Return");
		tglbtnNewToggleButton_1.setBounds(10, 70, 176, 47);
		tglbtnNewToggleButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(tglbtnNewToggleButton_1);
		
		JToggleButton tglbtnNewToggleButton_2 = new JToggleButton("User Information");
		tglbtnNewToggleButton_2.setBounds(10, 128, 176, 47);
		tglbtnNewToggleButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(tglbtnNewToggleButton_2);
		
		JToggleButton tglbtnNewToggleButton_3 = new JToggleButton("User Management");
		tglbtnNewToggleButton_3.setBounds(10, 186, 176, 47);
		tglbtnNewToggleButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(tglbtnNewToggleButton_3);
		
		JToggleButton tglbtnNewToggleButton_4 = new JToggleButton("Book Management");
		tglbtnNewToggleButton_4.setBounds(10, 244, 176, 53);
		tglbtnNewToggleButton_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(tglbtnNewToggleButton_4);
		
		JToggleButton tglbtnNewToggleButton_6 = new JToggleButton("Activity Logs");
		tglbtnNewToggleButton_6.setBounds(10, 366, 176, 47);
		tglbtnNewToggleButton_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(tglbtnNewToggleButton_6);
		
		btnRegisterSelection = new JButton("Register");
		btnRegisterSelection.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegisterSelection.setBounds(10, 308, 176, 47);
		btnRegisterSelection.addActionListener(this);
		leftContentPanel.add(btnRegisterSelection);
		
		rightContentPanel = new JPanel();
		rightContentPanel.setBounds(199, 0, 460, 424);
		HomeScreen.add(rightContentPanel);
		rightContentPanel.setLayout(new CardLayout(0, 0));
		
		JPanel SearchRentPanel = new JPanel();
		SearchRentPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(SearchRentPanel, "name_72204072661900");
		
		JPanel ReturnPanel = new JPanel();
		ReturnPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(ReturnPanel, "name_72224483657400");
		
		JPanel UserInformationPanel = new JPanel();
		UserInformationPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(UserInformationPanel, "name_72227231251400");
		
		JPanel UserManagementPanel = new JPanel();
		UserManagementPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(UserManagementPanel, "name_72229635802400");
		
		JPanel BookManagementPanel = new JPanel();
		BookManagementPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(BookManagementPanel, "name_72232200085800");
		
		RegisterPanel = new JPanel();
		RegisterPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(RegisterPanel, "name_72244918873200");
		RegisterPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("User Registration");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(134, 0, 176, 43);
		RegisterPanel.add(lblNewLabel_1);
		
		tFieldNameRegis = new JTextField();
		tFieldNameRegis.setBounds(134, 54, 278, 20);
		RegisterPanel.add(tFieldNameRegis);
		tFieldNameRegis.setColumns(10);
		
		tFieldAddrRegis = new JTextField();
		tFieldAddrRegis.setBounds(134, 100, 278, 20);
		RegisterPanel.add(tFieldAddrRegis);
		tFieldAddrRegis.setColumns(10);
		
		tFieldEmailRegis = new JTextField();
		tFieldEmailRegis.setBounds(134, 147, 278, 20);
		RegisterPanel.add(tFieldEmailRegis);
		tFieldEmailRegis.setColumns(10);
		
		tFieldDateRegis = new JTextField();
		tFieldDateRegis.setBounds(134, 192, 86, 20);
		RegisterPanel.add(tFieldDateRegis);
		tFieldDateRegis.setColumns(10);
		
		tFieldMonthRegis = new JTextField();
		tFieldMonthRegis.setBounds(230, 192, 86, 20);
		RegisterPanel.add(tFieldMonthRegis);
		tFieldMonthRegis.setColumns(10);
		
		tFieldYearRegis = new JTextField();
		tFieldYearRegis.setBounds(326, 192, 86, 20);
		RegisterPanel.add(tFieldYearRegis);
		tFieldYearRegis.setColumns(10);
		
		tFieldConRegis = new JTextField();
		tFieldConRegis.setBounds(134, 240, 278, 20);
		RegisterPanel.add(tFieldConRegis);
		tFieldConRegis.setColumns(10);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(134, 329, 59, 23);
		RegisterPanel.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(195, 329, 59, 23);
		RegisterPanel.add(rdbtnFemale);
		
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
		lblNewLabel_12.setBounds(39, 240, 96, 14);
		RegisterPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Gender");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_13.setBounds(134, 308, 96, 14);
		RegisterPanel.add(lblNewLabel_13);
		
		rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setBounds(292, 329, 59, 23);
		RegisterPanel.add(rdbtnAdmin);
		
		rdbtnClient = new JRadioButton("Client");
		rdbtnClient.setBounds(353, 329, 59, 23);
		RegisterPanel.add(rdbtnClient);
		
		JLabel lblNewLabel_14 = new JLabel("Role");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_14.setBounds(292, 307, 46, 14);
		RegisterPanel.add(lblNewLabel_14);
		
		btnRegistration = new JButton("Register");
		btnRegistration.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistration.setBounds(194, 377, 103, 36);
		btnRegistration.addActionListener(this);
		RegisterPanel.add(btnRegistration);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(39, 271, 103, 20);
		RegisterPanel.add(lblNewLabel);
		
		tfieldPassRegis = new JTextField();
		tfieldPassRegis.setBounds(134, 272, 278, 20);
		RegisterPanel.add(tfieldPassRegis);
		tfieldPassRegis.setColumns(10);
		
		JPanel ActivityLogsPanel = new JPanel();
		ActivityLogsPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(ActivityLogsPanel, "name_72247065996200");
	}
}
