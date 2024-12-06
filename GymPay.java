
import java.sql.*;  // For database connection and SQL classes
import javax.swing.*;  // For GUI components
import java.awt.*;  // For layout managers
import java.awt.event.*;  // For event handling
import javax.swing.table.DefaultTableModel;  // For managing table data
import java.time.LocalDate;
import java.io.FileWriter;
import java.io.IOException;

class GymPay implements ActionListener {
JLabel l1_Date, l2_ID, l3_package, l4_amount, l5_Mode, l6,l7_Rid,l8_mname,l9_pname,l10_ptype,image1,l11_Sid, l12_trainerId, l13_trainerName, l14_basicPay, l15_days, l16_hours, l17_totalSalary;;
JTextField t1_Date, t2_Package, t3_amount, t4,t5_RId,t6_mname,t7_pname,t10_ptype, t8_Sid, t9_trainerName, t11_basicPay, t12_days, t13_hours, t14_totalSalary;
JButton btn1_submit, btn2_back,btn3_view,btn1_submit1,btn2_view,btn4_reciept,btn5_submit2,btn6_view2,btn7_reciept2,btn8_cancel,btn9_cancel1;
JRadioButton opt1, opt2, opt3;
JComboBox<String> cb1,cb2_package,cb3_trainerId;
JFrame frm;
JPanel Panel1;//leftPanel;
 DefaultTableModel model,model1;
  JScrollPane scrollPane,scrollPane1;
public Connection conn;

GymPay(Dimension screenSize) {
int screenWidth = (int) screenSize.getWidth();
          int screenHeight = (int) screenSize.getHeight();
   frm = new JFrame("CRUD Operation Demo");
          frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frm.setTitle("Payment");

		   frm.setUndecorated(true); // Remove window decorations
		 frm.setExtendedState(JFrame.MAXIMIZED_BOTH); // Make fullscreen
		  conn = DBConnect.setConnection();

				   ImageIcon i1 = new ImageIcon("RBanner.png");
						  	        Image img1 = i1.getImage();
						  	       Image resizedImg1 = img1.getScaledInstance(screenWidth , 280,Image.SCALE_SMOOTH );
						  	        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
						  	        image1 = new JLabel(resizedIcon1);
						  	        image1.setBounds(0, 0, screenWidth, 280);
						  	        frm.add(image1);
						  Panel1 = new JPanel();
						          Panel1.setLayout(null);
        Panel1.setBounds(80, 300, 500, 240);
Panel1.setVisible(true);
LocalDate dt= LocalDate.now();
l7_Rid = new JLabel("Reciept Id");
l7_Rid.setBounds(80, 300, 200, 30);
 Panel1.add(l7_Rid);

 l8_mname= new JLabel("Membar Name");
l8_mname.setBounds(360, 300, 200, 30);
 Panel1.add(l8_mname);

 l9_pname= new JLabel("Package Name");
l9_pname.setBounds(80, 350, 200, 30);
 Panel1.add(l9_pname);

 l10_ptype = new JLabel("Package type");
l10_ptype.setBounds(360, 350, 200, 30);
 Panel1.add(l10_ptype);

 l1_Date = new JLabel("Date :");
l1_Date.setBounds(80, 400, 200, 30);
 Panel1.add(l1_Date);
l2_ID = new JLabel("Member ID :");
l2_ID.setBounds(80, 460, 200, 30);
 Panel1.add(l2_ID);
l3_package= new JLabel("Package ID :");
l3_package.setBounds(360, 400, 200, 30);
 Panel1.add(l3_package);
l4_amount = new JLabel("Total Amount :");
l4_amount.setBounds(360, 460, 200, 30);
 Panel1.add(l4_amount);
l5_Mode = new JLabel("Payment Mode :");
l5_Mode.setBounds(80, 510, 200, 30);
 Panel1.add(l5_Mode);
l6 = new JLabel("");
l6.setBounds(240, 510, 200, 100);
l6.setVisible(false);
 Panel1.add(l6);

 t1_Date = new JTextField(""+dt);
 t1_Date.setBounds(170, 400, 150, 30);
 Panel1.add(t1_Date);

t5_RId = new JTextField(DBConnect.alphanum_autoId("RPayment", "Reciept_no"));
t5_RId.setBounds(170, 300, 150, 30);
//t5_RId.setEditable(false);
 Panel1.add(t5_RId);

t6_mname = new JTextField();
t6_mname.setBounds(460, 300, 150, 30);
 Panel1.add(t6_mname);

 t7_pname = new JTextField();
  t7_pname.setBounds(170, 350, 150, 30);
 Panel1.add(t7_pname);

t10_ptype = new JTextField();
t10_ptype.setBounds(460, 350, 150, 30);
 Panel1.add(t10_ptype);
//t2_Package = new JTextField();
//t2_Package.setBounds(460, 300, 200, 30);
// Panel1.add(t2_Package);
t3_amount = new JTextField();
t3_amount.setBounds(460, 460, 150, 30);
 Panel1.add(t3_amount);
t4 = new JTextField();
t4.setBounds(380, 550, 200, 30);
t4.setVisible(false);
 Panel1.add(t4);

opt1 = new JRadioButton("Cash ");
opt1.setBounds(80, 550, 100, 30);
 Panel1.add(opt1);
opt1.addActionListener(this);
opt2 = new JRadioButton("QR Code ");
opt2.setBounds(80, 580, 100, 30);
 Panel1.add(opt2);
opt2.addActionListener(this);
opt3 = new JRadioButton("Online Transaction ");
opt3.setBounds(80, 610, 150, 30);
 Panel1.add(opt3);
opt3.addActionListener(this);
ButtonGroup bg = new ButtonGroup();
bg.add(opt1);
bg.add(opt2);
bg.add(opt3);

cb1 = new JComboBox<>(new String[]{});
cb1.setBounds(170, 460, 150, 30);
 Panel1.add(cb1);
DBConnect.fillCombo(cb1,"Member","mem_id");
cb1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        String selectedMemId = (String) cb1.getSelectedItem();
	        if (selectedMemId != null && !selectedMemId.isEmpty()) {
	            displayMemberName(selectedMemId);
	        }
	    }
	});


cb2_package = new JComboBox<>(new String[]{});
cb2_package.setBounds(460, 400, 150, 30);
 Panel1.add(cb2_package);
DBConnect.fillCombo(cb2_package,"Package","pack_id");
cb2_package.addActionListener(new ActionListener() {
@Override
 public void actionPerformed(ActionEvent e) {
				        String selectedTId = (String) cb2_package.getSelectedItem();
				        if (selectedTId != null && !selectedTId.isEmpty()) {
				            displayAmount(selectedTId);
				            displayPtype(selectedTId);
				            displayPname(selectedTId);
				        }
				    }
	});


btn1_submit = new JButton("Submit");
btn1_submit.setBounds(80, 650, 100, 30);
btn1_submit.addActionListener(this);
 Panel1.add(btn1_submit);

btn2_back = new JButton("Back");
btn2_back.setBounds(200, 650, 100, 30);
btn2_back.addActionListener(this);
 Panel1.add(btn2_back);

btn3_view =new JButton("view Transactions");
btn3_view.setBounds(320, 650, 150, 30);
btn3_view.addActionListener(this);
 Panel1.add(btn3_view);

 btn4_reciept=new JButton("Reciept");
btn4_reciept.setBounds(480, 650, 100, 30);
 btn4_reciept.addActionListener(this);
 Panel1.add(btn4_reciept);

  model = new DefaultTableModel();
 	  JTable table = new JTable(model);

 	        // Table columns
 	        model.addColumn("Reciept_no");
 	        model.addColumn("MemberID");
 	        model.addColumn("PackageID");
 	        model.addColumn("Payment mode");
 	       model.addColumn("Date");
 	       model.addColumn("Amount");


 	        scrollPane = new JScrollPane(table);
 	        scrollPane.setBounds(80, 700, 520, 100);
 	        scrollPane.setVisible(false);
	        Panel1.add(scrollPane);



 //leftPanel = new JPanel();
  //   leftPanel.setLayout(null);
  //   leftPanel.setBounds(400, 300, 450, 300); // Adjust position and size as needed

     // SID
     l11_Sid = new JLabel("Sid:");
     l11_Sid.setBounds(800, 300, 100, 30);
     Panel1.add(l11_Sid);

     t8_Sid = new JTextField(DBConnect.alphanum_autoId("SPayment", "Reciept_no"));
     t8_Sid.setBounds(950, 300, 150, 30);
    Panel1.add(t8_Sid);

     // Trainer ID JComboBox
     l12_trainerId = new JLabel("Trainer ID:");
     l12_trainerId.setBounds(800, 350, 100, 30);
     Panel1.add(l12_trainerId);

     cb3_trainerId = new JComboBox<>(new String[]{});
     cb3_trainerId.setBounds(950, 350, 150, 30);
     Panel1.add(cb3_trainerId);
     DBConnect.fillCombo(cb3_trainerId, "Trainer", "Trainer_id");
     cb3_trainerId.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String selectedTrainerId = (String) cb3_trainerId.getSelectedItem();
             if (selectedTrainerId != null && !selectedTrainerId.isEmpty()) {
                 displayTrainerName(selectedTrainerId);
                 displayBasicpay(selectedTrainerId);
                 displayDays(selectedTrainerId);
                 displayHours(selectedTrainerId);
                 calculateTotalSalary();
             }
         }
     });

     // Trainer Name
     l13_trainerName = new JLabel("Trainer Name:");
     l13_trainerName.setBounds(800, 400, 100, 30);
     Panel1.add(l13_trainerName);

     t9_trainerName = new JTextField();
     t9_trainerName.setBounds(950, 400, 150, 30);
     t9_trainerName.setEditable(false);
     Panel1.add(t9_trainerName);

     // Basic Pay
     l14_basicPay = new JLabel("Basic Pay:");
     l14_basicPay.setBounds(800, 450, 100, 30);
     Panel1.add(l14_basicPay);

     t11_basicPay = new JTextField();
     t11_basicPay.setBounds(950, 450, 150, 30);
     Panel1.add(t11_basicPay);

     // No. of Days
     l15_days = new JLabel("No. of Days:");
     l15_days.setBounds(800, 500, 100, 30);
    Panel1.add(l15_days);

     t12_days = new JTextField();
     t12_days.setBounds(950, 500, 150, 30);
     Panel1.add(t12_days);

     // Total No. of Hours
     l16_hours = new JLabel("Total Hours:");
     l16_hours.setBounds(800, 550, 100, 30);
     Panel1.add(l16_hours);

     t13_hours = new JTextField();
     t13_hours.setBounds(950, 550, 150, 30);
    Panel1.add(t13_hours);

     // Total Salary
     l17_totalSalary = new JLabel("Total Salary:");
     l17_totalSalary.setBounds(800, 600, 100, 30);
     Panel1.add(l17_totalSalary);

     t14_totalSalary = new JTextField();
     t14_totalSalary.setBounds(950, 600, 150, 30);
     t14_totalSalary.setEditable(false);
      Panel1.add(t14_totalSalary);

  btn5_submit2 = new JButton("Submit");
	 btn5_submit2.setBounds(800, 650, 100, 30);
	btn5_submit2.addActionListener(this);
	  Panel1.add(btn5_submit2);



	btn6_view2=new JButton("view Transactions");
	 btn6_view2.setBounds(920, 650, 150, 30);
	btn6_view2.addActionListener(this);
	  Panel1.add(btn6_view2);

	  btn7_reciept2=new JButton("Reciept");
	btn7_reciept2.setBounds(1090, 650, 100, 30);
	  btn7_reciept2.addActionListener(this);
 Panel1.add(btn7_reciept2);

  btn8_cancel=new JButton("Cancel");
 	 btn8_cancel.setBounds(80, 820, 100, 30);
 	   btn8_cancel.addActionListener(this);
 	   btn8_cancel.setVisible(false);
 Panel1.add( btn8_cancel);
  btn9_cancel1=new JButton("Cancel");
  	   btn9_cancel1.setBounds(800, 820, 100, 30);
  	    btn9_cancel1.addActionListener(this);
  	    btn9_cancel1.setVisible(false);
 Panel1.add(btn9_cancel1);


model1 = new DefaultTableModel();
 	  JTable table1 = new JTable(model1);

 	        // Table columns
 	        model1.addColumn("Reciept_no");
 	        model1.addColumn("TrainerID");
 	        model1.addColumn("Basicpay");
 	        model1.addColumn("No of Days");
 	       model1.addColumn("No of Hours");
 	       model1.addColumn("TotalAmount");


 	        scrollPane1 = new JScrollPane(table1);
 	        scrollPane1.setBounds(800, 700, 420, 100);
 	        scrollPane1.setVisible(false);
	        Panel1.add(scrollPane1);
displayAllTrainerRecords();

frm.add(Panel1);
   frm.setVisible(true);

}

public void actionPerformed(ActionEvent e) {
if (e.getSource() == opt1) {
l6.setText("Enter Amount: ");
l6.setVisible(true);
t4.setVisible(true);
l6.setIcon(null);
} else if (e.getSource() == opt2) {
ImageIcon img1 = new ImageIcon("QR.jpeg");
Image image = img1.getImage(); // transform it
Image newimg = image.getScaledInstance(100,100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
img1 = new ImageIcon(newimg);
l6.setIcon(img1);
l6.setText("");
l6.setVisible(true);
t4.setVisible(false);
} else if (e.getSource() == opt3) {
l6.setText("Enter Transaction ID");
l6.setVisible(true);
t4.setVisible(true);
l6.setIcon(null);
}

if (e.getSource() == btn2_back)
{
	frm.dispose();
}
if(e.getSource()==btn3_view)
{
	 scrollPane.setVisible(true);
	 btn8_cancel.setVisible(true);
	 displayAllRecords();

}
if(e.getSource()==btn6_view2)
{
	 scrollPane1.setVisible(true);
	  btn9_cancel1.setVisible(true);
	 displayAllTrainerRecords();
	// displayAllRecords();

}
if(e.getSource()==btn1_submit)
{
	InsertRecord();
	// resetForm();
}
if(e.getSource()==btn5_submit2)
{
	InsertRecord2();
	// resetForm();
}
if(e.getSource()==btn4_reciept)
{
	member_Receipt();
	// resetForm();
}
if(e.getSource()==btn7_reciept2)
{
	trainer_Receipt();
}
if(e.getSource()==btn8_cancel)
{
	scrollPane.setVisible(false);
	 btn8_cancel.setVisible(false);
}
if(e.getSource()==btn9_cancel1)
{
	scrollPane1.setVisible(false);
	 btn9_cancel1.setVisible(false);
}

}
void InsertRecord() {
    try {
        String recieptId = t5_RId.getText();
        String memberId = (String) cb1.getSelectedItem();
        String packageId = (String) cb2_package.getSelectedItem();
        String date = t1_Date.getText();
        String amount = t3_amount.getText();
        String paymentMode = "";

        // Determine the selected payment mode
        if (opt1.isSelected()) {
            paymentMode = "Cash";
        } else if (opt2.isSelected()) {
            paymentMode = "QR Code";
        } else if (opt3.isSelected()) {
            paymentMode = "Online Transaction";
        }

        // Validate the input fields
        if (recieptId.isEmpty() || memberId == null || packageId == null || date.isEmpty() || amount.isEmpty() || paymentMode.isEmpty()) {
            JOptionPane.showMessageDialog(frm, "Please fill all fields before submitting.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert record into the database
        String query = "INSERT INTO RPayment (Reciept_no, mem_id, pack_id, pay_mode, pay_date,Amount) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, recieptId);
        pst.setString(2, memberId);
        pst.setString(3, packageId);
        pst.setString(4, paymentMode);
        pst.setString(5, date);
        pst.setString(6, amount);

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(frm, "Payment record inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frm, "Failed to insert payment record.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frm, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
void InsertRecord2() {
    try {

        String recieptNo = t8_Sid.getText();
        String trainerId = (String) cb3_trainerId.getSelectedItem();
        float basicPay = Float.parseFloat(t11_basicPay.getText());
        int days = Integer.parseInt(t12_days.getText());
        int hours = Integer.parseInt(t13_hours.getText());
        float totalSalary = Float.parseFloat(t14_totalSalary.getText());

        if (recieptNo.isEmpty() || trainerId == null || t11_basicPay.getText().isEmpty() ||
            t12_days.getText().isEmpty() || t13_hours.getText().isEmpty() || t14_totalSalary.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frm, "Please fill all fields before submitting.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO SPayment (Reciept_no, Trainer_id, basic_pay, days, hours, Total_salary) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, recieptNo);
        pst.setString(2, trainerId);
        pst.setFloat(3, basicPay);
        pst.setInt(4, days);
        pst.setInt(5, hours);
        pst.setFloat(6, totalSalary);


        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(frm, "Salary record inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frm, "Failed to insert salary record.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frm, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frm, "Please enter valid numbers for Basic Pay, Days, Hours, and Total Salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void resetForm() {

    t5_RId.setText(DBConnect.alphanum_autoId("RPayment", "Reciept_no"));


    t6_mname.setText("");
    t7_pname.setText("");
    t10_ptype.setText("");
    t1_Date.setText("" + LocalDate.now());
    cb1.setSelectedIndex(0);
    cb2_package.setSelectedIndex(0);
    t3_amount.setText("");
    t4.setText("");


    opt1.setSelected(false);
    opt2.setSelected(false);
    opt3.setSelected(false);

    // Hide additional fields if necessary
    l6.setVisible(false);
    t4.setVisible(false);
}


private void displayAllRecords() {
    try {

        model.setRowCount(0);

        String query = "SELECT Reciept_no, mem_id, pack_id, pay_mode, pay_date,Amount FROM RPayment";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();


        while (rs.next()) {
            String recieptNo = rs.getString("Reciept_no");
            String memberId = rs.getString("mem_id");
            String packageId = rs.getString("pack_id");
            String paymentMode = rs.getString("pay_mode");
            String date = rs.getString("pay_date");
            String Amount = rs.getString("Amount");


            model.addRow(new Object[]{recieptNo, memberId, packageId, paymentMode, date, Amount});
        }

        rs.close();
        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frm, "Error retrieving records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void displayAllTrainerRecords() {
    try {

        model1.setRowCount(0);


        String query = "SELECT Reciept_no, Trainer_id, basic_pay, days, hours, Total_salary FROM SPayment";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        // Loop through the result set and add each record to the model1
        while (rs.next()) {
            String recieptNo = rs.getString("Reciept_no");
            String trainerId = rs.getString("Trainer_id");
            float basicPay = rs.getFloat("basic_pay");
            int days = rs.getInt("days");
            int hours = rs.getInt("hours");
            float totalSalary = rs.getFloat("Total_salary");


            model1.addRow(new Object[]{recieptNo, trainerId, basicPay, days, hours, totalSalary});
        }


        rs.close();
        pst.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frm, "Error retrieving trainer records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void displayAmount(String TId) {
    try {
        String query = "SELECT fee FROM Package WHERE pack_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String Amount = rs.getString("fee");
           t3_amount.setText(Amount);
        } else {
            t3_amount.setText("");
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void displayPname(String TId) {
    try {
        String query = "SELECT pack_name FROM Package WHERE pack_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String Amount = rs.getString("pack_name");
           t7_pname.setText(Amount);
        } else {
            t7_pname.setText("");
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void displayPtype(String TId) {
    try {
        String query = "SELECT pack_type FROM Package WHERE pack_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String Amount = rs.getString("pack_type");
           t10_ptype.setText(Amount);
        } else {
           t10_ptype.setText("");
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void displayMemberName(String memId) {
    try {
        String query = "SELECT mem_name FROM Member WHERE mem_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, memId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String memberName = rs.getString("mem_name");
            t6_mname.setText(memberName);
        } else {
          t6_mname.setText(""); // Clear the field if no member found
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void displayTrainerName(String TId) {
    try {
        String query = "SELECT Tname FROM Trainer WHERE Trainer_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String TName = rs.getString("Tname");
            t9_trainerName.setText(TName);
        } else {
            t9_trainerName.setText(""); // Clear the field if no member found
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void displayBasicpay(String TId) {
    try {
        String query = "SELECT basicpay FROM Trainer WHERE Trainer_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String TName = rs.getString("basicpay");
            t11_basicPay.setText(TName);
        } else {
            t11_basicPay.setText(""); // Clear the field if no member found
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void displayDays(String TId) {
    try {
        String query = "SELECT COUNT(date) FROM T_Attendance WHERE Trainer_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String TName = rs.getString("COUNT(date)");
            t12_days.setText(TName);
        } else {
            t12_days.setText(""); // Clear the field if no member found
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void displayHours(String TId) {
    try {
        String query = "SELECT SUM(hours) FROM T_Attendance WHERE Trainer_id = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, TId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String TName = rs.getString("SUM(hours)");
            t13_hours.setText(TName);
        } else {
            t13_hours.setText(""); // Clear the field if no member found
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void calculateTotalSalary() {
    try {

        double basicPay = Double.parseDouble(t11_basicPay.getText());
        int days = Integer.parseInt(t12_days.getText());
        int hours = Integer.parseInt(t13_hours.getText());


        double totalSalary = basicPay * days * hours;


        t14_totalSalary.setText(String.format("%.2f", totalSalary));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frm, "Please enter valid numeric values for basic pay, days, and hours.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
public void member_Receipt() {
    try {

        String receiptId = t5_RId.getText();
        String memberId = (String) cb1.getSelectedItem();
        String memberName = t6_mname.getText();
        String packageName = t7_pname.getText();
        String packageType = t10_ptype.getText();
        String date = t1_Date.getText();
        String amount = t3_amount.getText();
        String paymentMode = "";


        if (opt1.isSelected()) {
            paymentMode = "Cash";
        } else if (opt2.isSelected()) {
            paymentMode = "QR Code";
        } else if (opt3.isSelected()) {
            paymentMode = "Online Transaction";
        }


        if (receiptId.isEmpty() || memberId == null || memberName.isEmpty() || packageName.isEmpty() ||
                packageType.isEmpty() || date.isEmpty() || amount.isEmpty() || paymentMode.isEmpty()) {
            JOptionPane.showMessageDialog(frm, "Please fill all fields before generating the receipt.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String receiptFilePath = receiptId + "_Receipt.doc";


        FileWriter writer = new FileWriter(receiptFilePath);

        writer.write("-------- Gym Payment Receipt --------\n");
        writer.write("Receipt ID: " + receiptId + "\n");
        writer.write("Date: " + date + "\n");
        writer.write("Member ID: " + memberId + "\n");
        writer.write("Member Name: " + memberName + "\n");
        writer.write("Package Name: " + packageName + "\n");
        writer.write("Package Type: " + packageType + "\n");
        writer.write("Total Amount: ₹" + amount + "\n");
        writer.write("Payment Mode: " + paymentMode + "\n");
        writer.write("-------------------------------------\n");

        writer.close();
        JOptionPane.showMessageDialog(frm, "Receipt generated successfully at: " + receiptFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frm, "Error generating receipt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
public void trainer_Receipt() {
    try {
        // Retrieving trainer payment information from form fields
        String receiptId = t8_Sid.getText();
        String trainerId = (String) cb3_trainerId.getSelectedItem();
        String trainerName = t9_trainerName.getText();
        String basicPay = t11_basicPay.getText();
        String days = t12_days.getText();
        String hours = t13_hours.getText();
        String totalSalary = t14_totalSalary.getText();

        // Validating input fields
        if (receiptId.isEmpty() || trainerId == null || trainerName.isEmpty() || basicPay.isEmpty() ||
                days.isEmpty() || hours.isEmpty() || totalSalary.isEmpty()) {
            JOptionPane.showMessageDialog(frm, "Please fill all fields before generating the receipt.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Path for the receipt file
        String receiptFilePath = receiptId + "_Trainer_Receipt.doc";

        // Writing receipt content to the file
        FileWriter writer = new FileWriter(receiptFilePath);

        writer.write("-------- Gym Trainer Payment Receipt --------\n");
        writer.write("Receipt ID: " + receiptId + "\n");
        writer.write("Trainer ID: " + trainerId + "\n");
        writer.write("Trainer Name: " + trainerName + "\n");
        writer.write("Basic Pay: ₹" + basicPay + "\n");
        writer.write("Number of Days: " + days + "\n");
        writer.write("Total Hours: " + hours + "\n");
        writer.write("Total Salary: ₹" + totalSalary + "\n");
        writer.write("---------------------------------------------\n");

        writer.close();

        JOptionPane.showMessageDialog(frm, "Trainer receipt generated successfully at: " + receiptFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frm, "Error generating trainer receipt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public static void main(String[] args) {
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new GymPay(screenSize);
}
}
