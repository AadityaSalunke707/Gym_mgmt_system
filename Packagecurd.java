import java.sql.*;  // For database connection and SQL classes
import javax.swing.*;  // For GUI components
import java.awt.*;  // For layout managers
import java.awt.event.*;  // For event handling
import javax.swing.table.DefaultTableModel;  // For managing table data

class Packagecurd implements ActionListener {
    private JPanel Panel1, Panel2, Panel3, Panel4;
    private JLabel Lb1_pid, Lb2_pname, Lb3_Duration, Lb4_Fees, Lb5_Type, image1;
    private JTextField Txt_pid, Txt_pname, Txt_Fees, Txt_search;
    private JButton btn_modi, btn_add, btn_back, btn_del, btn_search, btn_reset;
    private JComboBox<String> cb1_Type, cb2_Months;
    DefaultTableModel model;
    JScrollPane scrollPane;
    JFrame frm;
    public Connection conn;

    // Constructor to initialize the GUI
  Packagecurd(Dimension screenSize) {

          int screenWidth = (int) screenSize.getWidth();
          int screenHeight = (int) screenSize.getHeight();

          frm = new JFrame("Package");
          frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frm.setTitle("Package Management");
          frm.setUndecorated(true); // Remove window decorations
		          frm.setExtendedState(JFrame.MAXIMIZED_BOTH); // Make fullscreen
        frm.setVisible(true);
          conn = DBConnect.setConnection();

          // Adding image
          ImageIcon i1 = new ImageIcon("PBanner.png");
          Image img1 = i1.getImage();
          Image resizedImg1 = img1.getScaledInstance(screenWidth, 250, Image.SCALE_SMOOTH);
          ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
          image1 = new JLabel(resizedIcon1);
          image1.setBounds(0, 0, screenWidth, 250);
          frm.add(image1);

          // Setting up the panel for input fields
          Panel1 = new JPanel();
          Panel1.setLayout(null);
          Panel1.setBounds(20, 300, screenWidth / 2 - 40, 250);

          Lb1_pid = new JLabel("Package Id");
          Lb1_pid.setBounds(60, 10, 120, 40);
          Panel1.add(Lb1_pid);

          Lb2_pname = new JLabel("Package Name");
          Lb2_pname.setBounds(60, 60, 120, 40);
          Panel1.add(Lb2_pname);

          Lb3_Duration = new JLabel("Duration (months)");
          Lb3_Duration.setBounds(60, 110, 120, 40);
          Panel1.add(Lb3_Duration);

          Lb4_Fees = new JLabel("Fees (Rs)");
          Lb4_Fees.setBounds(60, 160, 120, 40);
          Panel1.add(Lb4_Fees);

          Lb5_Type = new JLabel("Type");
          Lb5_Type.setBounds(60, 210, 120, 40);
          Panel1.add(Lb5_Type);

          // Input fields
          Txt_pid = new JTextField(DBConnect.alphanum_autoId("Package", "pack_id"));
          Txt_pid.setBounds(210, 10, 290, 40);
          Txt_pid.setEditable(true);
          Panel1.add(Txt_pid);

          Txt_pname = new JTextField();
          Txt_pname.setBounds(210, 60, 290, 40);
          Panel1.add(Txt_pname);

          cb2_Months = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
          cb2_Months.setBounds(210, 110, 290, 40);
          Panel1.add(cb2_Months);

          Txt_Fees = new JTextField();
          Txt_Fees.setBounds(210, 160, 290, 40);
          Panel1.add(Txt_Fees);

          cb1_Type = new JComboBox<>(new String[]{"gold", "silver", "basic"});
          cb1_Type.setBounds(210, 210, 290, 40);
          Panel1.add(cb1_Type);

          frm.add(Panel1);

          // Panel for buttons
          Panel2 = new JPanel();
          Panel2.setLayout(null);
          Panel2.setBounds(20, 580, screenWidth / 2 - 40, 100);

          btn_add = new JButton("INSERT");
          btn_add.setBounds(50, 20, 120, 40);
          btn_add.addActionListener(this);
          Panel2.add(btn_add);

          btn_del = new JButton("DELETE");
          btn_del.setBounds(200, 20, 120, 40);
          btn_del.addActionListener(this);
          Panel2.add(btn_del);

          btn_modi = new JButton("UPDATE");
          btn_modi.setBounds(350, 20, 120, 40);
          btn_modi.addActionListener(this);
          Panel2.add(btn_modi);

          btn_back = new JButton("Back");
          btn_back.setBounds(500, 20, 120, 40);
          btn_back.addActionListener(this);
          Panel2.add(btn_back);

          frm.add(Panel2);

          // Panel for displaying the table
          Panel3 = new JPanel();
          Panel3.setLayout(null);
          Panel3.setBounds(screenWidth / 2 + 20, 360, screenWidth / 2 - 60, 300);

          model = new DefaultTableModel();
          JTable table = new JTable(model);

          // Table columns
          model.addColumn("Package-ID");
          model.addColumn("Package-Name");
          model.addColumn("Duration");
          model.addColumn("Fees");
          model.addColumn("Type");

          scrollPane = new JScrollPane(table);
          scrollPane.setBounds(20, 20, screenWidth / 2 - 100, 250);
          Panel3.add(scrollPane);

          frm.add(Panel3);
          displayAllRecords();

          // Panel for search functionality
          Panel4 = new JPanel();
          Panel4.setLayout(null);
          Panel4.setBounds(screenWidth / 2 + 20, 300, screenWidth / 2 - 400, 150);

          btn_search = new JButton("Search:");
          btn_search.setBounds(screenWidth / 2 + 20, 300, 120, 40);
          btn_search.addActionListener(this);
          Panel4.add(btn_search);

          Txt_search = new JTextField();
          Txt_search.setBounds(screenWidth / 2 + 160, 300, 440, 40);
          Panel4.add(Txt_search);

          btn_reset = new JButton("Reset");
          btn_reset.setBounds(screenWidth / 2 +620, 300, 120, 40);
          btn_reset.addActionListener(this);
          Panel4.add(btn_reset);

          frm.add(Panel4);

          frm.setVisible(true);
    }

    // Event handler for button actions
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btn_add) {


            InsertRecord();
            clearTableModel(model);
            displayAllRecords();
        }

        if (ae.getSource() == btn_del) {


            DeleteRecord();
            clearTableModel(model);
            displayAllRecords();

        }

        if (ae.getSource() == btn_modi) {

            ModifyRecord();
            clearTableModel(model);
            displayAllRecords();

        }
           if(ae.getSource()==btn_back)
           {
			   frm.dispose();
		}

        if (ae.getSource() == btn_search) {
			String searchTerm = Txt_search.getText().trim();

			    if (searchTerm.isEmpty()) {
			        JOptionPane.showMessageDialog(frm, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
			        return;
			    }


			    model.setRowCount(0);

			    try {
			        conn = DBConnect.setConnection();
			        String query = "SELECT pack_id, pack_name, duration, fee, pack_type FROM Package WHERE pack_name LIKE ?";
			        PreparedStatement pst = conn.prepareStatement(query);
			        pst.setString(1, "%" + searchTerm + "%");

			        ResultSet rs = pst.executeQuery();

			        while (rs.next()) {
			            String pid = rs.getString("pack_id");
			            String pname = rs.getString("pack_name");
			            int duration = rs.getInt("duration");
			            float fees = rs.getFloat("fee");
			            String type = rs.getString("pack_type");


			            model.addRow(new Object[]{pid, pname, duration, fees, type});
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

        if (ae.getSource() == btn_reset) {

            Txt_search.setText("");
            clearTableModel(model);
            displayAllRecords();
        }
    }


    void InsertRecord() {
       try {
            String pid = Txt_pid.getText().trim();
            String pname = Txt_pname.getText().trim();
            int duration = Integer.parseInt((String) cb2_Months.getSelectedItem());
            float fees = Float.parseFloat(Txt_Fees.getText().trim());
            String type = (String) cb1_Type.getSelectedItem();

            if (DBConnect.isValidName(pname)) {
                PreparedStatement pst = conn.prepareStatement("INSERT INTO Package (pack_id, pack_name, duration, fee, pack_type) VALUES (?, ?, ?, ?, ?)");
                pst.setString(1, pid);
                pst.setString(2, pname);
                pst.setInt(3, duration);
                pst.setFloat(4, fees);
                pst.setString(5, type);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(frm, "Record Inserted Successfully!");
            } else {
                JOptionPane.showMessageDialog(frm, "Invalid Package Name!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void DeleteRecord() {
        try {
		            String pid = Txt_pid.getText().trim();
		            DBConnect.DeleteRecord("Package", "pack_id", pid);
		            JOptionPane.showMessageDialog(frm, "Record Deleted Successfully!");
		        } catch (Exception e) {
		            e.printStackTrace();
        }

    }


    void ModifyRecord()
    {
   try {
               String pid = Txt_pid.getText().trim();
               String pname = Txt_pname.getText().trim();
               int duration = Integer.parseInt((String) cb2_Months.getSelectedItem());
               float fees = Float.parseFloat(Txt_Fees.getText().trim());
               String type = (String) cb1_Type.getSelectedItem();
               String fees1= String.valueOf(fees);

               if(DBConnect.isValidPrice(fees1))
               {
				   JOptionPane.showMessageDialog(frm, "Invalid Fee type");
			   }

		            if (DBConnect.isValidName(pname)) {
		                PreparedStatement pst = conn.prepareStatement("UPDATE Package SET pack_name=?, duration=?, fee=?, pack_type=? WHERE pack_id=?");
		                pst.setString(1, pname);
		                pst.setInt(2, duration);
		                pst.setFloat(3, fees);
		                pst.setString(4, type);
		                pst.setString(5, pid);
		                pst.executeUpdate();
		                JOptionPane.showMessageDialog(frm, "Record Updated Successfully!");
		            } else {
		                JOptionPane.showMessageDialog(frm, "Invalid Package Name!");
		            }
		        } catch (Exception e) {
            e.printStackTrace();

               }
}
 private void displayAllRecords() {
        try {
            conn = DBConnect.setConnection();
            String query = "SELECT pack_id, pack_name, duration, fee, pack_type FROM Package";
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String pid = results.getString("pack_id");
                String pname = results.getString("pack_name");
                int duration = results.getInt("duration");
                float fees = results.getFloat("fee");
                String type = results.getString("pack_type");

                model.addRow(new Object[]{pid, pname, duration, fees, type});
            }
            results.close();
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
        new Packagecurd(screenSize);
    }
}
