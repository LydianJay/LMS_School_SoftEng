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

// container for UI elements

public class UIBookManagement implements ActionListener, ComponentResize{
	
	public static JTextField tfName, tfAuthor, tfCategory;
	public static JButton btnSearch, btnCreate, btnDelete, btnAdd, btnSubtract, btnShowAll;
	public static JFormattedTextField ftfID, ftfYear;
	public static Connection dtbConn;
	public static JScrollPane scrollPane;
	public static JPanel panel, mainPanel;
	public static MaskFormatter idFormatter, yearFormatter;
	public static JFrame windowRef;
	private static ArrayList<JCheckBox> checkBox = new ArrayList<JCheckBox>();
	private static int numOfBookEntries = -1;
	
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
	
	
	public UIBookManagement(){
		

		try {
			idFormatter = new MaskFormatter("######");
			yearFormatter = new MaskFormatter("####");
			idFormatter.setPlaceholderCharacter('0');
			yearFormatter.setPlaceholderCharacter('0');
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	public void initActionListener() {
		btnSearch.addActionListener(this);
		btnCreate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnAdd.addActionListener(this);
		btnSubtract.addActionListener(this);
		btnShowAll.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)(e.getSource());
		
		if(btn == btnCreate) {
			createActionResponse();
		}
		
		else if(btn == btnSearch) {
			searchActionResponse();
		}
		
		
		else if(btn == btnDelete) {
			deleteActionResponse();
		}
		
		else if(btn == btnAdd) {
			qtyActionResponse(true);
		}
		else if(btn == btnSubtract) {
			qtyActionResponse(false);
		}
	
		else if(btn == btnShowAll) {
			showAllActionResponse();
		}
		
	}
	
	
	
	private static void showAllActionResponse() {
		checkBox.clear();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		
		try {
			Statement st = dtbConn.createStatement();
			
			ResultSet r = st.executeQuery("SELECT * FROM bookinfo");
			while(r.next()) {
				
				String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
				JCheckBox j = new JCheckBox(parsed);
				j.setBackground(new Color(128,128,128));
				checkBox.add(j);
				panel.add(j);	
				
			}
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		panel.revalidate();
		panel.repaint();
		
		
	}
	
	
	private static void qtyActionResponse(boolean toAdd) {
		
		
		boolean success = true;
		int selectedCount = 0;
		try {
			Statement st = dtbConn.createStatement();
			
			
			
			for(int i = 0; i < checkBox.size(); i++) {
				JCheckBox j = checkBox.get(i);
				
				if(j.isSelected()) {
					
					selectedCount++;
					String bookID = j.getText().substring(0, j.getText().indexOf("NAME: ")).replaceAll("[^0-9]", "");
					String stm;
					if(toAdd) {
						stm = "UPDATE bookinfo SET bookQTY = bookQTY + 1 WHERE bookID = " + bookID + ";";
					}
					else {
						stm = "UPDATE bookinfo SET bookQTY = bookQTY - 1 WHERE bookID = " + bookID + " AND bookQTY > 0;";
					}
					
					st.executeUpdate(stm);
					
				}
				
				
			}
			
			
		} catch (SQLException e) {
			
			success = false;
			e.printStackTrace();
		}
		if(selectedCount <= 0) {
			JOptionPane.showMessageDialog(windowRef, "No selected Books!");
		}
		else
			if(success) JOptionPane.showMessageDialog(windowRef, "Update Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Update was not successful!");
		
		checkBox.clear();
		panel.removeAll();
		panel.revalidate();
		
	}
	
	private static void deleteActionResponse() {
		
		
		if(JOptionPane.showConfirmDialog(windowRef,"Are you sure?", "DELETE BOOK",
	               JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) return;
		
		boolean success = true;
		
		try {
			Statement st = dtbConn.createStatement();
			
			
			
			for(int i = 0; i < checkBox.size(); i++) {
				JCheckBox j = checkBox.get(i);
				
				if(j.isSelected()) {
					
					
					String bookID = j.getText().substring(0, j.getText().indexOf("NAME: ")).replaceAll("[^0-9]", "");
					
					String stm = "DELETE FROM bookinfo WHERE bookID = " + bookID;
					st.executeUpdate(stm);
					st.executeUpdate("INSERT INTO deleted_ids VALUES(0, " + bookID + ")");
					numOfBookEntries--;
				}
				
				
			}
			
			
		} catch (SQLException e) {
			
			success = false;
			e.printStackTrace();
		}
		
		if(success) JOptionPane.showMessageDialog(windowRef, "Deleted Successfuly!"); else JOptionPane.showMessageDialog(windowRef, "Deletion was not successful!");
		
		checkBox.clear();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
	
	
	private static void searchActionResponse() {
		
		
		String bookName = "'" + tfName.getText() + "%'", bookAuthor = "'" + tfAuthor.getText() + "%'", bookCat = "'" + tfCategory.getText() + "%'", bookID = ftfID.getText(), bookYear = ftfYear.getText();
		//boolean createSuccess = true;
		//boolean isValid = !(bookName.isBlank() && bookAuthor.isBlank() && bookCat.isBlank() && bookYear.isBlank());
		checkBox.clear();
		panel.removeAll();
		panel.revalidate();
		
		try {
			
			
			if(bookID.compareTo("000000") != 0) {
				
				
				
				Statement st = dtbConn.createStatement();
				String str = "SELECT * FROM bookinfo WHERE bookID = " + bookID + ";";
				ResultSet r = st.executeQuery(str);
				
				while(r.next()) {
					
					String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
					JCheckBox j = new JCheckBox(parsed);
					
					checkBox.add(j);
					panel.add(j);	
				}
				
				
				
			}
			else {
				
				
				String name = tfName.getText(), author = tfAuthor.getText()  , category =  tfCategory.getText();
				
				
				
				String stmName = !name.isEmpty() ? "WHERE bookQTY > 0 AND bookName LIKE " + "'"+name+"%' OR " : " WHERE bookQTY > 0 AND ";
				String stmAuthor = !author.isEmpty() ?  "bookAuthor LIKE " + "'"+author+"%' OR " : " bookID = -1 OR ";
				String stmCat = !category.isEmpty() ?  "bookCategory LIKE " + "'"+category+"%'" : " bookID = -1";
				
				
				String str = "SELECT * FROM bookinfo " + stmName + stmAuthor + stmCat + ";";
				
				Statement st = dtbConn.createStatement();
				ResultSet r = st.executeQuery(str);
				Font f = new Font("Tahoma Bold", Font.BOLD, 12) ;
				while(r.next()) {
					
					String parsed = "ID: " + r.getString("bookID") + "   NAME: " + r.getString("bookName") + "   CATEGORY: " + r.getString("bookCategory") + "   QTY: " + r.getString("bookQTY");
					JCheckBox j = new JCheckBox(parsed);
					j.setBackground(new Color(128,128,128));
					j.setFont(f);
					checkBox.add(j);
					panel.add(j);	
				}
				
				
			}
			
			
			
			
		}
		catch (SQLException e1) {
			//isSuccess = false;
			System.out.println("SQL_ERROR: " + e1.getMessage());
			JOptionPane.showMessageDialog(windowRef, "SQL Failed - Error getting number of book entries");
			
		}
		
		panel.revalidate();
		panel.repaint();
	}
	
	
	
	
	
	
	private static String generateID()  {
		
		Statement st;
		try {
			st = dtbConn.createStatement();
		
			ResultSet r = st.executeQuery("SELECT (id) FROM deleted_ids WHERE idType = 0");
			
			if(r.next()) {
				String id = String.valueOf(r.getInt("id"));
				st.executeUpdate("DELETE FROM deleted_ids WHERE id = " + id);
				return id;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return String.valueOf(numOfBookEntries + 1);
	}
	
	
	
	
	private static void createActionResponse() {
		
		
		
		if(numOfBookEntries < 0) {
			try {
				Statement st = dtbConn.createStatement();
				String str = "SELECT COUNT(bookName) AS entry_count FROM bookinfo;";
				ResultSet r = st.executeQuery(str);
				
				if(r.next()) {
					numOfBookEntries = r.getInt("entry_count");
				}
				
				
			} catch (SQLException e1) {
				//isSuccess = false;
				System.out.println("SQL_ERROR: " + e1.getMessage());
				JOptionPane.showMessageDialog(windowRef, "SQL Failed - Error getting number of book entries");
				
			}
			
		}
		
		
		
		String bookName = "'" + tfName.getText() + "'", bookAuthor = "'" + tfAuthor.getText() + "'", bookCat = "'" + tfCategory.getText() + "'", bookID = generateID(), bookYear = ftfYear.getText();
		boolean createSuccess = true;
		boolean isValid = !(tfName.getText().isEmpty() || tfAuthor.getText().isEmpty() || tfCategory.getText().isEmpty() || ftfYear.getText().isEmpty());
		String str = null;
		if(isValid) {
			
			
			try {
				Statement st = dtbConn.createStatement();
				str = "INSERT INTO bookinfo VALUES (" + bookID + "," + bookName + "," + bookCat + "," + bookAuthor + "," + bookYear + "," + "1" + ");";
				st.executeUpdate(str);
				
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
				System.out.println("SQL STATEMENT: " + str);
				createSuccess = false;
				JOptionPane.showMessageDialog(windowRef, "SQL Failed");
				
			}
			
			if(createSuccess) {
				JOptionPane.showMessageDialog(windowRef, "Book Added Successfully");
				numOfBookEntries++;
				tfName.setText(""); tfAuthor.setText(""); tfCategory.setText("");  ftfYear.setText(""); 
			}
			
		}
		else {
			JOptionPane.showMessageDialog(windowRef, "[ERROR] Empty fields detected");
		}
		
		
		
		
	}
	
	
	
	
}
