import java.sql.*;  // For database connection and SQL classes
import javax.swing.*;  // For GUI components
import java.awt.*;  // For layout managers
import java.awt.event.*;  // For event handling
import javax.swing.table.DefaultTableModel;  // For managing table data
import java.time.LocalDate;

class Attendance implements ActionListener
{
 private JPanel Panel1, Panel2, Panel3, Panel4;
 private JButton btn_save1,btn_cancel1,btn_view1,btn_save2,btn_cancel2,btn_view2;
 private JTextField Txt_t1, Txt_t2, Txt_mname, Txt_Tname;
  private JComboBox<String> cb_Member,cb_Hour3,cb_Trainer,cb_Hour2;
  private JLabel Lb1_MEMBER, Lb2_TRAINER,image1,Lb3_ID1,Lb4_ID2,Lb5_Hour1,Lb6_Hour2,Lb7_date1,Lb8_date2,Lb9_mname,Lb10_Tname;
 JFrame frm;
 DefaultTableModel model,model1;
  JScrollPane scrollPane;
  public Connection conn;

 Attendance(Dimension screenSize)
 {

		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

  LocalDate dt= LocalDate.now();
    frm = new JFrame("CRUD Operation Demo");
          frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frm.setTitle("Attendance");

		          frm.setUndecorated(true); // Remove window decorations
				          frm.setExtendedState(JFrame.MAXIMIZED_BOTH); // Make fullscreen

          conn = DBConnect.setConnection();


        ImageIcon i1 = new ImageIcon("ABanner.png");
	        Image img1 = i1.getImage();
	       Image resizedImg1 = img1.getScaledInstance(screenWidth , 250,Image.SCALE_SMOOTH );
	        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
	        image1 = new JLabel(resizedIcon1);
	        image1.setBounds(0, 0, screenWidth, 250);
	        frm.add(image1);

        Panel1 = new JPanel();
        Panel1.setLayout(null);
        Panel1.setBounds(80, 300, 500, 240);

	Lb1_MEMBER=new JLabel("Member");
	Lb1_MEMBER.setBounds(240,10,120,40);
	Panel1.add(Lb1_MEMBER);

	Lb3_ID1=new JLabel("MemberID:");
	Lb3_ID1.setBounds(40,60,80,40);
	Panel1.add(Lb3_ID1);

	cb_Member = new JComboBox<>(new String[]{});
	cb_Member.setBounds(120,60,120,40);
	Panel1.add(cb_Member);
	DBConnect.fillCombo(cb_Member,"Member","mem_id");
	cb_Member.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        String selectedMemId = (String) cb_Member.getSelectedItem();
	        if (selectedMemId != null && !selectedMemId.isEmpty()) {
	            displayMemberName(selectedMemId);
	        }
	    }
	});


    Lb5_Hour1=new JLabel("No. of Hours:");
    Lb5_Hour1.setBounds(260,60,100,40);
    Panel1.add(Lb5_Hour1);



	cb_Hour2 =new JComboBox<>(new String[]{"1","2","3","4","5","6"});
	cb_Hour2.setBounds(360,60,120,40);
	Panel1.add(cb_Hour2);

	frm.add(Panel1);

	Lb7_date1=new JLabel("Date:");
	Lb7_date1.setBounds(40,120,60,40);
	Panel1.add(Lb7_date1);

	Lb9_mname=new JLabel("name:");
	Lb9_mname.setBounds(260,120,60,40);
	Panel1.add(Lb9_mname);


	Txt_t1 = new JTextField(""+dt);
	Txt_t1.setBounds(120,120,120,40);
	Panel1.add(Txt_t1);

	Txt_mname =new JTextField();
	Txt_mname.setBounds(360,120,120,40);
	Panel1.add(Txt_mname);

	btn_save1=new JButton("Save");
  btn_save1.setBounds(40,180,140,40);
    Panel1.add(btn_save1);
    btn_save1.addActionListener(this);

    btn_cancel1=new JButton("Delete");
	btn_cancel1.setBounds(200,180,140,40);
    Panel1.add(btn_cancel1);
     btn_cancel1.addActionListener(this);

     btn_view1=new JButton("Back");
	btn_view1.setBounds(360,180,140,40);
    Panel1.add(btn_view1);
      btn_view1.addActionListener(this);

	Panel2 = new JPanel();
	Panel2.setLayout(null);
        Panel2.setBounds(80, 560, 540, 420);

	 model = new DefaultTableModel();
	  JTable table = new JTable(model);

	        // Table columns
	        model.addColumn("MemberID");
	        model.addColumn("MemberName");
	        model.addColumn("Date");
	        model.addColumn("No of hours");


	        scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(20, 20, 520, 100);
	        Panel2.add(scrollPane);

        frm.add(Panel2);
        displayAllRecords();


         Panel3 = new JPanel();
		        Panel3.setLayout(null);
		        Panel3.setBounds(800, 300, 500, 240);

			 Lb2_TRAINER=new JLabel("Trainer");
			 Lb2_TRAINER.setBounds(240,10,120,40);
			Panel3.add( Lb2_TRAINER);

			Lb4_ID2=new JLabel("TrainerID:");
			Lb4_ID2.setBounds(40,60,80,40);
			Panel3.add(Lb4_ID2);

			cb_Trainer = new JComboBox<>(new String[]{});
			cb_Trainer.setBounds(120,60,120,40);
			Panel3.add(cb_Trainer);
			DBConnect.fillCombo(cb_Trainer,"Trainer","Trainer_id");
			cb_Trainer.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        String selectedTId = (String) cb_Trainer.getSelectedItem();
				        if (selectedTId != null && !selectedTId.isEmpty()) {
				            displayTrainerName(selectedTId);
				        }
				    }
	});

		    Lb6_Hour2=new JLabel("No. of Hours:");
		    Lb6_Hour2.setBounds(260,60,100,40);
		    Panel3.add(Lb6_Hour2);



			cb_Hour3 =new JComboBox<>(new String[]{"1","2","3","4","5","6","7","8","9"});
			cb_Hour3.setBounds(360,60,120,40);
			Panel3.add(cb_Hour3);

			frm.add(Panel3);

			Lb8_date2=new JLabel("Date:");
			Lb8_date2.setBounds(40,120,60,40);
			Panel3.add(Lb8_date2);



			Txt_t2 = new JTextField(""+dt);
			Txt_t2.setBounds(120,120,120,40);
			Panel3.add(Txt_t2);

			Lb10_Tname=new JLabel("name:");
		Lb10_Tname.setBounds(260,120,60,40);
	Panel3.add(Lb10_Tname);

	Txt_Tname =new JTextField();
		Txt_Tname.setBounds(360,120,120,40);
	Panel3.add(Txt_Tname);

			btn_save2=new JButton("Save");
		    btn_save2.setBounds(40,180,140,40);
		    Panel3.add(btn_save2);
		     btn_save2.addActionListener(this);

		    btn_cancel2=new JButton("Delete");
			btn_cancel2.setBounds(200,180,140,40);
		    Panel3.add(btn_cancel2);
		     btn_cancel2.addActionListener(this);

		     btn_view2=new JButton("back");
				btn_view2.setBounds(360,180,140,40);
		    Panel3.add(btn_view2);
		    btn_view2.addActionListener(this);

			Panel4 = new JPanel();
			Panel4.setLayout(null);
		        Panel4.setBounds(800, 560, 540, 420);

			 model1 = new DefaultTableModel();
			  JTable table1 = new JTable(model1);

			        // Table columns
			        model1.addColumn("TrainerID");
			        model1.addColumn("TrainerName");
			        model1.addColumn("Date");
			        model1.addColumn("No of hours");


			        scrollPane = new JScrollPane(table1);
			        scrollPane.setBounds(800, 575, 520, 100);
			        Panel4.add(scrollPane);

        frm.add(Panel4);
        displayAllRecords1();
        frm.setVisible(true);


	}


  public void actionPerformed(ActionEvent ae) {
	  if(ae.getSource()==btn_view1)
	  {
         frm.dispose();
        }
         if(ae.getSource()==btn_view2)
			  {
		         frm.dispose();
        }

        if(ae.getSource()==btn_save1)
        {

			InsertRecord();
			 clearTableModel(model);
            displayAllRecords();
		}
		  if(ae.getSource()==btn_save2)
		        {

					InsertRecord1();
					 clearTableModel1(model1);
		            displayAllRecords1();
		}

		if(ae.getSource()==btn_cancel1)
		{

			            DeleteRecord();
			            clearTableModel(model);
            displayAllRecords();
		}
		if(ae.getSource()==btn_cancel2)
				{

					            DeleteRecord1();
					            clearTableModel1(model1);
		            displayAllRecords1();
		}
	}
	 void InsertRecord() {
	       try {
	            String mid = (String)cb_Member.getSelectedItem();
	            String mname = Txt_mname.getText().trim();
	            int hour1 = Integer.parseInt((String) cb_Hour2.getSelectedItem());
	            String date = Txt_t1.getText().trim();

	            if (DBConnect.isValidName(mname)) {
	                PreparedStatement pst = conn.prepareStatement("INSERT INTO M_Attendance (mem_id, mem_name, date, hours) VALUES (?, ?, ?, ?)");
	                pst.setString(1, mid);
	                pst.setString(2, mname);
	                pst.setString(3, date);
	                pst.setInt(4, hour1);

	                pst.executeUpdate();
	                JOptionPane.showMessageDialog(frm, "Record Inserted Successfully!");
	            } else {
	                JOptionPane.showMessageDialog(frm, "Invalid member Name!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
	     void InsertRecord1() {
			       try {
			            String tid = (String)cb_Trainer.getSelectedItem();
			            String tname = Txt_Tname.getText().trim();
			            int hour2 = Integer.parseInt((String) cb_Hour3.getSelectedItem());
			            String date = Txt_t2.getText().trim();

			            if (DBConnect.isValidName(tname)) {
			                PreparedStatement pst = conn.prepareStatement("INSERT INTO T_Attendance (Trainer_id, Tname, date, hours) VALUES (?, ?, ?, ?)");
			                pst.setString(1, tid);
			                pst.setString(2, tname);
			                pst.setString(3, date);
			                pst.setInt(4, hour2);

			                pst.executeUpdate();
			                JOptionPane.showMessageDialog(frm, "Record Inserted Successfully!");
			            } else {
			                JOptionPane.showMessageDialog(frm, "Invalid Trainer Name!");
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			        }

	    }

	      void DeleteRecord() {
		        try {
				            String mid = (String)cb_Member.getSelectedItem();
				            DBConnect.DeleteRecord("M_Attendance", "mem_id", mid);
				            JOptionPane.showMessageDialog(frm, "Record Deleted Successfully!");
				        } catch (Exception e) {
				            e.printStackTrace();
		        }

    }
    void DeleteRecord1() {
			        try {
					            String tid = (String)cb_Trainer.getSelectedItem();
					            DBConnect.DeleteRecord("T_Attendance", "Trainer_id", tid);
					            JOptionPane.showMessageDialog(frm, "Record Deleted Successfully!");
					        } catch (Exception e) {
					            e.printStackTrace();
			        }

    }
 private void displayAllRecords() {
        try {
            conn = DBConnect.setConnection();
            String query = "SELECT mem_id, mem_name, date, hours FROM M_Attendance";
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String mid = results.getString("mem_id");
                String mname = results.getString("mem_name");
                String date = results.getString("date");
                int hours = results.getInt("hours");


                model.addRow(new Object[]{mid, mname, date, hours});
            }
            results.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

private void displayAllRecords1() {
        try {
            conn = DBConnect.setConnection();
            String query = "SELECT Trainer_id, Tname, date, hours FROM T_Attendance";
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String tid = results.getString("Trainer_id");
                String tname = results.getString("Tname");
                String date = results.getString("date");
                int hours = results.getInt("hours");


                model1.addRow(new Object[]{tid, tname, date, hours});
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
public void clearTableModel1(DefaultTableModel model1) {
	    // Remove all rows from the table model
	    int rowCount = model1.getRowCount();
	    for (int i = rowCount - 1; i >= 0; i--) {
	        model1.removeRow(i);
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
            Txt_mname.setText(memberName);
        } else {
            Txt_mname.setText(""); // Clear the field if no member found
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
            Txt_Tname.setText(TName);
        } else {
            Txt_Tname.setText(""); // Clear the field if no member found
        }

        rs.close();
        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		new Attendance(screenSize);
	}
	}




