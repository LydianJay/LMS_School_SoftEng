package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import main.ComponentResize;





/*
 * UIRenting - this is the class that handles the Renting procedure
 * 
 * 
 * 
 */



public class UIRenting implements ActionListener, ComponentResize{
	public static JFrame windowRef;
	public static JPanel panel1, panel2, mainPanel;
	public static Connection dtbConn;
	public static JTextField tfBookName, tfAuthor, tfCategory;
	public static JFormattedTextField ftfUserID;
	public static JButton btnSearch, btnAdd, btnRemove, btnConfirm; 
	public static JScrollPane scrlPane1, scrlPane2;
	private static ArrayList<JCheckBox> searchQuery = new ArrayList<JCheckBox>(); // the checklist of the retrived Books when the user clicks search
	private static ArrayList<JCheckBox> rentList = new ArrayList<JCheckBox>(); // the checklist for the books that was added to the rent list
	public static MaskFormatter idFormatter;
	
	@Override
	public void resizeCall(Dimension old, Dimension n) { // for resizing the UI components
		
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
	public void actionPerformed(ActionEvent e) { // button handler
		
		
		JButton btn = (JButton)(e.getSource()); // get button
		
		
		if(btn == btnSearch) { // if search button is pressed
			searchActionResponse();
		}
		
		else if(btn == btnAdd) { // if add button is pressed
			addActionResponse();
		}
		
		else if(btn == btnRemove) { // if remove button is pressed
			removeActionResponse();
		}
		
		else if(btn == btnConfirm) { // if confirm button is pressed
			confirmActionResponse();
		}
		
		
	}
	
	/*
	 * This method is called when the 'confirm' button is pressed
	 * 
	 */
	private static void confirmActionResponse() {
		
		// Shows the message "Empty Rent List" when there is no selected books is the rent list and the confirm button is pressed
		if(rentList.isEmpty()) {JOptionPane.showMessageDialog(windowRef, "Empty Rent List!!!"); return;} 
		
		
		
		
		boolean success = false;
		try {
			Statement st = dtbConn.createStatement();
			String userID = ftfUserID.getText();
			
			for (JCheckBox j : rentList) { // we check each checklist
				
				if(!j.isSelected()) { // if the list is not selected we ignore
					continue;
				}
				
				
				String bookID = j.getText().substring(0, j.getText().indexOf("NAME: ")).replaceAll("[^0-9]", ""); // parses the string
				
				String stm = "SELECT COUNT(*) AS ft FROM ownershipinfo WHERE bookID = " + bookID + " AND userID = " + userID;
				String stm2 = "UPDATE bookinfo SET bookQTY = bookQTY - 1 WHERE bookID = " + bookID;
				ResultSet r = st.executeQuery(stm); // first we query if the book was already borrowed by the user
			
				int count = -1;
				if(r.next()) {
					count = r.getInt("ft");
				}
				
				String stm3 = "SELECT COUNT(userID) as ft FROM userinfo WHERE userID = " + userID;
				ResultSet t = st.executeQuery(stm3); t.next(); // we query if the userID/user exist in the database
				boolean doesUserExist = t.getInt("ft") > 0;
				
				if(doesUserExist) {
					
					if(count == 0) { // if the user exist and the book is not yet owned by the user sets ownership to that book and updates necessary info
						success = true;
						st.executeUpdate(stm2);
						String s = "INSERT INTO ownershipinfo (userID, bookID) VALUES (" + userID + "," + bookID + ")";
						st.executeUpdate(s);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(windowRef, "Invalid userID!"); // shows error message of "Invalid userID" when the user does not exist in the database
					success = false;
					break;
				}
				
				
			}
			
			
			
		}
		catch(Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		panel1.removeAll(); panel2.removeAll(); // refresh the panel
		panel1.revalidate(); panel2.revalidate();
		panel1.repaint(); panel2.repaint();
		rentList.clear();
		searchQuery.clear();
		
		// display message "Rent Successful" or "Rent was not successful"
		if(success) JOptionPane.showMessageDialog(windowRef, "Rent Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Rent was not successful!");
		
	}
	
	
	
	/*
	 * Removes the books from the rent list
	 * 
	 */
	private static void removeActionResponse() {
		
		
		Iterator<JCheckBox> ir = rentList.iterator();
		
		
		
		while(ir.hasNext()){ // iterates through the query list
			JCheckBox j = ir.next();
			if(j.isSelected()) { // if the book is selected we remove that book in the rent list
				panel2.remove(j);
				ir.remove();
			}
		}
		
		// refresh the panel
		panel1.revalidate();
		panel1.repaint();
		panel2.revalidate();
		panel2.repaint();
		
	}
	/*
	 * 
	 * Adds the book to the rent list
	 */
	private static void addActionResponse() {
		
		
		Iterator<JCheckBox> ir = searchQuery.iterator();
		
		while(ir.hasNext()) { // iterates through the query list
			
			JCheckBox j = ir.next();
			
			if(j.isSelected() && !alreadyExist(j.getText(), rentList)) { // if the book in the query list is selected and does already exist in the rent list then we add it to the rent list
				j.setSelected(false); 
				
				
				
				panel1.remove(j);
				rentList.add(j);
				panel2.add(j);
				ir.remove();
			}
			
			
		}
		
		
		
		panel1.revalidate();
		panel1.repaint();
		panel2.revalidate();
		panel2.repaint();
		
	}
	
	// check if the book already in the list
	/*
	 * @param - the string that contains the book info
	 * @list - the list(rent list, or query list)
	 * @return - returns true if it already exist, false otherwise
	 */
	private static boolean alreadyExist(String parsed, ArrayList<JCheckBox> list) {
		for(JCheckBox j : list) {
			if(j.getText().compareTo(parsed) == 0)return true;
		}
		
		return false;
	}
	
	
	
	
	/*
	 * This method is called when the "search" button is pressed 
	 * it queries the database for books base on the parameter like name or category
	 * 
	 */
	private static void searchActionResponse() {
		
		panel1.removeAll();
		panel1.revalidate();
		panel1.repaint();
		searchQuery.clear(); // clear first the search list
		
		if(tfBookName.getText().isEmpty() && tfAuthor.getText().isEmpty() && tfCategory.getText().isEmpty()) return; // we need to validate first if no input was taken
		
		
		try {
			
			String name = tfBookName.getText(), author = tfAuthor.getText()  , category =  tfCategory.getText();
			
			
			
			String stmName = !name.isEmpty() ? "WHERE bookQTY > 0 AND bookName LIKE " + "'"+name+"%' OR " : " WHERE bookQTY > 0 AND ";
			String stmAuthor = !author.isEmpty() ?  "bookAuthor LIKE " + "'"+author+"%' OR " : " bookID = -1 OR ";
			String stmCat = !category.isEmpty() ?  "bookCategory LIKE " + "'"+category+"%'" : " bookID = -1";
			
			Statement st = dtbConn.createStatement();
			
			String str = "SELECT * FROM bookinfo " + stmName + stmAuthor + stmCat + ";";
			
			ResultSet r = st.executeQuery(str); // query for the book
			Font f = new Font("Tahoma Bold", Font.BOLD, 12) ;
			while(r.next()) { // iterates the queried data and parses them then added to the search list
				
				String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
				JCheckBox j = new JCheckBox(parsed);
				j.setBackground(new Color(128,128,128));
				j.setFont(f);
				
				
				if( !alreadyExist(parsed, rentList)) {
					panel1.add(j);
					searchQuery.add(j);
				}
				
			}
			
			
			panel1.revalidate(); // refresh panel
			panel1.repaint();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
