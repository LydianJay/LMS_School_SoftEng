package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class UIRenting implements ActionListener {
	public static JFrame windowRef;
	public static JPanel panel1, panel2;
	public static Connection dtbConn;
	public static JTextField tfBookName, tfAuthor, tfCategory;
	public static JFormattedTextField ftfUserID;
	public static JButton btnSearch, btnAdd, btnRemove, btnConfirm; 
	public static JScrollPane scrlPane1, scrlPane2;
	private static ArrayList<JCheckBox> searchQuery = new ArrayList<JCheckBox>();
	private static ArrayList<JCheckBox> rentList = new ArrayList<JCheckBox>();
	public static MaskFormatter idFormatter;
	
	public void initActionResponse() {
		btnSearch.addActionListener(this);
		btnAdd.addActionListener(this);
		btnRemove.addActionListener(this);
		btnConfirm.addActionListener(this);
	}
	
	public UIRenting(){
		
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
		
		
		if(btn == btnSearch) {
			searchActionResponse();
		}
		
		else if(btn == btnAdd) {
			addActionResponse();
		}
		
		else if(btn == btnRemove) {
			removeActionResponse();
		}
		
		else if(btn == btnConfirm) {
			confirmActionResponse();
		}
		
		
	}
	
	
	private static void confirmActionResponse() {
		
		
		if(rentList.isEmpty()) {JOptionPane.showMessageDialog(windowRef, "Empty Rent List!!!"); return;}
		
		boolean success = false;
		try {
			Statement st = dtbConn.createStatement();
			String userID = ftfUserID.getText();
			for (JCheckBox j : rentList) {
				
				String bookID = j.getText().substring(0, j.getText().indexOf("NAME: ")).replaceAll("[^0-9]", "");
				
				String stm = "SELECT COUNT(*) AS ft FROM ownershipinfo WHERE bookID = " + bookID + " AND userID = " + userID;
				String stm2 = "UPDATE bookinfo SET bookQTY = bookQTY - 1 WHERE bookID = " + bookID;
				ResultSet r = st.executeQuery(stm);
			
				int count = -1;
				if(r.next()) {
					count = r.getInt("ft");
				}
				
				String stm3 = "SELECT COUNT(userID) as ft FROM userinfo WHERE userID = " + userID;
				ResultSet t = st.executeQuery(stm3); t.next();
				boolean doesUserExist = t.getInt("ft") > 0;
				
				if(doesUserExist) {
					
					if(count == 0) {
						success = true;
						st.executeUpdate(stm2);
						String s = "INSERT INTO ownershipinfo (userID, bookID) VALUES (" + userID + "," + bookID + ")";
						st.executeUpdate(s);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(windowRef, "Invalid userID!");
					success = false;
					break;
				}
				
				
			}
			
			
			
		}
		catch(Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		panel1.removeAll(); panel2.removeAll();
		panel1.revalidate(); panel2.revalidate();
		panel1.repaint(); panel2.repaint();
		rentList.clear();
		searchQuery.clear();
		
		if(success) JOptionPane.showMessageDialog(windowRef, "Rent Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Rent was not successful!");
		
	}
	
	
	
	
	private static void removeActionResponse() {
		
		
		Iterator<JCheckBox> ir = rentList.iterator();
		
		
		
		while(ir.hasNext()){
			JCheckBox j = ir.next();
			if(j.isSelected()) {
				panel2.remove(j);
				ir.remove();
			}
		}
		
		
		
		panel1.revalidate();
		panel1.repaint();
		panel2.revalidate();
		panel2.repaint();
		
	}
	
	private static void addActionResponse() {
		
		
		Iterator<JCheckBox> ir = searchQuery.iterator();
		try {
			Statement st = dtbConn.createStatement();
			while(ir.hasNext()) {
				
				JCheckBox j = ir.next();
				
				if(j.isSelected() && !alreadyExist(j.getText(), rentList)) {
					j.setSelected(false);
					
					
					
					panel1.remove(j);
					rentList.add(j);
					panel2.add(j);
					ir.remove();
				}
				
				
			}
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		panel1.revalidate();
		panel1.repaint();
		panel2.revalidate();
		panel2.repaint();
		
	}
	
	
	private static boolean alreadyExist(String parsed, ArrayList<JCheckBox> list) {
		for(JCheckBox j : list) {
			if(j.getText().compareTo(parsed) == 0)return true;
		}
		
		return false;
	}
	
	private static void searchActionResponse() {
		
		panel1.removeAll();
		panel1.revalidate();
		panel1.repaint();
		searchQuery.clear();
		
		if(tfBookName.getText().isBlank() && tfAuthor.getText().isBlank() && tfCategory.getText().isBlank()) return;
		
		
		try {
			
			String name = tfBookName.getText(), author = tfAuthor.getText()  , category =  tfCategory.getText();
			
			
			
			String stmName = !name.isBlank() ? "WHERE bookQTY > 0 AND bookName LIKE " + "'"+name+"%' OR " : " WHERE bookQTY > 0 AND ";
			String stmAuthor = !author.isBlank() ?  "bookAuthor LIKE " + "'"+author+"%' OR " : " bookID = -1 OR ";
			String stmCat = !category.isBlank() ?  "bookCategory LIKE " + "'"+category+"%'" : " bookID = -1";
			
			Statement st = dtbConn.createStatement();
			
			String str = "SELECT * FROM bookinfo " + stmName + stmAuthor + stmCat + ";";
			
			ResultSet r = st.executeQuery(str);
			Font f = new Font("Tahoma Bold", Font.BOLD, 12) ;
			while(r.next()) {
				
				String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
				JCheckBox j = new JCheckBox(parsed);
				j.setBackground(new Color(128,128,128));
				j.setFont(f);
				
				
				if( !alreadyExist(parsed, rentList)) {
					panel1.add(j);
					searchQuery.add(j);
				}
				
			}
			
			
			panel1.revalidate();
			panel1.repaint();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
