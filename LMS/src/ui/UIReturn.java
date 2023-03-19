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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.text.MaskFormatter;

public class UIReturn implements ActionListener{
	public static JButton btnEnter, btnReturn;
	public static JFormattedTextField ftfID;
	public static Connection dtbConn;
	public static JScrollPane scrollPane;
	public static JPanel panel;
	public static MaskFormatter idFormatter;
	public static JFrame windowRef;
	private static ArrayList<JCheckBox> checkBox = new ArrayList<JCheckBox>();
	
	
	public UIReturn() {
		
		try {
			idFormatter = new MaskFormatter("##########");
			idFormatter.setPlaceholderCharacter('0');
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)(e.getSource());
		
		
		if(btn == btnEnter) {
			enterActionResponse();
		}
		
		else if(btn == btnReturn) {
			returnActionResponse();
		}
		
		
		
	}
	
	private static void enterActionResponse() {
		
		
		
		String userID = ftfID.getText();
		
		
		checkBox.clear();
		try {
			
			Statement st = dtbConn.createStatement();
			String str = "SELECT * FROM ownershipinfo WHERE userID = " + userID;
			ResultSet r = st.executeQuery(str);
			
			ArrayList<String> bookIDs = new ArrayList<String>();
			while(r.next()) {
				bookIDs.add(r.getString("bookID"));
			}
			
			
			for(String bookID : bookIDs) {
				
				str = "SELECT * FROM bookinfo WHERE bookID = " + bookID;
				r = st.executeQuery(str);
				while(r.next()) {
					
					String bookName = r.getString("bookID"), bookCategory = r.getString("bookCategory");
					String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
					JCheckBox j = new JCheckBox(parsed);
					
					checkBox.add(j);
					panel.add(j);	
					
				}
				
				
			}
			if(bookIDs.isEmpty())
				JOptionPane.showMessageDialog(windowRef, "No book rented!");
			
			panel.revalidate();
			panel.repaint();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private static void returnActionResponse() {
		
		if(JOptionPane.showConfirmDialog(windowRef,"Are you sure?", "Return Book",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) return;
		
		boolean success = true;
		try {
			String userID = ftfID.getText();
			Statement st = dtbConn.createStatement();
			
			for (JCheckBox j : checkBox) {
				
				if(j.isSelected()) {
					String bookID = j.getText().substring(0, j.getText().indexOf("NAME: ")).replaceAll("[^0-9]", "");
					String stm = "DELETE FROM ownershipinfo WHERE bookID = " + bookID + " AND userID = " + userID;
					
					st.executeUpdate(stm);
					
					
				}
				
			}
			
			
		}
		catch(SQLException s) {
			s.printStackTrace();
			success = false;
		}
		if(success) JOptionPane.showMessageDialog(windowRef, "Returned Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Return was not successful!");
		
		ftfID.setText("");
		
		checkBox.clear();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
	
	
	public void initActionResponse() {
		btnEnter.addActionListener(this); btnReturn.addActionListener(this);
	}
	
	
	
	
}
