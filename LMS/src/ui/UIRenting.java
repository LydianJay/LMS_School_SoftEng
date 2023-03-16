package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
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
		
		
		boolean success = true;
		try {
			Statement st = dtbConn.createStatement();
			String userID = ftfUserID.getText();
			for (JCheckBox j : rentList) {
				
				String bookID = j.getText().substring(0, j.getText().indexOf("NAME: ")).replaceAll("[^0-9]", "");
				
				String stm = "SELECT COUNT(*) AS ft FROM ownershipinfo WHERE bookID = " + bookID + " AND userID = " + userID;
				ResultSet r = st.executeQuery(stm);
				int count = -1;
				if(r.next()) {
					count = r.getInt("ft");
				}
					
				
				if(count == 0) {
					String s = "INSERT INTO ownershipinfo (userID, bookID) VALUES (" + userID + "," + bookID + ")";
					st.executeUpdate(s);
				}
				
			}
			
			
			
		}
		catch(Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		
		
		if(success) JOptionPane.showMessageDialog(windowRef, "Rent Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Rent was not successful!");
		
	}
	
	
	
	
	private static void removeActionResponse() {
		
		
		Iterator<JCheckBox> ir = rentList.iterator();
		
		
		
		do{
			JCheckBox j = ir.next();
			
			if(j.isSelected()) {
				panel2.remove(j);
				ir.remove();
			}
			
		}while(ir.hasNext());
		
		
		
		panel1.revalidate();
		panel1.repaint();
		panel2.revalidate();
		panel2.repaint();
		
	}
	
	private static void addActionResponse() {
		
		
		Iterator<JCheckBox> ir = searchQuery.iterator();
		
		do {
			JCheckBox j = ir.next();
			if(j.isSelected() && !alreadyExist(j.getText(), rentList)) {
				j.setSelected(false);
				panel1.remove(j);
				rentList.add(j);
				panel2.add(j);
				ir.remove();
			}
			
			
		}while(ir.hasNext());
		
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
		searchQuery.clear();
		try {
			
			String name = "'" + tfBookName.getText() + "%'", author =  "'" + tfAuthor.getText() + "%'" , category =  "'" + tfCategory.getText() + "%'";
			
			String stmName = !name.isBlank() ? "bookName LIKE " + name : "";
			String stmAuthor = !author.isBlank() ? "bookAuthor LIKE " + author : "";
			String stmCat = !author.isBlank() ? "bookCategory LIKE " + category : "";
			
			
			Statement st = dtbConn.createStatement();
			String str = "SELECT * FROM bookinfo WHERE " + stmName + " OR " + stmAuthor + " OR " + stmCat + ";";
			ResultSet r = st.executeQuery(str);
			
			while(r.next()) {
				
				String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
				JCheckBox j = new JCheckBox(parsed);
				
				
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
