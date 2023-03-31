package ui;

import java.awt.Component;
import java.awt.Dimension;
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
import main.ComponentResize;

public class UIUserInfo implements ActionListener, ComponentResize{
	public static JButton btnEnter;
	public static JFormattedTextField ftfID;
	public static JLabel tfName, tfAddress, tfGender, tfRole, tfEmail, tfContactNo;
	public static Connection dtbConn;
	public static JScrollPane scrollPane;
	public static JPanel panel,mainPanel;
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
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
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
				
				String stm = "SELECT (bookID) FROM ownershipinfo WHERE userID = " + userID;
				ResultSet t = st.executeQuery(stm);
				ArrayList<String> bookIDs = new ArrayList<String>();
				while(t.next()) {
					bookIDs.add( String.valueOf(t.getInt("bookID")) );
				}
				
				for (String bookID : bookIDs) {
					
					String query = "SELECT * FROM bookinfo WHERE bookID = " + bookID;
					ResultSet s = st.executeQuery(query);
					if(s.next()) {
						
						String parsed = "ID: " + s.getString("bookID") + "   NAME: " + s.getString("bookName") + "   CATEGORY: " + s.getString("bookCategory") + "   QTY: " + s.getString("bookQTY");
						JLabel j = new JLabel(parsed);
						panel.add(j);
					}
					
					
				}
				
				
				
			}else {
				success = false;
			}
			
			
			
		}catch(SQLException e) {
			success = false;
			e.printStackTrace();
		}
		
		if(success) JOptionPane.showMessageDialog(windowRef, "User found!"); else JOptionPane.showMessageDialog(windowRef, "User was not found in database!");
		panel.revalidate(); panel.repaint();
	}
	
	@Override
	public void resizeCall(Dimension old, Dimension n) {
		
		double rX = n.getWidth() / old.getWidth();
		double rY = n.getWidth() / old.getWidth();
		

		
		
		for(Component c : mainPanel.getComponents()) {
			
			Dimension newSize = new Dimension( (int)(c.getWidth() * rX), (int)(c.getHeight() * rY));
			
			c.setSize(newSize);
			int x = (int) (c.getX() * rX), y = (int) (c.getY() * rY);
			c.setLocation(x, y);
			c.revalidate();
			c.repaint();
			
		}
		
		
	}
	
	
}
