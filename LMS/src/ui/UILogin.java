package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import main.ComponentResize;
import main.Main;

public class UILogin implements ActionListener, ComponentResize{
	public static JLabel banner;
	public static JFrame windowRef;
	public static JPanel mainPanel, panel;
	public static JPasswordField passField;
	public static JFormattedTextField ftfUserID;
	public static JButton btnLogin;
	public static Connection dtbConn;
	public static ArrayList<Component> compList = new ArrayList<Component>();
	
	public void initActionListener() {
		btnLogin.addActionListener(this);
		compList.add(passField); compList.add(btnLogin);
		compList.add(ftfUserID);
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int userId = Integer.valueOf(ftfUserID.getText());
		String userPass = new String(passField.getPassword());
	
		try {
			Statement st = dtbConn.createStatement();
			String str = "SELECT * FROM `userinfo` WHERE userID = " + String.valueOf(userId);
			ResultSet r = st.executeQuery(str);
			if(r.next()) {
				String userName = r.getString("userName");
				String userCorrectPass = r.getString("userPass");
				int role = r.getInt("userRole");
				
				if(role == 0) {
					JOptionPane.showMessageDialog(windowRef, "User is not an administrator");
					passField.setText("");
					return;
				}
				
				if(userPass.compareTo(userCorrectPass) == 0) {
					CardLayout c = (CardLayout)(mainPanel.getLayout());
					c.next(panel);
				}
				else {
					JOptionPane.showMessageDialog(windowRef, "UserID or password Incorrect!");
					passField.setText("");
				}
				
				
				
			}
			else {
				
				JOptionPane.showMessageDialog(windowRef, "UserID or password Incorrect!");
				passField.setText("");
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}




	@Override
	public void resizeCall(Dimension old, Dimension n) {
		double rX = n.getWidth() / old.getWidth();
		double rY = n.getHeight() / old.getHeight();
		
		
		
		for(Component c : compList) {
			
			Dimension newSize = new Dimension( (int)(c.getWidth() * rX), (int)(c.getHeight() * rY));
			
			c.setSize(newSize);
			int x = (int) (c.getX() * rX), y = (int) (c.getY() * rY);
			c.setLocation(x, y);
			
			if(c == banner) {
				banner.setIcon( new ImageIcon (new ImageIcon(Main.class.getResource("/res/LMS_banner.png")).getImage().getScaledInstance(banner.getWidth(), banner.getHeight(), Image.SCALE_SMOOTH) ) );
			}
			
			c.revalidate();
			c.repaint();
			
		}
		
		
		
		
		
	}
	
	
}
