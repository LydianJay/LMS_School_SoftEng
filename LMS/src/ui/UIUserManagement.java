package ui;

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
import java.awt.Component;

import main.ComponentResize;

public class UIUserManagement implements ActionListener, ComponentResize{
	
	public static JTextField tfName;
	public static JButton btnSearch, btnUnreg, btnShowAll;
	public static JFormattedTextField ftfID;
	public static Connection dtbConn;
	public static JScrollPane scrollPane;
	public static JPanel panel, mainPanel;
	public static JFrame windowRef;
	private static ArrayList<JCheckBox> checkBox = new ArrayList<JCheckBox>();
	private static ArrayList<Component> compList = new ArrayList<Component>();
	
	
	
	
	public void initActionListener() {
		btnSearch.addActionListener(this);
		btnUnreg.addActionListener(this);
		btnShowAll.addActionListener(this);
		
		compList.add(btnSearch); compList.add(panel);
		compList.add(scrollPane); compList.add(ftfID);
		compList.add(btnUnreg); compList.add(btnShowAll);
		compList.add(tfName); compList.add(panel);
		
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton) e.getSource();
		
		if(btn == btnSearch) {
			searchActionResponse();
		}
		else if(btn == btnUnreg) {
			unregisterActionResponse();
		}
		else if(btn == btnShowAll) {
			showAllActionResponse();
		}
		
	}
	
	
	private static void showAllActionResponse() {
		
		
		Statement st;
		try {
			checkBox.clear();
			panel.removeAll();
			panel.revalidate();
			
			st = dtbConn.createStatement();
			String stm = "SELECT * FROM `userinfo`";
			ResultSet r = st.executeQuery(stm);
			
			while(r.next()) {
				
				String parsed = "UserID:  " + r.getString("userID") + " Name:  " + r.getString("userName");
				JCheckBox j = new JCheckBox(parsed);
				checkBox.add(j);
				panel.add(j);
			}
			
			panel.revalidate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void unregisterActionResponse() {
		
		
		
		if(JOptionPane.showConfirmDialog(windowRef,"Are you sure?", "DELETE USER",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) return;
		
		
		boolean success = true;
		int selectedCount = 0;
		for(JCheckBox j : checkBox) {
			
			if(j.isSelected()) {
				selectedCount++;
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
		
		if(selectedCount <= 0) {
			JOptionPane.showMessageDialog(windowRef, "No selected User!");
		}
		else
			if(success) JOptionPane.showMessageDialog(windowRef, "Deleted Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Delete Failed!");
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		checkBox.clear();
		
		
		
		
		
	}
	
	private static void searchActionResponse() {
		
		
		String userName = "'" + tfName.getText() + "%'";
		String userID = ftfID.getText();
		
		
		panel.removeAll();
		panel.revalidate();
		
		Statement st;
		try {
			st = dtbConn.createStatement();
			
			if(userID.isEmpty() == false && userID.compareTo("0000000000") != 0) {
				checkBox.clear();

				String stm = "SELECT * FROM `userinfo` WHERE userID = " + userID;
				ResultSet r = st.executeQuery(stm);
				
				while(r.next()) {
					
					String parsed = "UserID:  " + r.getString("userID") + " Name:  " + r.getString("userName");
					JCheckBox j = new JCheckBox(parsed);
					
					checkBox.add(j);
					panel.add(j);
				}
				
				
			}
			else if(!tfName.getText().isEmpty()) {
				
				checkBox.clear();
				String stm = "SELECT * FROM `userinfo` WHERE userName LIKE " + userName;
				ResultSet r = st.executeQuery(stm);
				
				while(r.next()) {
					
					String parsed = "UserID:  " + r.getString("userID") + " Name:  " + r.getString("userName");
					JCheckBox j = new JCheckBox(parsed);
					checkBox.add(j);
					panel.add(j);
				}
				
				
				
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel.revalidate();
		panel.repaint();
		
		
		
	}


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
	
	
	
}
