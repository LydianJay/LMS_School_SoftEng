package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

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
import javax.swing.text.MaskFormatter;
import javax.swing.border.EtchedBorder;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JScrollBar;
import user.Client;

public class Main extends JFrame implements ActionListener {
	
	private static final int PNL_SEARCH = 0, PNL_RETURN = 1, PNL_USER_INFO = 2, PNL_USER_MNG = 3, PNL_BOOK_MNG = 4, PNL_REGIS = 5, PNL_LOG = 6;
	
	private static String dtbUrl = "jdbc:mysql://localhost:3306/lms_database", dtbUsername = "root", dtbPassword = "";
	private static Connection dtbConn;
	private static CardLayout crdMain = new CardLayout();
	private static ArrayList<JCheckBox> chkBoxUsermng = new ArrayList<JCheckBox>();
	
	
	private static int currentHomeCntIdx = 0; // current panel number for the homepage
	private JButton btnLogin;
	private JPasswordField pfieldPassword;
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
	private ButtonGroup g2;
	private ButtonGroup g1;
	private JFormattedTextField ftFieldUserID;
	private MaskFormatter maskFUserID;
	private JTextField tFieldUsermngUserName;
	private JFormattedTextField fTFieldUsermngUserID;
	private JButton btnUsermngSearch;
	private JButton btnUserManagement;
	private JPanel scrPanelUsermng;
	private JButton btnUsermngShowAll;
	private JButton btnUsermngUnregis;
	private JButton btnUsermngModify;
	
	
	
	
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
			
			
			int userId = Integer.valueOf(ftFieldUserID.getText());
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
			gender = this.rdbtnMale.isSelected() ? "1" : this.rdbtnFemale.isSelected() ? "0" : "1";
			role = this.rdbtnAdmin.isSelected() ? "1" : this.rdbtnClient.isSelected() ? "0" : "1";
			
			
			boolean isValid = !(userName.isEmpty() && address.isEmpty() && email.isBlank() && bday.isBlank() && conNum.isBlank() && password.isBlank() && gender.isBlank() && role.isBlank());
			
			
			if(isValid) {
				
				
				
				String userID = this.tFieldYearRegis.getText() + String.valueOf(new Random().nextInt(999999));
				boolean isSuccess = true;
				try {
					Statement st = dtbConn.createStatement();
					String str = "INSERT INTO userinfo VALUES (" + userName + "," + address + "," + email + "," + bday + "," + conNum + "," + gender + "," + role + "," + userID + "," + password + ");";
					st.executeUpdate(str);
					
					
				} catch (SQLException e1) {
					isSuccess = false;
					
					JOptionPane.showMessageDialog(this, "Failed");
					
					
				}
				if(isSuccess) {
					JOptionPane.showMessageDialog(this, "Registration Success!");
					this.tFieldNameRegis.setText(""); this.tFieldAddrRegis.setText(""); this.tFieldEmailRegis.setText(""); this.tFieldDateRegis.setText(""); this.tFieldMonthRegis.setText("");
					this.tFieldYearRegis.setText(""); this.tFieldConRegis.setText(""); this.tfieldPassRegis.setText("");
					g1.clearSelection(); g2.clearSelection();
					movePanel(PNL_SEARCH);
				}
				
			}
			else {
				JOptionPane.showMessageDialog(this, "Empty Fields Detected!!!");
			}
			
			
			
			
		}
		
		
		
		else if(b == this.btnUserManagement) {
			movePanel(PNL_USER_MNG);
		}
		else if(b == this.btnUsermngSearch) {
			sectionUserManagement();
		}
		else if(b == this.btnUsermngShowAll) {
			sectionUserManagementShowAll();
		}
		
		else if(b == this.btnUsermngUnregis) {
			sectionUserManagementUnregis();
		}
		
	}
	
	private void sectionUserManagementUnregis() {
		
		
		if(JOptionPane.showConfirmDialog(this,"Are you sure?", "DELETE USER",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) return;
		
		
		boolean success = true;
		for(JCheckBox j : chkBoxUsermng) {
			
			if(j.isSelected()) {
				
				try {
					Statement st = dtbConn.createStatement();
					String userName;
					int idx = j.getText().indexOf("Name:") + 7;
					userName = "'" + j.getText().substring(idx) + "'";
					String stm = "DELETE FROM `userinfo` WHERE userName = " + userName;
					st.executeUpdate(stm);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					success = false;
					e.printStackTrace();
				}
				
				
			}
			
			
		}
		if(success) JOptionPane.showMessageDialog(this, "Deleted Successfuly!"); else JOptionPane.showMessageDialog(this, "Delete Failed!");
		scrPanelUsermng.removeAll();
		this.scrPanelUsermng.revalidate();
		chkBoxUsermng.clear();
	}
	
	
	
	private void sectionUserManagement() {
		
		String userName = "'" + this.tFieldUsermngUserName.getText() + "%'";
		String userID = this.fTFieldUsermngUserID.getText();
		
		
		this.scrPanelUsermng.removeAll();
		this.scrPanelUsermng.revalidate();
		//ArrayList<Client> clients = new ArrayList<Client>();
		Statement st;
		try {
			st = dtbConn.createStatement();
			
			if(userID.isEmpty() == false && userID.compareTo("0000000000") != 0) {
				chkBoxUsermng.clear();

				String stm = "SELECT * FROM `userinfo` WHERE userID = " + userID;
				ResultSet r = st.executeQuery(stm);
				
				while(r.next()) {
					
					String parsed = "UserID:  " + r.getString("userID") + " Name:  " + r.getString("userName");
					JCheckBox j = new JCheckBox(parsed);
					
					chkBoxUsermng.add(j);
					this.scrPanelUsermng.add(j);
				}
				
				
			}
			else if(!userName.isEmpty()) {
				chkBoxUsermng.clear();
				String stm = "SELECT * FROM `userinfo` WHERE userName LIKE " + userName;
				ResultSet r = st.executeQuery(stm);
				
				while(r.next()) {
					
					String parsed = "UserID:  " + r.getString("userID") + " Name:  " + r.getString("userName");
					JCheckBox j = new JCheckBox(parsed);
					chkBoxUsermng.add(j);
					this.scrPanelUsermng.add(j);
				}
				
				
				
			}
			
			this.scrPanelUsermng.revalidate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		
	}
	
	
	
	
	
	
	private void sectionUserManagementShowAll() {
		Statement st;
		try {
			chkBoxUsermng.clear();
			this.scrPanelUsermng.removeAll();
			this.scrPanelUsermng.revalidate();
			
			st = dtbConn.createStatement();
			String stm = "SELECT * FROM `userinfo`";
			ResultSet r = st.executeQuery(stm);
			
			while(r.next()) {
				
				String parsed = "UserID:  " + r.getString("userID") + " Name:  " + r.getString("userName");
				JCheckBox j = new JCheckBox(parsed);
				chkBoxUsermng.add(j);
				this.scrPanelUsermng.add(j);
			}
			
			this.scrPanelUsermng.revalidate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		
		try {
			this.maskFUserID = new MaskFormatter("##########");
			this.maskFUserID.setPlaceholderCharacter('0');
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
		
		
		
		ftFieldUserID = new JFormattedTextField(maskFUserID);
		ftFieldUserID.setBounds(127, 55, 146, 20);
		ftFieldUserID.setColumns(10);
		
		
		
		panel.add(ftFieldUserID);
		
		HomeScreen = new JPanel();
		HomeScreen.setBackground(new Color(128, 128, 128));
		mainPanel.add(HomeScreen, "name_66130146280600");
		HomeScreen.setLayout(null);
		
		JPanel leftContentPanel = new JPanel();
		leftContentPanel.setBackground(new Color(128, 128, 128));
		leftContentPanel.setBounds(0, 0, 195, 424);
		HomeScreen.add(leftContentPanel);
		leftContentPanel.setLayout(null);
		
		btnRegisterSelection = new JButton("Register");
		btnRegisterSelection.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegisterSelection.setBounds(10, 308, 176, 47);
		btnRegisterSelection.addActionListener(this);
		leftContentPanel.add(btnRegisterSelection);
		
		JButton btnBookManagement = new JButton("Book Management");
		btnBookManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookManagement.setBounds(10, 244, 176, 47);
		leftContentPanel.add(btnBookManagement);
		
		btnUserManagement = new JButton("User Management");
		btnUserManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUserManagement.setBounds(10, 186, 176, 47);
		btnUserManagement.addActionListener(this);
		leftContentPanel.add(btnUserManagement);
		
		JButton btnUserInformation = new JButton("User Information");
		btnUserInformation.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUserInformation.setBounds(10, 128, 176, 47);
		leftContentPanel.add(btnUserInformation);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReturn.setBounds(10, 70, 176, 47);
		leftContentPanel.add(btnReturn);
		
		JButton btnSeachrent = new JButton("Seach/Rent");
		btnSeachrent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSeachrent.setBounds(10, 11, 176, 47);
		leftContentPanel.add(btnSeachrent);
		
		JButton btnActivityLogs = new JButton("Activity Logs");
		btnActivityLogs.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnActivityLogs.setBounds(10, 366, 176, 47);
		leftContentPanel.add(btnActivityLogs);
		
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
		UserManagementPanel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("UserID");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 11, 46, 14);
		UserManagementPanel.add(lblNewLabel_3);
		
		fTFieldUsermngUserID = new JFormattedTextField(this.maskFUserID);
		fTFieldUsermngUserID.setBounds(54, 8, 115, 20);
		UserManagementPanel.add(fTFieldUsermngUserID);
		
		JLabel lblNewLabel_4 = new JLabel("User Name");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(179, 11, 61, 14);
		UserManagementPanel.add(lblNewLabel_4);
		
		tFieldUsermngUserName = new JTextField();
		tFieldUsermngUserName.setBounds(250, 8, 115, 20);
		UserManagementPanel.add(tFieldUsermngUserName);
		tFieldUsermngUserName.setColumns(10);
		
		btnUsermngSearch = new JButton("Search");
		btnUsermngSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngSearch.setBounds(10, 49, 95, 29);
		btnUsermngSearch.addActionListener(this);
		UserManagementPanel.add(btnUsermngSearch);
		
		
		
		scrPanelUsermng = new JPanel();
		scrPanelUsermng.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, null, null, null));
		scrPanelUsermng.setBackground(Color.WHITE);
		
		
		
		JScrollPane scrollPane = new JScrollPane(scrPanelUsermng);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 120, 267, 293);
		UserManagementPanel.add(scrollPane);
		scrollPane.setViewportView(scrPanelUsermng);
		scrPanelUsermng.setLayout(new BoxLayout(scrPanelUsermng, BoxLayout.Y_AXIS));
		
		btnUsermngUnregis = new JButton("UNREGISTER");
		btnUsermngUnregis.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngUnregis.setBounds(304, 120, 115, 29);
		btnUsermngUnregis.addActionListener(this);
		UserManagementPanel.add(btnUsermngUnregis);
		
		btnUsermngShowAll = new JButton("Show All");
		btnUsermngShowAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngShowAll.setBounds(126, 49, 114, 29);
		btnUsermngShowAll.addActionListener(this);
		UserManagementPanel.add(btnUsermngShowAll);
		
		btnUsermngModify = new JButton("MODIFY");
		btnUsermngModify.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngModify.setBounds(304, 160, 115, 29);
		UserManagementPanel.add(btnUsermngModify);
		//scrPanelUsermng.setBounds(scrollPane.getBounds());
		
		
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
		rdbtnMale.setBounds(134, 329, 72, 23);
		RegisterPanel.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(133, 355, 73, 23);
		RegisterPanel.add(rdbtnFemale);
		
		g1 = new ButtonGroup(); 
		g1.add(rdbtnFemale); 
		g1.add(rdbtnMale);  
		
		
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
		rdbtnAdmin.setBounds(292, 329, 80, 23);
		RegisterPanel.add(rdbtnAdmin);
		
		rdbtnClient = new JRadioButton("Client");
		rdbtnClient.setBounds(292, 355, 80, 23);
		RegisterPanel.add(rdbtnClient);
		g2 = new ButtonGroup();
		g2.add(rdbtnAdmin);
		g2.add(rdbtnClient);
		
		
		JLabel lblNewLabel_14 = new JLabel("Role");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_14.setBounds(292, 307, 46, 14);
		RegisterPanel.add(lblNewLabel_14);
		
		btnRegistration = new JButton("Register");
		btnRegistration.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistration.setBounds(194, 385, 103, 28);
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
