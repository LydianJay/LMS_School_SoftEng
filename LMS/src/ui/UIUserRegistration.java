package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import main.ComponentResize;


public class UIUserRegistration implements ActionListener, ComponentResize{
	
	public static JPanel mainPanel;
	public static JButton btnRegister;
	public static JFormattedTextField ftfDate, ftfMonth, ftfYear, ftfContactNo;
	public static JTextField tfName, tfAddress, tfEmail, tfPass;
	public static Connection dtbConn;
	public static JRadioButton rbMale, rbFemale, rbClient, rbAdmin;
	public static JFrame windowRef;
	public static MaskFormatter dateFormatter, monthFormatter, yearFormatter, contactNoFormatter;
	public static ButtonGroup bgGender = new ButtonGroup(), bgRole = new ButtonGroup();
	private static int userCount = -1;
	
	
	@Override
	public void resizeCall(Dimension old, Dimension n) {
		
		double rX = n.getWidth() / old.getWidth();
		double rY = n.getHeight() / old.getHeight();
		

		
		
		for(Component c : mainPanel.getComponents()) {
			
			Dimension newSize = new Dimension( (int)(c.getWidth() * rX), (int)(c.getHeight() * rY));
			
			c.setSize(newSize);
			int x = (int) (c.getX() * rX), y = (int) (c.getY() * rY);
			c.setLocation(x, y);
			c.revalidate();
			c.repaint();
			
		}
		
		
	}
	
	
	public UIUserRegistration(){
			
		
		try {
			this.dateFormatter = new MaskFormatter("##");
			this.dateFormatter.setPlaceholderCharacter('0');
			contactNoFormatter = new MaskFormatter("###########");
			contactNoFormatter.setPlaceholder("09123456789");
			monthFormatter = new MaskFormatter("##");
			monthFormatter.setPlaceholderCharacter('0');
			yearFormatter = new MaskFormatter("####");
			yearFormatter.setPlaceholder("2069");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void getUserCount() {
		
		try {
			Statement st = dtbConn.createStatement();
			
			String sqlstm = "SELECT COUNT(userName) AS c FROM userinfo";
			ResultSet t = st.executeQuery(sqlstm);
			if(t.next())
				userCount = t.getInt("c");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	public void initButtonGroup() {
		
		bgGender = new ButtonGroup();
		bgGender.add(rbMale);
		bgGender.add(rbFemale);
		bgRole = new ButtonGroup();
		bgRole.add(rbAdmin);
		bgRole.add(rbClient);
		
	}
	
	
	private static int getNumOfDigits(long n) {
		
		int count = 0;
		while(n > 1) {
			n = n / 10;
			count++;
		}
		return count;
	}
	
	private static String generateUserID(String year) throws SQLException {
		
		
		Statement st = dtbConn.createStatement();
		
		ResultSet s = st.executeQuery("SELECT COUNT(id) FROM deleted_ids AS c");
		
		if(s.next())  { 
			s = st.executeQuery("SELECT (id) FROM deleted_ids WHERE idType = 1");
			
			if(s.next()) {
				
				String id = String.valueOf(s.getLong("id"));
				st.executeUpdate("DELETE FROM deleted_ids WHERE id = " + id + " AND idType = 1;");
				return id;
			}
			
		}
		
		int numOfzerosToFill = 6 - getNumOfDigits(userCount + 1);
		
		String userID = "";
		for(int i = 0; i < numOfzerosToFill; i++) {
			userID += "0";
		}
		
		userID += String.valueOf(userCount + 1);
		
		
		
		return year + userID;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton)(e.getSource());
		
		if(btn == btnRegister) {
			registerActionResponse();
		}
		
	}
	
	
	
	
	private static void registerActionResponse() {
		String userName, address, email,bday,conNum, password, gender, role;
		
		
		
		
		userName = "'" + tfName.getText() + "'";
		address = "'" + tfAddress.getText() + "'";
		email = "'" + tfEmail.getText() + "'";
		bday = ftfDate.getText() + ftfMonth.getText() + ftfYear.getText();
		conNum = "'" + ftfContactNo.getText() + "'";
		password = "'" + tfPass.getText() + "'";
		gender = rbMale.isSelected() ? "1" : rbFemale.isSelected() ? "0" : "1";
		role = rbAdmin.isSelected() ? "1" : rbClient.isSelected() ? "0" : "1";
		
		
		boolean checkEmptyFields = !(tfName.getText().isEmpty() || tfAddress.getText().isEmpty() || tfEmail.getText().isEmpty() || tfPass.getText().isEmpty());
		boolean checkRadioButton = !( (!rbMale.isSelected() && !rbFemale.isSelected()) || (!rbAdmin.isSelected() && !rbClient.isSelected()) );
		
		int date  = Integer.valueOf(ftfDate.getText());
		int month = Integer.valueOf(ftfMonth.getText());
		int year = Integer.valueOf(ftfYear.getText());
		
		
		boolean checkBirthDate = (date > 0 && date <= 31) && (month > 0 && month <= 12) && (year > 1000);
		
		
		boolean isValid = checkEmptyFields && checkRadioButton && checkBirthDate;
		
		if(isValid) {
			
			if(userCount < 0) getUserCount();
			String userID = null;
			
			boolean isSuccess = true;
			try {
				Statement st = dtbConn.createStatement();
				
				
				
				userID = generateUserID(ftfYear.getText());
				
				String str = "INSERT INTO userinfo VALUES (" + userName + "," + address + "," + email + "," + bday + "," + conNum + "," + gender + "," + role + "," + userID + "," + password + ");";
				st.executeUpdate(str);
				
				
			} catch (SQLException e1) {
				isSuccess = false;
				e1.printStackTrace();
				JOptionPane.showMessageDialog(windowRef, "SQL Fail");
				
				
			}
			if(isSuccess) {
				JOptionPane.showMessageDialog(windowRef, "Registration Success!");
				JOptionPane.showMessageDialog(windowRef, "UserID: " + userID);
				tfName.setText(""); tfAddress.setText(""); tfEmail.setText(""); ftfDate.setText(""); ftfMonth.setText("");
				ftfYear.setText(""); ftfContactNo.setText(""); tfPass.setText("");
				bgGender.clearSelection(); bgRole.clearSelection();
				
			}
			
		}
		else {
			if(!checkEmptyFields) {
				JOptionPane.showMessageDialog(windowRef, "Empty Fields Detected!!!");
			}
			
			if(!checkBirthDate) {
				JOptionPane.showMessageDialog(windowRef, "Invalid Date!");
			}
			
			if(!checkRadioButton) {
				JOptionPane.showMessageDialog(windowRef, "No selection for role or gender!");
			}
			
		}
		
		
		
	}
	
	
	
}
