package ui;

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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class UIUserRegistration implements ActionListener {
	
	
	public static JButton btnRegister;
	public static JFormattedTextField ftfDate, ftfMonth, ftfYear, ftfContactNo;
	public static JTextField tfName, tfAddress, tfEmail, tfPass;
	public static Connection dtbConn;
	public static JRadioButton rbMale, rbFemale, rbClient, rbAdmin;
	public static JFrame windowRef;
	public static MaskFormatter dateFormatter, monthFormatter, yearFormatter, contactNoFormatter;
	public static ButtonGroup bgGender = new ButtonGroup(), bgRole = new ButtonGroup();
	private static int userCount = -1;
	
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
				st.executeUpdate("DELETE FROM deleted_ids WHERE id == " + id);
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
		email = "'" +tfEmail.getText() + "'";
		bday = ftfDate.getText() + ftfMonth.getText() + ftfYear.getText();
		conNum = "'" + ftfContactNo.getText() + "'";
		password = "'" + tfPass.getText() + "'";
		gender = rbMale.isSelected() ? "1" : rbFemale.isSelected() ? "0" : "1";
		role = rbAdmin.isSelected() ? "1" : rbClient.isSelected() ? "0" : "1";
		
		
		boolean isValid = !(userName.isEmpty() && address.isEmpty() && email.isEmpty() && bday.isEmpty() && conNum.isEmpty() && password.isEmpty() && gender.isEmpty() && role.isEmpty());
		
		
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
				JOptionPane.showMessageDialog(windowRef, "Failed");
				
				
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
			JOptionPane.showMessageDialog(windowRef, "Empty Fields Detected!!!");
		}
		
		
		
	}
	
	
	
}
