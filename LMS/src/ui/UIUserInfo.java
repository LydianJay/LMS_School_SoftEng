package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class UIUserInfo implements ActionListener {
	public static JButton btnEnter;
	public static JFormattedTextField ftfID;
	public static JLabel tfName, tfAddress, tfGender, tfRole, tfEmail, tfContactNo;
	public static Connection dtbConn;
	public static JScrollPane scrollPane;
	public static JPanel panel;
	public static JFrame windowRef;
	
	
	
	
	
	
	public void initActionResponse() {
		btnEnter.addActionListener(this);
	}
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton)(e.getSource());
		
		if(btn == btnEnter) {
			enterActionResponse();
		}
		
	}
	
	
	
	
	private static void enterActionResponse() {
		
		boolean success = true;
		try {
			String userID = ftfID.getText();
			
			Statement st = dtbConn.createStatement();
			String str = "SELECT * FROM userinfo WHERE userID = " + userID;
			ResultSet r = st.executeQuery(str);
			
			if(r.next()) {
				
				String name = r.getString("userName");
				String addr = r.getString("UserAddr");
				String email = r.getString("userEmail");
				String conNum = r.getString("userCNum");
				String gender = r.getInt("userGender") == 1 ? "Male" : "Female";
				String role = r.getInt("userRole") == 1 ? "Admin" : "Client";
				
				tfName.setText(name); tfGender.setText(gender); tfAddress.setText(addr); tfRole.setText(role); tfEmail.setText(email); tfContactNo.setText(conNum);
				
				
			}else {
				success = false;
			}
			
			
			
		}catch(SQLException e) {
			success = false;
			e.printStackTrace();
		}
		
		if(success) JOptionPane.showMessageDialog(windowRef, "User found!"); else JOptionPane.showMessageDialog(windowRef, "User was not found in database!");
		
	}
	
	
	
	
}
