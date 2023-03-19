package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
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
import ui.UIBookManagement;
import ui.UIRenting;
import ui.UIReturn;
import ui.UIUserInfo;
import ui.UIUserRegistration;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;

public class Main extends JFrame implements ActionListener {
	
	private static UIBookManagement bookManagmentUI = new UIBookManagement();
	private static UIRenting uiRenting = new UIRenting();
	private static UIReturn uiReturn = new UIReturn();
	private static UIUserInfo uiUserInfo = new UIUserInfo();
	private static UIUserRegistration uiUserRegis = new UIUserRegistration();
	
	private static final int PNL_SEARCH = 0, PNL_RETURN = 1, PNL_USER_INFO = 2, PNL_USER_MNG = 3, PNL_BOOK_MNG = 4, PNL_REGIS = 5, PNL_LOG = 6;
	
	private static String dtbUrl = "jdbc:mysql://localhost:3306/lms_database", dtbUsername = "root", dtbPassword = "";
	private static Connection dtbConn;
	private static ArrayList<JCheckBox> chkBoxUsermng = new ArrayList<JCheckBox>();
	
	
	private JButton btnLogin;
	private JPasswordField pfieldPassword;
	private JPanel mainPanel;
	private JButton btnRegisterSelection;
	private JPanel rightContentPanel;
	private JPanel HomeScreen;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnMale;
	private JButton btnRegistration;
	private JPanel RegisterPanel;
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
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnBookManagement;
	private JButton btnSeachrent;
	private JButton btnReturn;
	private JButton btnUserInformation;
	
	
	
	
	private void movePanel(int idx) {
		
		
		CardLayout c = (CardLayout)(this.rightContentPanel.getLayout());
		c.first(rightContentPanel);
		for(int i = 0; i < idx; i++) c.next(rightContentPanel);
		
	}
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
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
					int role = r.getInt("userRole");
					
					if(role == 0) {
						JOptionPane.showMessageDialog(this, "User is not an administrator");
						pfieldPassword.setText("");
						return;
					}
					
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
				e1.printStackTrace();
			}
			
		}
		
		
		else if(b == this.btnRegisterSelection) {
			CardLayout c = (CardLayout)(this.rightContentPanel.getLayout());
			movePanel(PNL_REGIS);
		}
		
		
		
		else if(b == this.btnUserManagement) {
			movePanel(PNL_USER_MNG);
		}
		else if(b == this.btnBookManagement) {
			movePanel(PNL_BOOK_MNG);
		}
		else if(b == this.btnSeachrent) {
			movePanel(PNL_SEARCH);
		}
		else if(b == this.btnReturn) {
			movePanel(PNL_RETURN);
		}
		
		else if(b == this.btnUserInformation) {
			movePanel(PNL_USER_INFO);
		}
		
		else if(b == this.btnUsermngSearch) {
			sectionUserManagementSearch();
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
					
					ResultSet query = st.executeQuery("SELECT (userID) FROM userinfo WHERE userName = " + userName);
					if(query.next()) {
						
						String userID = String.valueOf( query.getLong("userID"));
						
						st.executeUpdate("INSERT INTO deleted_ids VALUES(1, " + userID + ")");
						
						String stm = "DELETE FROM `userinfo` WHERE userName = " + userName;
						st.executeUpdate(stm);
						
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					success = false;
					e.printStackTrace();
				}
				
				
			}
			
			
		}
		if(success) JOptionPane.showMessageDialog(this, "Deleted Successfuly!"); else JOptionPane.showMessageDialog(this, "Delete Failed!");
		scrPanelUsermng.removeAll();
		scrPanelUsermng.revalidate();
		scrPanelUsermng.repaint();
		chkBoxUsermng.clear();
	}
	
	
	
	private void sectionUserManagementSearch() {
		
		String userName = "'" + this.tFieldUsermngUserName.getText() + "%'";
		String userID = this.fTFieldUsermngUserID.getText();
		
		
		this.scrPanelUsermng.removeAll();
		this.scrPanelUsermng.revalidate();
		
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
			else if(!tFieldUsermngUserName.getText().isEmpty()) {
				
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
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrPanelUsermng.revalidate();
		scrPanelUsermng.repaint();
		
		
		
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
			uiRenting.dtbConn = dtbConn;
			uiReturn.dtbConn = dtbConn;
			uiUserInfo.dtbConn = dtbConn;
			uiUserRegis.dtbConn = dtbConn;
			Statement stm = dtbConn.createStatement();
			stm.executeUpdate("CREATE DATABASE IF NOT EXISTS lms_database");
			
			
			
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
	@SuppressWarnings("static-access")
	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/res/lms_icon.png")));
		bookManagmentUI.windowRef = this;
		uiUserInfo.windowRef = this;
		uiUserRegis.windowRef = this;
		
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
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(146, 209, 351, 158);
		Login.add(panel);
		panel.setLayout(null);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setForeground(new Color(27, 27, 27));
		lblLogin.setBounds(127, 0, 83, 55);
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel.add(lblLogin);
		
		pfieldPassword = new JPasswordField();
		pfieldPassword.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pfieldPassword.setBackground(new Color(192, 192, 192));
		pfieldPassword.setBounds(127, 83, 146, 20);
		panel.add(pfieldPassword);
		
		JLabel lblUserID = new JLabel("UserID");
		lblUserID.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblUserID.setBackground(new Color(255, 163, 26));
		lblUserID.setForeground(new Color(27, 27, 27));
		lblUserID.setOpaque(true);
		lblUserID.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserID.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUserID.setBounds(45, 55, 83, 20);
		panel.add(lblUserID);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblPassword.setBackground(new Color(255, 163, 26));
		lblPassword.setForeground(new Color(27, 27, 27));
		lblPassword.setOpaque(true);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(45, 83, 83, 20);
		panel.add(lblPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(new Color(223, 156, 32));
		btnLogin.setBounds(121, 124, 89, 23);
		btnLogin.addActionListener(this);
		
		panel.add(btnLogin);
		
		
		
		ftFieldUserID = new JFormattedTextField(maskFUserID);
		ftFieldUserID.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		ftFieldUserID.setBackground(new Color(192, 192, 192));
		ftFieldUserID.setBounds(127, 55, 146, 20);
		ftFieldUserID.setColumns(10);
		
		
		
		panel.add(ftFieldUserID);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new BevelBorder(BevelBorder.RAISED, Color.YELLOW, null, null, null));
		panel_6.setBackground(Color.GRAY);
		panel_6.setBounds(77, 31, 497, 167);
		Login.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_34 = new JLabel("");
		lblNewLabel_34.setBounds(10, 11, 477, 145);
		lblNewLabel_34.setIcon( new ImageIcon (new ImageIcon(Main.class.getResource("/res/LMS_banner.png")).getImage().getScaledInstance(lblNewLabel_34.getWidth(), lblNewLabel_34.getHeight(), Image.SCALE_SMOOTH) ) );
		panel_6.add(lblNewLabel_34);
		
		HomeScreen = new JPanel();
		HomeScreen.setBackground(new Color(128, 128, 128));
		mainPanel.add(HomeScreen, "name_66130146280600");
		HomeScreen.setLayout(null);
		
		JPanel leftContentPanel = new JPanel();
		leftContentPanel.setBackground(new Color(128, 128, 128));
		leftContentPanel.setBounds(0, 0, 195, 424);
		HomeScreen.add(leftContentPanel);
		leftContentPanel.setLayout(null);
		
		btnRegisterSelection = new JButton("");
		btnRegisterSelection.setBounds(10, 308, 176, 47);
		btnRegisterSelection.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/Register.png")).getImage().getScaledInstance(btnRegisterSelection.getWidth(), btnRegisterSelection.getHeight(), Image.SCALE_SMOOTH)));
		btnRegisterSelection.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegisterSelection.addActionListener(this);
		leftContentPanel.add(btnRegisterSelection);
		
		btnBookManagement = new JButton(""); btnBookManagement.setBounds(10, 244, 176, 47);
		btnBookManagement.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/Book management.png")).getImage().getScaledInstance(btnBookManagement.getWidth(), btnBookManagement.getHeight(), Image.SCALE_SMOOTH)));
		btnBookManagement.addActionListener(this);
		btnBookManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(btnBookManagement);
		
		btnUserManagement = new JButton("");
		btnUserManagement.setBounds(10, 186, 176, 47);
		btnUserManagement.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/User management.png")).getImage().getScaledInstance(btnUserManagement.getWidth(), btnUserManagement.getHeight(), Image.SCALE_SMOOTH)));
		btnUserManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUserManagement.addActionListener(this);
		leftContentPanel.add(btnUserManagement);
		
		btnUserInformation = new JButton(""); 
		btnUserInformation.setBounds(10, 128, 176, 47);
		btnUserInformation.addActionListener(this);
		btnUserInformation.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/User Information.png")).getImage().getScaledInstance(btnUserInformation.getWidth(), btnUserInformation.getHeight(), Image.SCALE_SMOOTH)));
		btnUserInformation.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(btnUserInformation);
		
		btnReturn = new JButton(""); 
		btnReturn.setBounds(10, 70, 176, 47);
		btnReturn.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/Return.png")).getImage().getScaledInstance(btnReturn.getWidth(), btnReturn.getHeight(), Image.SCALE_SMOOTH)));
		btnReturn.addActionListener(this);
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		leftContentPanel.add(btnReturn);
		
		btnSeachrent = new JButton(""); 
		btnSeachrent.setBounds(10, 11, 176, 47);
		btnSeachrent.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/Search Rent.png")).getImage().getScaledInstance(btnSeachrent.getWidth(), btnSeachrent.getHeight(), Image.SCALE_SMOOTH)));
		btnSeachrent.addActionListener(this);
		btnSeachrent.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		leftContentPanel.add(btnSeachrent);
		
		JButton btnActivityLogs = new JButton("");
		btnActivityLogs.setBounds(10, 366, 176, 47);
		btnActivityLogs.setIcon( new ImageIcon(new ImageIcon(Main.class.getResource("/res/Activity logs.png")).getImage().getScaledInstance(btnActivityLogs.getWidth(), btnActivityLogs.getHeight(), Image.SCALE_SMOOTH)));
		btnActivityLogs.setFont(new Font("Tahoma", Font.BOLD, 12));
		leftContentPanel.add(btnActivityLogs);
		
		rightContentPanel = new JPanel();
		rightContentPanel.setBounds(199, 0, 460, 424);
		HomeScreen.add(rightContentPanel);
		rightContentPanel.setLayout(new CardLayout(0, 0));
		
		JPanel SearchRentPanel = new JPanel();
		SearchRentPanel.setBackground(new Color(27, 27, 27));
		rightContentPanel.add(SearchRentPanel, "name_72204072661900");
		SearchRentPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(10, 87, 440, 133);
		SearchRentPanel.add(scrollPane_1);
		
		JPanel panel_2 = new JPanel(); 
		panel_2.setBackground(new Color(128, 128, 128));uiRenting.panel1 = panel_2;
		scrollPane_1.setViewportView(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_15 = new JLabel("Book Name");
		lblNewLabel_15.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);lblNewLabel_15.setOpaque(true);
		lblNewLabel_15.setBackground(new Color(255, 163, 26));
		lblNewLabel_15.setForeground(new Color(27, 27, 27));
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_15.setBounds(10, 9, 76, 20);
		SearchRentPanel.add(lblNewLabel_15);
		
		JTextField textField = new JTextField(); 
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textField.setBackground(new Color(128, 128, 128));
		textField.setForeground(new Color(27, 27, 27));uiRenting.tfBookName = textField;
		textField.setBounds(86, 9, 154, 20);
		SearchRentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_20 = new JLabel("Category");
		lblNewLabel_20.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_20.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_20.setBackground(new Color(255, 163, 26));
		lblNewLabel_20.setOpaque(true);
		lblNewLabel_20.setForeground(new Color(27, 27, 27));
		lblNewLabel_20.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_20.setBounds(10, 34, 76, 20);
		SearchRentPanel.add(lblNewLabel_20);
		
		JTextField textField_3 = new JTextField(); 
		textField_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textField_3.setBackground(new Color(128, 128, 128));
		textField_3.setForeground(new Color(27, 27, 27));uiRenting.tfCategory = textField_3;
		textField_3.setBounds(86, 34, 154, 20);
		SearchRentPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_21 = new JLabel("Author");
		lblNewLabel_21.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_21.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_21.setBackground(new Color(255, 163, 26));
		lblNewLabel_21.setOpaque(true);
		lblNewLabel_21.setForeground(new Color(27, 27, 27));
		lblNewLabel_21.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_21.setBounds(10, 60, 76, 20);
		SearchRentPanel.add(lblNewLabel_21);
		
		JTextField textField_4 = new JTextField(); 
		textField_4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textField_4.setForeground(new Color(27, 27, 27));
		textField_4.setBackground(new Color(128, 128, 128));uiRenting.tfAuthor = textField_4;
		textField_4.setBounds(86, 60, 154, 20);
		SearchRentPanel.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("SEARCH"); 
		btnNewButton_4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_4.setForeground(Color.BLACK);
		btnNewButton_4.setBackground(new Color(255, 163, 26));uiRenting.btnSearch = btnNewButton_4;
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_4.setBounds(250, 7, 89, 23);
		SearchRentPanel.add(btnNewButton_4);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(10, 260, 440, 116);
		SearchRentPanel.add(scrollPane_2);
		
		JPanel panel_3 = new JPanel(); 
		panel_3.setBackground(new Color(128, 128, 128));uiRenting.panel2 = panel_3;
		scrollPane_2.setViewportView(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		uiRenting.windowRef = this;
		
		JLabel lblNewLabel_22 = new JLabel("Book To Rent");
		lblNewLabel_22.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_22.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_22.setOpaque(true);
		lblNewLabel_22.setForeground(new Color(27, 27, 27));
		lblNewLabel_22.setBackground(new Color(255, 163, 26));
		lblNewLabel_22.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_22.setBounds(171, 231, 105, 23);
		SearchRentPanel.add(lblNewLabel_22);
		
		JButton btnNewButton_5 = new JButton("ADD"); 
		btnNewButton_5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));uiRenting.btnAdd = btnNewButton_5;
		btnNewButton_5.setBackground(new Color(255, 163, 26));
		btnNewButton_5.setForeground(new Color(27, 27, 27));
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_5.setBounds(250, 33, 89, 23);
		SearchRentPanel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("REMOVE"); 
		btnNewButton_6.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_6.setBackground(new Color(255, 163, 26));
		btnNewButton_6.setForeground(new Color(27, 27, 27));uiRenting.btnRemove = btnNewButton_6;
		btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_6.setBounds(349, 387, 89, 36);
		SearchRentPanel.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("CONFIRM"); 
		btnNewButton_7.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_7.setForeground(new Color(27, 27, 27));
		btnNewButton_7.setBackground(new Color(255, 163, 26));uiRenting.btnConfirm = btnNewButton_7;
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_7.setBounds(228, 387, 105, 36);
		SearchRentPanel.add(btnNewButton_7);
		
		JLabel lblNewLabel_23 = new JLabel("UserID");
		lblNewLabel_23.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_23.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_23.setBackground(new Color(255, 163, 26));
		lblNewLabel_23.setOpaque(true);
		lblNewLabel_23.setForeground(new Color(27, 27, 27));
		lblNewLabel_23.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_23.setBounds(10, 396, 56, 20);
		SearchRentPanel.add(lblNewLabel_23);
		
		JFormattedTextField formattedTextField_3 = new JFormattedTextField(uiRenting.idFormatter); 
		formattedTextField_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		formattedTextField_3.setForeground(new Color(0, 0, 0));
		formattedTextField_3.setBackground(new Color(128, 128, 128));uiRenting.ftfUserID = formattedTextField_3;
		formattedTextField_3.setBounds(68, 396, 116, 20);
		SearchRentPanel.add(formattedTextField_3);
		
		JPanel ReturnPanel = new JPanel();
		ReturnPanel.setForeground(new Color(128, 128, 128));
		ReturnPanel.setBackground(new Color(27, 27, 27));
		rightContentPanel.add(ReturnPanel, "name_72224483657400");
		ReturnPanel.setLayout(null);
		
		JLabel lblNewLabel_24 = new JLabel("UserID");
		lblNewLabel_24.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_24.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_24.setBackground(new Color(255, 163, 26));
		lblNewLabel_24.setOpaque(true);
		lblNewLabel_24.setForeground(new Color(27, 27, 27));
		lblNewLabel_24.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_24.setBounds(10, 21, 66, 20);
		ReturnPanel.add(lblNewLabel_24);
		
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField(uiReturn.idFormatter); 
		formattedTextField_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 163, 26)));
		formattedTextField_2.setBackground(new Color(128, 128, 128));uiReturn.ftfID = formattedTextField_2;
		formattedTextField_2.setBounds(77, 21, 119, 20);
		ReturnPanel.add(formattedTextField_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setBounds(10, 144, 440, 269);
		ReturnPanel.add(scrollPane_3);
		
		JPanel panel_4 = new JPanel(); 
		panel_4.setBackground(new Color(128, 128, 128));uiReturn.panel = panel_4;
		scrollPane_3.setViewportView(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JButton btnNewButton_8 = new JButton("ENTER"); 
		btnNewButton_8.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_8.setBackground(new Color(255, 163, 26));uiReturn.btnEnter = btnNewButton_8;
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_8.setBounds(206, 19, 89, 23);
		ReturnPanel.add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("RETURN"); 
		btnNewButton_9.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_9.setBackground(new Color(255, 163, 26));uiReturn.btnReturn = btnNewButton_9;
		btnNewButton_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_9.setBounds(361, 110, 89, 23);
		ReturnPanel.add(btnNewButton_9);
		
		JPanel UserInformationPanel = new JPanel(); 
		UserInformationPanel.setBackground(new Color(27, 27, 27));
		rightContentPanel.add(UserInformationPanel, "name_72227231251400");
		UserInformationPanel.setLayout(null);
		
		JLabel lblNewLabel_25 = new JLabel("UserID");
		lblNewLabel_25.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_25.setBackground(new Color(255, 163, 26));
		lblNewLabel_25.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_25.setOpaque(true);
		lblNewLabel_25.setForeground(new Color(27, 27, 27));
		lblNewLabel_25.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_25.setBounds(10, 9, 104, 20);
		UserInformationPanel.add(lblNewLabel_25);
		
		JFormattedTextField formattedTextField_4 = new JFormattedTextField(uiReturn.idFormatter); 
		formattedTextField_4.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		formattedTextField_4.setBackground(new Color(128, 128, 128));uiUserInfo.ftfID = formattedTextField_4;
		formattedTextField_4.setBounds(124, 9, 181, 20);
		UserInformationPanel.add(formattedTextField_4);
		
		JButton btnNewButton_10 = new JButton("ENTER"); 
		btnNewButton_10.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_10.setBackground(new Color(255, 163, 26));uiUserInfo.btnEnter = btnNewButton_10; uiUserInfo.initActionResponse();
		btnNewButton_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_10.setBounds(347, 8, 89, 23);
		UserInformationPanel.add(btnNewButton_10);
		
		JLabel lblNewLabel_26 = new JLabel("Name:");
		lblNewLabel_26.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_26.setBackground(new Color(255, 163, 26));
		lblNewLabel_26.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_26.setOpaque(true);
		lblNewLabel_26.setForeground(new Color(27, 27, 27));
		lblNewLabel_26.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_26.setBounds(10, 40, 104, 20);
		UserInformationPanel.add(lblNewLabel_26);
		
		JLabel lblNewLabel_27 = new JLabel(""); 
		lblNewLabel_27.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		lblNewLabel_27.setBackground(new Color(128, 128, 128));uiUserInfo.tfName = lblNewLabel_27;
		lblNewLabel_27.setOpaque(true);
		lblNewLabel_27.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_27.setBounds(124, 40, 181, 20);
		UserInformationPanel.add(lblNewLabel_27);
		
		JLabel lblNewLabel_28 = new JLabel("Address:");
		lblNewLabel_28.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_28.setBackground(new Color(255, 163, 26));
		lblNewLabel_28.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_28.setOpaque(true);
		lblNewLabel_28.setForeground(new Color(27, 27, 27));
		lblNewLabel_28.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_28.setBounds(10, 71, 104, 20);
		UserInformationPanel.add(lblNewLabel_28);
		
		JLabel lblNewLabel_29 = new JLabel("Email:");
		lblNewLabel_29.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_29.setBackground(new Color(255, 163, 26));
		lblNewLabel_29.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_29.setOpaque(true);
		lblNewLabel_29.setForeground(new Color(27, 27, 27));
		lblNewLabel_29.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_29.setBounds(10, 102, 104, 20);
		UserInformationPanel.add(lblNewLabel_29);
		
		JLabel lblNewLabel_30 = new JLabel("Contact No.");
		lblNewLabel_30.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_30.setBackground(new Color(255, 163, 26));
		lblNewLabel_30.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_30.setOpaque(true);
		lblNewLabel_30.setForeground(new Color(27, 27, 27));
		lblNewLabel_30.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_30.setBounds(10, 133, 104, 20);
		UserInformationPanel.add(lblNewLabel_30);
		
		JLabel lblNewLabel_31 = new JLabel("Gender:");
		lblNewLabel_31.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_31.setBackground(new Color(255, 163, 26));
		lblNewLabel_31.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_31.setOpaque(true);
		lblNewLabel_31.setForeground(new Color(27, 27, 27));
		lblNewLabel_31.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_31.setBounds(10, 164, 104, 20);
		UserInformationPanel.add(lblNewLabel_31);
		
		JLabel lblNewLabel_32 = new JLabel("Role:");
		lblNewLabel_32.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_32.setBackground(new Color(255, 163, 26));
		lblNewLabel_32.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_32.setOpaque(true);
		lblNewLabel_32.setForeground(new Color(27, 27, 27));
		lblNewLabel_32.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_32.setBounds(315, 164, 70, 20);
		UserInformationPanel.add(lblNewLabel_32);
		
		JLabel lblNewLabel_33 = new JLabel(""); 
		lblNewLabel_33.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		lblNewLabel_33.setBackground(new Color(128, 128, 128));lblNewLabel_33.setOpaque(true); uiUserInfo.tfAddress = lblNewLabel_33;
		lblNewLabel_33.setBounds(124, 71, 181, 20);
		UserInformationPanel.add(lblNewLabel_33);
		
		JLabel lblNewLabel_33_1 = new JLabel(""); 
		lblNewLabel_33_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		lblNewLabel_33_1.setBackground(new Color(128, 128, 128));uiUserInfo.tfEmail = lblNewLabel_33_1;
		lblNewLabel_33_1.setOpaque(true);
		lblNewLabel_33_1.setBounds(124, 102, 181, 20);
		UserInformationPanel.add(lblNewLabel_33_1);
		
		JLabel lblNewLabel_33_1_1 = new JLabel(""); 
		lblNewLabel_33_1_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		lblNewLabel_33_1_1.setBackground(new Color(128, 128, 128));uiUserInfo.tfContactNo = lblNewLabel_33_1_1;
		lblNewLabel_33_1_1.setOpaque(true);
		lblNewLabel_33_1_1.setBounds(124, 133, 181, 20);
		UserInformationPanel.add(lblNewLabel_33_1_1);
		
		JLabel lblNewLabel_33_1_2 = new JLabel(""); 
		lblNewLabel_33_1_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		lblNewLabel_33_1_2.setBackground(new Color(128, 128, 128));uiUserInfo.tfGender = lblNewLabel_33_1_2;
		lblNewLabel_33_1_2.setOpaque(true);
		lblNewLabel_33_1_2.setBounds(124, 164, 181, 20);
		UserInformationPanel.add(lblNewLabel_33_1_2);
		
		JLabel lblNewLabel_33_1_3 = new JLabel(""); 
		lblNewLabel_33_1_3.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), null));
		lblNewLabel_33_1_3.setBackground(new Color(128, 128, 128));uiUserInfo.tfRole = lblNewLabel_33_1_3;
		lblNewLabel_33_1_3.setOpaque(true);
		lblNewLabel_33_1_3.setBounds(395, 164, 55, 20);
		UserInformationPanel.add(lblNewLabel_33_1_3);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 195, 440, 218);
		UserInformationPanel.add(scrollPane_4);
		
		JPanel panel_5 = new JPanel(); 
		panel_5.setBackground(new Color(128, 128, 128));uiUserInfo.panel = panel_5;
		scrollPane_4.setViewportView(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel UserManagementPanel = new JPanel();
		UserManagementPanel.setBackground(new Color(27, 27, 27));
		rightContentPanel.add(UserManagementPanel, "name_72229635802400");
		UserManagementPanel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("UserID");
		lblNewLabel_3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBackground(new Color(255, 163, 26));
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setForeground(new Color(27, 27, 27));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 8, 78, 20);
		UserManagementPanel.add(lblNewLabel_3);
		
		fTFieldUsermngUserID = new JFormattedTextField(this.maskFUserID);
		fTFieldUsermngUserID.setBackground(new Color(128, 128, 128));
		fTFieldUsermngUserID.setBounds(89, 8, 122, 20);
		UserManagementPanel.add(fTFieldUsermngUserID);
		
		JLabel lblNewLabel_4 = new JLabel("User Name");
		lblNewLabel_4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBackground(new Color(255, 163, 26));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(new Color(27, 27, 27));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(10, 32, 78, 20);
		UserManagementPanel.add(lblNewLabel_4);
		
		tFieldUsermngUserName = new JTextField();
		tFieldUsermngUserName.setBackground(new Color(128, 128, 128));
		tFieldUsermngUserName.setBounds(89, 32, 122, 20);
		UserManagementPanel.add(tFieldUsermngUserName);
		tFieldUsermngUserName.setColumns(10);
		
		btnUsermngSearch = new JButton("Search");
		btnUsermngSearch.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnUsermngSearch.setBackground(new Color(255, 163, 26));
		btnUsermngSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngSearch.setBounds(221, 3, 114, 29);
		btnUsermngSearch.addActionListener(this);
		UserManagementPanel.add(btnUsermngSearch);
		
		
		
		scrPanelUsermng = new JPanel();
		scrPanelUsermng.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, null, null, null));
		scrPanelUsermng.setBackground(new Color(128, 128, 128));
		
		
		
		JScrollPane scrollPane = new JScrollPane(scrPanelUsermng);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 83, 440, 330);
		UserManagementPanel.add(scrollPane);
		scrollPane.setViewportView(scrPanelUsermng);
		scrPanelUsermng.setLayout(new BoxLayout(scrPanelUsermng, BoxLayout.Y_AXIS));
		
		btnUsermngUnregis = new JButton("UNREGISTER");
		btnUsermngUnregis.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnUsermngUnregis.setBackground(new Color(255, 163, 26));
		btnUsermngUnregis.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngUnregis.setBounds(345, 3, 115, 29);
		btnUsermngUnregis.addActionListener(this);
		UserManagementPanel.add(btnUsermngUnregis);
		
		btnUsermngShowAll = new JButton("Show All");
		btnUsermngShowAll.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnUsermngShowAll.setBackground(new Color(255, 163, 26));
		btnUsermngShowAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngShowAll.setBounds(221, 43, 114, 29);
		btnUsermngShowAll.addActionListener(this);
		UserManagementPanel.add(btnUsermngShowAll);
		
		btnUsermngModify = new JButton("MODIFY");
		btnUsermngModify.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnUsermngModify.setBackground(new Color(255, 163, 26));
		btnUsermngModify.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUsermngModify.setBounds(345, 43, 115, 29);
		UserManagementPanel.add(btnUsermngModify);
		//scrPanelUsermng.setBounds(scrollPane.getBounds());
		
		
		JPanel BookManagementPanel = new JPanel();
		BookManagementPanel.setBackground(new Color(27, 27, 27));
		rightContentPanel.add(BookManagementPanel, "name_72232200085800");
		BookManagementPanel.setLayout(null);
		
		bookManagmentUI.dtbConn = this.dtbConn;
		
		JPanel panel_1 = new JPanel(); 
		panel_1.setBackground(new Color(128, 128, 128));bookManagmentUI.panel = panel_1;
		
		
		JScrollPane scrollPaneBookMng = new JScrollPane(panel_1); bookManagmentUI.scrollPane = scrollPaneBookMng;
		scrollPaneBookMng.setBounds(10, 160, 440, 253);
		BookManagementPanel.add(scrollPaneBookMng);
		
		
		
		scrollPaneBookMng.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_5 = new JLabel("ID");
		lblNewLabel_5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBackground(new Color(255, 163, 26));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setForeground(new Color(27, 27, 27));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(10, 32, 82, 20);
		BookManagementPanel.add(lblNewLabel_5);
		
		JFormattedTextField formattedTextField = new JFormattedTextField(bookManagmentUI.idFormatter); 
		formattedTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formattedTextField.setBackground(new Color(128, 128, 128));bookManagmentUI.ftfID = formattedTextField;
		formattedTextField.setBounds(93, 32, 132, 20);
		BookManagementPanel.add(formattedTextField);
		
		JLabel lblBookMgnName = new JLabel("Name");
		lblBookMgnName.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblBookMgnName.setOpaque(true);
		lblBookMgnName.setBackground(new Color(255, 163, 26));
		lblBookMgnName.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookMgnName.setForeground(new Color(27, 27, 27));
		lblBookMgnName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBookMgnName.setBounds(10, 5, 82, 20);
		BookManagementPanel.add(lblBookMgnName);
		
		JTextField txtFieldNameBookMng = new JTextField(); 
		txtFieldNameBookMng.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtFieldNameBookMng.setBackground(new Color(128, 128, 128));bookManagmentUI.tfName = txtFieldNameBookMng;
		txtFieldNameBookMng.setBounds(93, 5, 132, 20);
		BookManagementPanel.add(txtFieldNameBookMng);
		txtFieldNameBookMng.setColumns(10);
		
		JLabel lblNewLabel_16 = new JLabel("Category"); 
		lblNewLabel_16.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_16.setOpaque(true);
		lblNewLabel_16.setBackground(new Color(255, 163, 26));
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_16.setForeground(new Color(27, 27, 27));
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_16.setBounds(10, 59, 82, 21);
		BookManagementPanel.add(lblNewLabel_16);
		
		textField_1 = new JTextField(); 
		textField_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textField_1.setBackground(new Color(128, 128, 128));bookManagmentUI.tfCategory = textField_1;
		textField_1.setBounds(93, 59, 132, 21);
		BookManagementPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_17 = new JLabel("Author");
		lblNewLabel_17.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_17.setOpaque(true);
		lblNewLabel_17.setBackground(new Color(255, 163, 26));
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_17.setForeground(new Color(27, 27, 27));
		lblNewLabel_17.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_17.setBounds(10, 89, 82, 20);
		BookManagementPanel.add(lblNewLabel_17);
		
		textField_2 = new JTextField(); 
		textField_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textField_2.setBackground(new Color(128, 128, 128));bookManagmentUI.tfAuthor = textField_2;
		textField_2.setBounds(93, 89, 132, 20);
		BookManagementPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("Year");
		lblNewLabel_18.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_18.setOpaque(true);
		lblNewLabel_18.setBackground(new Color(255, 163, 26));
		lblNewLabel_18.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_18.setForeground(new Color(27, 27, 27));
		lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_18.setBounds(10, 117, 82, 20);
		BookManagementPanel.add(lblNewLabel_18);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField(bookManagmentUI.yearFormatter); 
		formattedTextField_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formattedTextField_1.setBackground(new Color(128, 128, 128));bookManagmentUI.ftfYear = formattedTextField_1;
		formattedTextField_1.setBounds(93, 117, 132, 20);
		BookManagementPanel.add(formattedTextField_1);
		
		JButton btnNewButton = new JButton("CREATE"); 
		btnNewButton.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton.setBackground(new Color(255, 163, 26));bookManagmentUI.btnCreate = btnNewButton;
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(361, 8, 89, 23);
		BookManagementPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DELETE"); 
		btnNewButton_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_1.setBackground(new Color(255, 163, 26));bookManagmentUI.btnDelete = btnNewButton_1;
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(266, 44, 108, 23);
		BookManagementPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("SEARCH"); 
		btnNewButton_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_2.setBackground(new Color(255, 163, 26));bookManagmentUI.btnSearch = btnNewButton_2;
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBounds(266, 8, 89, 23);
		BookManagementPanel.add(btnNewButton_2);
		
		JLabel lblNewLabel_19 = new JLabel("QTY:"); 
		lblNewLabel_19.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_19.setOpaque(true);
		lblNewLabel_19.setBackground(new Color(255, 163, 26));
		lblNewLabel_19.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_19.setForeground(new Color(27, 27, 27));
		lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_19.setBounds(235, 117, 46, 21);
		BookManagementPanel.add(lblNewLabel_19);
		
		JButton btnNewButton_3 = new JButton("ADD"); 
		btnNewButton_3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_3.setBackground(new Color(255, 163, 26));bookManagmentUI.btnAdd = btnNewButton_3;
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3.setBounds(292, 117, 82, 20);
		BookManagementPanel.add(btnNewButton_3);
		
		JButton btnNewButton_3_1 = new JButton("SUB"); 
		btnNewButton_3_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnNewButton_3_1.setBackground(new Color(255, 163, 26));bookManagmentUI.btnSubtract = btnNewButton_3_1;
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3_1.setBounds(378, 117, 82, 20);
		BookManagementPanel.add(btnNewButton_3_1);
		
		JButton btnNewButton_11 = new JButton("SHOW ALL"); 
		btnNewButton_11.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));bookManagmentUI.btnShowAll = btnNewButton_11;
		btnNewButton_11.setBackground(new Color(255, 163, 26));
		btnNewButton_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_11.setBounds(266, 82, 108, 23);
		BookManagementPanel.add(btnNewButton_11);
		
		RegisterPanel = new JPanel();
		RegisterPanel.setForeground(new Color(255, 255, 255));
		RegisterPanel.setBackground(new Color(27, 27, 27));
		rightContentPanel.add(RegisterPanel, "name_72244918873200");
		RegisterPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("User Registration");
		lblNewLabel_1.setForeground(new Color(255, 163, 26));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(134, 0, 176, 43);
		RegisterPanel.add(lblNewLabel_1);
		
		JTextField tFieldNameRegis = new JTextField(); 
		tFieldNameRegis.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tFieldNameRegis.setBackground(new Color(128, 128, 128));uiUserRegis.tfName = tFieldNameRegis;
		tFieldNameRegis.setBounds(134, 54, 278, 20);
		RegisterPanel.add(tFieldNameRegis);
		tFieldNameRegis.setColumns(10);
		
		JTextField tFieldAddrRegis = new JTextField(); 
		tFieldAddrRegis.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tFieldAddrRegis.setBackground(new Color(128, 128, 128));uiUserRegis.tfAddress = tFieldAddrRegis;
		tFieldAddrRegis.setBounds(134, 100, 278, 20);
		RegisterPanel.add(tFieldAddrRegis);
		tFieldAddrRegis.setColumns(10);
		
		JTextField tFieldEmailRegis = new JTextField(); 
		tFieldEmailRegis.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tFieldEmailRegis.setBackground(new Color(128, 128, 128));uiUserRegis.tfEmail = tFieldEmailRegis;
		tFieldEmailRegis.setBounds(134, 147, 278, 20);
		RegisterPanel.add(tFieldEmailRegis);
		tFieldEmailRegis.setColumns(10);
		
		rdbtnMale = new JRadioButton("Male"); 
		rdbtnMale.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnMale.setBackground(new Color(255, 163, 26));uiUserRegis.rbMale = rdbtnMale; uiUserRegis.bgGender.add(rdbtnMale);
		rdbtnMale.setBounds(134, 329, 73, 23);
		RegisterPanel.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female"); 
		rdbtnFemale.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnFemale.setBackground(new Color(255, 163, 26));uiUserRegis.rbFemale = rdbtnFemale; uiUserRegis.bgGender.add(rdbtnFemale);
		rdbtnFemale.setBounds(134, 355, 73, 23);
		RegisterPanel.add(rdbtnFemale);
		
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBackground(new Color(255, 163, 26));
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setForeground(new Color(27, 27, 27));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(39, 54, 96, 20);
		RegisterPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("Address");
		lblNewLabel_6.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBackground(new Color(255, 163, 26));
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setForeground(new Color(27, 27, 27));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(39, 100, 96, 20);
		RegisterPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Email");
		lblNewLabel_7.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setBackground(new Color(255, 163, 26));
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setForeground(new Color(27, 27, 27));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(39, 147, 96, 20);
		RegisterPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Birthday");
		lblNewLabel_8.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBackground(new Color(255, 163, 26));
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setForeground(new Color(27, 27, 27));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setBounds(39, 196, 96, 20);
		RegisterPanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Date");
		lblNewLabel_9.setForeground(new Color(255, 255, 255));
		lblNewLabel_9.setBounds(134, 215, 46, 14);
		RegisterPanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Month");
		lblNewLabel_10.setForeground(new Color(255, 255, 255));
		lblNewLabel_10.setBounds(230, 215, 46, 14);
		RegisterPanel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Year");
		lblNewLabel_11.setForeground(new Color(255, 255, 255));
		lblNewLabel_11.setBounds(326, 215, 46, 14);
		RegisterPanel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("Contact No.");
		lblNewLabel_12.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setBackground(new Color(255, 163, 26));
		lblNewLabel_12.setOpaque(true);
		lblNewLabel_12.setForeground(new Color(27, 27, 27));
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_12.setBounds(39, 241, 96, 20);
		RegisterPanel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Gender");
		lblNewLabel_13.setForeground(new Color(255, 255, 255));
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_13.setBounds(134, 308, 96, 14);
		RegisterPanel.add(lblNewLabel_13);
		
		JRadioButton rdbtnAdmin = new JRadioButton("Admin"); 
		rdbtnAdmin.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnAdmin.setBackground(new Color(255, 163, 26));uiUserRegis.rbAdmin = rdbtnAdmin; uiUserRegis.bgRole.add(rdbtnAdmin);
		rdbtnAdmin.setBounds(292, 329, 80, 23);
		RegisterPanel.add(rdbtnAdmin);
		
		JRadioButton rdbtnClient = new JRadioButton("Client"); 
		rdbtnClient.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnClient.setBackground(new Color(255, 163, 26));uiUserRegis.rbClient = rdbtnClient; uiUserRegis.bgRole.add(rdbtnClient);
		rdbtnClient.setBounds(292, 355, 80, 23);
		RegisterPanel.add(rdbtnClient);
		
		
		
		JLabel lblNewLabel_14 = new JLabel("Role");
		lblNewLabel_14.setForeground(new Color(255, 255, 255));
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_14.setBounds(292, 307, 46, 14);
		RegisterPanel.add(lblNewLabel_14);
		
		btnRegistration = new JButton("Register"); 
		btnRegistration.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		btnRegistration.setBackground(new Color(255, 163, 26));uiUserRegis.btnRegister = btnRegistration; btnRegistration.addActionListener(uiUserRegis);
		btnRegistration.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistration.setBounds(194, 385, 103, 28);
		RegisterPanel.add(btnRegistration);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(128, 128, 128)));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(255, 163, 26));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setForeground(new Color(27, 27, 27));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(39, 272, 96, 20);
		RegisterPanel.add(lblNewLabel);
		
		JTextField tfieldPassRegis = new JTextField(); 
		tfieldPassRegis.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tfieldPassRegis.setBackground(new Color(128, 128, 128));uiUserRegis.tfPass = tfieldPassRegis;
		tfieldPassRegis.setBounds(134, 272, 278, 20);
		RegisterPanel.add(tfieldPassRegis);
		tfieldPassRegis.setColumns(10);
		
		JFormattedTextField formattedTextField_5 = new JFormattedTextField(uiUserRegis.dateFormatter); 
		formattedTextField_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formattedTextField_5.setBackground(new Color(128, 128, 128));uiUserRegis.ftfDate = formattedTextField_5;
		formattedTextField_5.setBounds(134, 196, 59, 20);
		RegisterPanel.add(formattedTextField_5);
		
		JFormattedTextField formattedTextField_5_1 = new JFormattedTextField(uiUserRegis.monthFormatter); 
		formattedTextField_5_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formattedTextField_5_1.setBackground(new Color(128, 128, 128));uiUserRegis.ftfMonth = formattedTextField_5_1;
		formattedTextField_5_1.setBounds(217, 196, 59, 20);
		RegisterPanel.add(formattedTextField_5_1);
		
		JFormattedTextField formattedTextField_5_1_1 = new JFormattedTextField(uiUserRegis.yearFormatter); 
		formattedTextField_5_1_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formattedTextField_5_1_1.setBackground(new Color(128, 128, 128));uiUserRegis.ftfYear = formattedTextField_5_1_1;
		formattedTextField_5_1_1.setBounds(300, 196, 72, 20);
		RegisterPanel.add(formattedTextField_5_1_1);
		
		JFormattedTextField formattedTextField_5_1_2 = new JFormattedTextField(uiUserRegis.contactNoFormatter); 
		formattedTextField_5_1_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		formattedTextField_5_1_2.setBackground(new Color(128, 128, 128));uiUserRegis.ftfContactNo = formattedTextField_5_1_2;
		formattedTextField_5_1_2.setBounds(134, 240, 278, 20);
		RegisterPanel.add(formattedTextField_5_1_2);
		
		JPanel ActivityLogsPanel = new JPanel();
		ActivityLogsPanel.setBackground(new Color(192, 192, 192));
		rightContentPanel.add(ActivityLogsPanel, "name_72247065996200");
		
		uiReturn.windowRef = this;
		
		
		bookManagmentUI.initActionListener();
		uiRenting.initActionResponse();
		uiReturn.initActionResponse();
		
		
	}
}
