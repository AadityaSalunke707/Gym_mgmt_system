import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GymReg implements ActionListener {
    JButton btn_search, btn_reset, btn_add, btn_update, btn_delete, btn_Back;
    JTextField txt_search, mem_id, mem_name, mem_age, mem_weight, mem_height, mem_contact, mem_mailid, mem_address;
    JLabel lbl_ID, lbl_name, lbl_age, lbl_weight, lbl_height, lbl_gender, lbl_addr, lbl_phno, lbl_mailID, image1;
    JRadioButton rb_male, rb_female;
    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;
    JPanel jp1, jp2, jp3;
    JFrame frm;
     public Connection conn;

    GymReg(Dimension screenSize) {
        // Set up JFrame
        frm = new JFrame();
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setTitle("Member Registration Window");
        frm.setUndecorated(true); // Remove window decorations
        frm.setExtendedState(JFrame.MAXIMIZED_BOTH); // Make fullscreen
        frm.setVisible(true);
         conn = DBConnect.setConnection();

        // Get the dimensions from the screen
        int frameWidth = (int) screenSize.getWidth();
        int frameHeight = (int) screenSize.getHeight();

        // Panel jp1 for the banner


        ImageIcon i1 = new ImageIcon("MBanner.png");
        Image img1 = i1.getImage();
        Image resizedImg1 = img1.getScaledInstance(frameWidth, 250, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
        image1 = new JLabel(resizedIcon1);
        image1.setBounds(0, 0, frameWidth, 250);
        frm.add(image1);


        // Panel jp2 for input fields
        jp2 = new JPanel();
        jp2.setLayout(null);
        jp2.setBounds(50, 260, 400, 800);


        // Add components to jp2
        btn_add = new JButton("Add");
        btn_add.setBounds(10, 400, 110, 30);
        btn_add.addActionListener(this);
        jp2.add(btn_add);

        btn_update = new JButton("Update");
        btn_update.setBounds(130, 400, 110, 30);
        btn_update.addActionListener(this);
        jp2.add(btn_update);

        btn_delete = new JButton("Delete");
        btn_delete.setBounds(250, 400, 110, 30);
        btn_delete.addActionListener(this);
        jp2.add(btn_delete);

        btn_Back = new JButton("Back");
        btn_Back.setBounds(10, 450, 350, 30);
        btn_Back.addActionListener(this);
        jp2.add(btn_Back);

        // Add input fields
        mem_id = new JTextField(DBConnect.alphanum_autoId("Member", "mem_id"));
        mem_id.setBounds(100, 30, 250, 30);
        jp2.add(mem_id);

        mem_name = new JTextField();
        mem_name.setBounds(100, 70, 250, 30);
        jp2.add(mem_name);

        mem_age = new JTextField();
        mem_age.setBounds(100, 110, 250, 30);
        jp2.add(mem_age);

        mem_weight = new JTextField();
        mem_weight.setBounds(100, 150, 250, 30);
        jp2.add(mem_weight);

        mem_height = new JTextField();
        mem_height.setBounds(100, 190, 250, 30);
        jp2.add(mem_height);

        mem_contact = new JTextField();
        mem_contact.setBounds(100, 230, 250, 30);
        jp2.add(mem_contact);

        mem_mailid = new JTextField();
        mem_mailid.setBounds(100, 270, 250, 30);
        jp2.add(mem_mailid);

        mem_address = new JTextField();
        mem_address.setBounds(100, 310, 250, 30);
        jp2.add(mem_address);

        // Add labels
        lbl_ID = new JLabel("ID :");
        lbl_ID.setBounds(10, 30, 100, 30);
        jp2.add(lbl_ID);

        lbl_name = new JLabel("Name :");
        lbl_name.setBounds(10, 70, 100, 30);
        jp2.add(lbl_name);

        lbl_addr = new JLabel("Address :");
        lbl_addr.setBounds(10, 310, 100, 30);
        jp2.add(lbl_addr);

        lbl_phno = new JLabel("Contact no. :");
        lbl_phno.setBounds(10, 230, 100, 30);
        jp2.add(lbl_phno);

        lbl_mailID = new JLabel("Gmail :");
        lbl_mailID.setBounds(10, 270, 100, 30);
        jp2.add(lbl_mailID);

        lbl_gender = new JLabel("Gender :");
        lbl_gender.setBounds(10, 350, 100, 30);
        jp2.add(lbl_gender);

        lbl_weight = new JLabel("Weight :");
        lbl_weight.setBounds(10, 150, 100, 30);
        jp2.add(lbl_weight);

        lbl_height = new JLabel("Height :");
        lbl_height.setBounds(10, 190, 100, 30);
        jp2.add(lbl_height);

        lbl_age = new JLabel("Age :");
        lbl_age.setBounds(10, 110, 100, 30);
        jp2.add(lbl_age);

        // Gender Radio Buttons
        rb_male = new JRadioButton("Male");
        rb_male.setBounds(100, 350, 100, 30);
        rb_male.addActionListener(this);
        rb_female = new JRadioButton("Female");
        rb_female.setBounds(200, 350, 100, 30);
        rb_female.addActionListener(this);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rb_male);
        bg.add(rb_female);

        jp2.add(rb_male);
        jp2.add(rb_female);

        frm.add(jp2);

        // Panel jp3 for table and search
        jp3 = new JPanel();
        jp3.setLayout(null);
        jp3.setBounds(600, 300,550, frameHeight - 200);

        btn_search = new JButton("Search");
        btn_search.setBounds(500, 300, 120, 30);
        btn_search.addActionListener(this);
        jp3.add(btn_search);

        btn_reset = new JButton("Reset");
        btn_reset.setBounds(630, 300, 120, 30);
        btn_reset.addActionListener(this);
        jp3.add(btn_reset);

        txt_search = new JTextField();
        txt_search.setBounds(760, 300, 500, 30);
        jp3.add(txt_search);

        // Table setup
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Member Name");
        model.addColumn("Address");
        model.addColumn("Contact");
        model.addColumn("Gmail");
        model.addColumn("Gender");
        model.addColumn("Weight");
        model.addColumn("Height");
        model.addColumn("Age");

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(500, 340, 950, 380);
        jp3.add(scrollPane);


        frm.add(jp3);
        displayAllRecords();


     frm.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn_add) {
			InsertRecord();
			clearTableModel(model);
            displayAllRecords();

        }  if (ae.getSource() == btn_update) {
            ModifyRecord();
			clearTableModel(model);
            displayAllRecords();
        }  if (ae.getSource() == btn_delete) {
            DeleteRecord();
			            clearTableModel(model);
            displayAllRecords();
        } if (ae.getSource() == btn_reset) {
             txt_search.setText("");
			 clearTableModel(model);
            displayAllRecords();
        }  if (ae.getSource() == btn_search) {

           String searchTerm = txt_search.getText().trim();

		   			    if (searchTerm.isEmpty()) {
		   			        JOptionPane.showMessageDialog(frm, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
		   			        return;
		   			    }


		   			    model.setRowCount(0);

		   			    try {
		   			        conn = DBConnect.setConnection();
		   			       String query = "SELECT mem_id, mem_name, mem_address, mem_contact, mem_mailid, mem_gender, mem_weight, mem_height, mem_age FROM Member WHERE mem_name LIKE ?";

		   			        PreparedStatement pst = conn.prepareStatement(query);
		   			        pst.setString(1, "%" + searchTerm + "%");

		   			        ResultSet rs = pst.executeQuery();

		   			        while (rs.next()) {
		   			           String id = rs.getString("mem_id");
							   													   String name =  rs.getString("mem_name");

							   													  String address = rs.getString("mem_address");
							   													   String contact = rs.getString("mem_contact");
							   													   String mailid = rs.getString("mem_mailid");

							   								                       String gender =  rs.getString("mem_gender");
							   								                    int weight = rs.getInt("mem_weight");
							   													   int height = rs.getInt("mem_height");
							   													    int age = rs.getInt("mem_age");

	                model.addRow(new Object[]{id, name, address, contact, mailid, gender, weight, height, age});
		   			        }

		   			        rs.close();
		   			        pst.close();
		   			    } catch (SQLException e) {
		   			        e.printStackTrace();
		   			        JOptionPane.showMessageDialog(frm, "An error occurred while searching.", "Error", JOptionPane.ERROR_MESSAGE);
		   			    } catch (Exception e) {
		   			        e.printStackTrace();
    }

        }

        if (ae.getSource() == btn_Back) {
            frm.dispose();
        }

    }
     void InsertRecord() {
	       try {
	                        String id = mem_id.getText();
							String name = mem_name.getText();
							int age = Integer.parseInt(mem_age.getText());
							int weight = Integer.parseInt(mem_weight.getText());
							int height = Integer.parseInt(mem_height.getText());
							String contact = mem_contact.getText();
							String mailid = mem_mailid.getText();
							String address = mem_address.getText();
							String gender = "";
							if (rb_male.isSelected())
							{
								gender = "Male";
							}
							else if (rb_female.isSelected())
							{
								gender = "Female";
                            }

	            if (DBConnect.isValidName(name)) {
	                PreparedStatement pst = conn.prepareStatement("INSERT INTO Member (mem_id, mem_name, mem_address, mem_contact, mem_mailid,mem_gender,mem_weight,mem_height,mem_age) VALUES (?, ?, ?, ?, ?,?,?,?,?)");
	                pst.setString(1, id);
	                pst.setString(2, name);
	                pst.setString(3, address);
	                pst.setString(4, contact );
	                pst.setString(5, mailid);
	                pst.setString(6, gender);
	                pst.setInt(7, weight);
	                pst.setInt(8, height);
	                pst.setInt(9, age);


	                pst.executeUpdate();
	                JOptionPane.showMessageDialog(frm, "Record Inserted Successfully!");
	            }

	            else {
	                JOptionPane.showMessageDialog(frm, "Invalid Member Name!");
	            }
	               if(!DBConnect.isValidAddress(address))
					            {
									JOptionPane.showMessageDialog(frm, "Invalid address");
				}
				  if(!DBConnect.isValidEmail(mailid))
									            {
													JOptionPane.showMessageDialog(frm, "Invalid mailid");
				}
				  if(!DBConnect.isValidcontact(contact))
			    {
			    	JOptionPane.showMessageDialog(frm, "Invalid contact");
				}


	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }


	    void DeleteRecord() {
	        try {
			            String id = mem_id.getText().trim();
			            DBConnect.DeleteRecord("Member", "mem_id", id);
			            JOptionPane.showMessageDialog(frm, "Record Deleted Successfully!");
			        } catch (Exception e) {
			            e.printStackTrace();
	        }

	    }


	  void ModifyRecord() {
	      try {
	          String id = mem_id.getText();
	          String name = mem_name.getText();
	          int age = Integer.parseInt(mem_age.getText());
	          int weight = Integer.parseInt(mem_weight.getText());
	          int height = Integer.parseInt(mem_height.getText());
	          String contact = mem_contact.getText();
	          String mailid = mem_mailid.getText();
	          String address = mem_address.getText();
	          String gender = rb_male.isSelected() ? "Male" : (rb_female.isSelected() ? "Female" : "");

	          if (DBConnect.isValidName(name)) {
	              PreparedStatement pst = conn.prepareStatement(
	                  "UPDATE Member SET mem_name=?, mem_address=?, mem_contact=?, mem_mailid=?, mem_gender=?, mem_weight=?, mem_height=?, mem_age=? WHERE mem_id=?"
	              );

	              pst.setString(1, name);
	              pst.setString(2, address);
	              pst.setString(3, contact);
	              pst.setString(4, mailid);
	              pst.setString(5, gender);
	              pst.setInt(6, weight);
	              pst.setInt(7, height);
	              pst.setInt(8, age);
	              pst.setString(9, id);

	              pst.executeUpdate();
	              JOptionPane.showMessageDialog(frm, "Record Updated Successfully!");
	          } else {
	              JOptionPane.showMessageDialog(frm, "Invalid Data!");
	          }
	           if(!DBConnect.isValidAddress(address))
			  					            {
			  									JOptionPane.showMessageDialog(frm, "Invalid address");
			  				}
			  				  if(!DBConnect.isValidEmail(mailid))
			  									            {
			  													JOptionPane.showMessageDialog(frm, "Invalid mailid");
			  				}
			  				  if(!DBConnect.isValidcontact(contact))
			  			    {
			  			    	JOptionPane.showMessageDialog(frm, "Invalid contact");
							}
						}
catch (Exception e) {
	          e.printStackTrace();
	      }
	  }

	 private void displayAllRecords() {

	        try {
	            conn = DBConnect.setConnection();
	            String query = "SELECT mem_id, mem_name, mem_address, mem_contact, mem_mailid,mem_gender,mem_weight,mem_height,mem_age FROM Member";
	            Statement statement = conn.createStatement();
	            ResultSet rs = statement.executeQuery(query);

	            while (rs.next()) {
	                String id = rs.getString("mem_id");
													   String name =  rs.getString("mem_name");

													  String address = rs.getString("mem_address");
													   String contact = rs.getString("mem_contact");
													   String mailid = rs.getString("mem_mailid");

								                       String gender =  rs.getString("mem_gender");
								                    int weight = rs.getInt("mem_weight");
													   int height = rs.getInt("mem_height");
													    int age = rs.getInt("mem_age");

	                model.addRow(new Object[]{id, name, address, contact, mailid, gender, weight, height, age});
	            }
	            rs.close();
	            statement.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

}
public void clearTableModel(DefaultTableModel model) {
	    // Remove all rows from the table model
	    int rowCount = model.getRowCount();
	    for (int i = rowCount - 1; i >= 0; i--) {
	        model.removeRow(i);
	    }
}

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new GymReg(screenSize);
    }
}
