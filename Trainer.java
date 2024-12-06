import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;

public class Trainer extends JFrame implements ActionListener {
    JPanel inputPanel, buttonPanel, searchPanel, mainPanel, tablePanel;
    JLabel Trainer_Id, TName, TGender, TAge, TSkill, TBasicPay, TAddress, TEmailid, TJoiningDate, TQualification;
    JTextField txtTrainerId, txtName, txtSkill, txtBasicPay, txtAddress, txtEmail, txtJoiningDate, txtQualification, txtSearch;
    JButton btnAdd, btnDelete, btnUpdate, btnView, btnAddSchedule, btnWeeklySchedule, btnSearch, btnReset,btnShow,btnAdd1;
    JTable table;
    JScrollPane scrollPane;
    JRadioButton rbMale, rbFemale;
    JComboBox<String> cbAge;
    DefaultTableModel model;
    JLabel image1;
    Connection conn;

    Trainer(Dimension screenSize) {
        LocalDate dt = LocalDate.now();
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Trainer Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        conn = DBConnect.setConnection();

        ImageIcon i1 = new ImageIcon("TBanner.png");
        Image img1 = i1.getImage();
        Image resizedImg1 = img1.getScaledInstance(screenWidth - 20, 250, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
        image1 = new JLabel(resizedIcon1);
        image1.setBounds(10, 10, screenWidth - 20, 250);

        // Main panel setup
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(10, 10, screenWidth - 20, screenHeight - 20);
        mainPanel.add(image1);

        // Input panel
        inputPanel = new JPanel();
        inputPanel.setLayout(null);
       // inputPanel.setBorder(BorderFactory.createTitledBorder("Trainer Information"));
        inputPanel.setBounds(20, 270, screenWidth / 3 - 40, 420);

        int labelWidth = 100;
        int fieldWidth = 200;
        int rowHeight = 30;
        int y = 20;

        Trainer_Id = new JLabel("ID:");
        Trainer_Id.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(Trainer_Id);
        txtTrainerId = new JTextField(DBConnect.alphanum_autoId("Trainer", "Trainer_id"));
        txtTrainerId.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtTrainerId);
        y += rowHeight + 10;

        TName = new JLabel("Name:");
        TName.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TName);
        txtName = new JTextField();
        txtName.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtName);
        y += rowHeight + 10;

        TAge = new JLabel("Age:");
        TAge.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TAge);
        cbAge = new JComboBox<>(new String[]{"Select", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"});
        cbAge.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(cbAge);
        y += rowHeight + 10;

        TGender = new JLabel("Gender:");
        TGender.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TGender);
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        rbMale.setBounds(120, y, 100, rowHeight);
        rbFemale.setBounds(220, y, 100, rowHeight);
        inputPanel.add(rbMale);
        inputPanel.add(rbFemale);
        y += rowHeight + 10;

        TSkill = new JLabel("Experience:");
        TSkill.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TSkill);
        txtSkill = new JTextField();
        txtSkill.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtSkill);
        y += rowHeight + 10;

        TBasicPay = new JLabel("Salary:");
        TBasicPay.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TBasicPay);
        txtBasicPay = new JTextField();
        txtBasicPay.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtBasicPay);
        y += rowHeight + 10;

        TAddress = new JLabel("Address:");
        TAddress.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtAddress);
        y += rowHeight + 10;

        TEmailid = new JLabel("Email:");
        TEmailid.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TEmailid);
        txtEmail = new JTextField();
        txtEmail.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtEmail);
        y += rowHeight + 10;

        TJoiningDate = new JLabel("Joining Date:");
        TJoiningDate.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TJoiningDate);
        txtJoiningDate = new JTextField(dt.toString());
        txtJoiningDate.setEditable(false);
        txtJoiningDate.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtJoiningDate);
        y += rowHeight + 10;

        TQualification = new JLabel("Qualification:");
        TQualification.setBounds(10, y, labelWidth, rowHeight);
        inputPanel.add(TQualification);
        txtQualification = new JTextField();
        txtQualification.setBounds(120, y, fieldWidth, rowHeight);
        inputPanel.add(txtQualification);

        mainPanel.add(inputPanel);

        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds( 20, 700, screenWidth / 2 - 40, 200);

        btnAdd = new JButton("Add Trainer");
        btnAdd.setBounds(10, 10, 150, 40);
        btnAdd.addActionListener(this);
        buttonPanel.add(btnAdd);

        btnDelete = new JButton("Delete Trainer");
        btnDelete.setBounds(180, 10, 150, 40);
        btnDelete.addActionListener(this);
        buttonPanel.add(btnDelete);

        btnUpdate = new JButton("Update Trainer");
        btnUpdate.setBounds(10, 60, 150, 40);
        btnUpdate.addActionListener(this);
        buttonPanel.add(btnUpdate);

        btnView = new JButton("Back");
        btnView.setBounds(180, 60, 150, 40);
        btnView.addActionListener(this);
        buttonPanel.add(btnView);

         btnShow = new JButton("View Schedule");
		       btnShow.setBounds(350, 60, 150, 40);
		       btnShow.addActionListener(this);
        buttonPanel.add(btnShow);

          btnAdd1 = new JButton("Add Schedule");
				        btnAdd1.setBounds(350, 10, 150, 40);
				    btnAdd1.addActionListener(this);
        buttonPanel.add(btnAdd1);

        mainPanel.add(buttonPanel);

        // Table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Trainer List"));
        tablePanel.setBounds(screenWidth / 2 - 200, 270, (screenWidth / 3 - 40)*2, 300);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Experience");
        model.addColumn("Salary");
        model.addColumn("Email");
        model.addColumn("Joining Date");

        scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel);

        add(mainPanel);
        displayAllRecords();
        setVisible(true);
    }


    public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnShow)
		{
			 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new GymViewSchdl(screenSize);
		}
		if(ae.getSource() == btnAdd1)
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			        WeeklySch ws = new WeeklySch(screenSize);
        ws.setVisible(true);
		}

        if (ae.getSource() == btnAdd) {
            InsertRecord();
            clearText();
            displayAllRecords();
        } else if (ae.getSource() == btnUpdate) {
            ModifyRecord();
            clearText();
            displayAllRecords();
        } else if (ae.getSource() == btnDelete) {
            DeleteRecord();
            clearText();
            displayAllRecords();
        } else if (ae.getSource() == btnReset) {
            txtSearch.setText("");
            displayAllRecords();
        } else if (ae.getSource() == btnSearch) {
            String searchTerm = txtSearch.getText().trim();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // searchRecords(searchTerm);
        }

        if(ae.getSource() == btnView)
        {
			dispose();
		}
        }

    void InsertRecord() {
        try {
            String id = txtTrainerId.getText();
            String name = txtName.getText();
            int age = Integer.parseInt(cbAge.getSelectedItem().toString());
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String skill = txtSkill.getText();
            String salary = txtBasicPay.getText();
            String joindate = txtJoiningDate.getText();
            String qulify = txtQualification.getText();

            if (DBConnect.isValidName(name)) {
                PreparedStatement pst = conn.prepareStatement("INSERT INTO Trainer (Trainer_id, Tname, Tage, Taddress, Tmailid, Tgender, Tskill, basicpay, joindate, qualification) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, id);
                pst.setString(2, name);
                pst.setInt(3, age);
                pst.setString(4, address);
                pst.setString(5, email);
                pst.setString(6, gender);
                pst.setString(7, skill);
                pst.setString(8, salary);
                pst.setString(9, joindate);
                pst.setString(10, qulify);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Trainer added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid name. Name should only contain alphabets.", "Error", JOptionPane.ERROR_MESSAGE);
            }
             if(!DBConnect.isValidAddress(address))
								            {
												JOptionPane.showMessageDialog(this, "Invalid address","Error", JOptionPane.ERROR_MESSAGE);
							}
							  if(!DBConnect.isValidEmail(email))
												            {
																JOptionPane.showMessageDialog(this, "Invalid mailid","Error", JOptionPane.ERROR_MESSAGE);
							}

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void ModifyRecord() {
        try {
            String id = txtTrainerId.getText();
            String name = txtName.getText();
            int age = Integer.parseInt(cbAge.getSelectedItem().toString());
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String gender = rbMale.isSelected() ? "Male" : "Female";
            String skill = txtSkill.getText();
            String salary = txtBasicPay.getText();
            String joindate = txtJoiningDate.getText();
            String qulify = txtQualification.getText();
              if(!DBConnect.isValidAddress(address))
											            {
															JOptionPane.showMessageDialog(this, "Invalid address","Error", JOptionPane.ERROR_MESSAGE);
										}
										  if(!DBConnect.isValidEmail(email))
															            {
																			JOptionPane.showMessageDialog(this, "Invalid mailid","Error", JOptionPane.ERROR_MESSAGE);
							}

            PreparedStatement pst = conn.prepareStatement("UPDATE Trainer SET Tname=?, Tage=?, Taddress=?, Tmailid=?, Tgender=?, Tskill=?, basicpay=?, joindate=?, qualification=? WHERE Trainer_id=?");
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, address);
            pst.setString(4, email);
            pst.setString(5, gender);
            pst.setString(6, skill);
            pst.setString(7, salary);
            pst.setString(8, joindate);
            pst.setString(9, qulify);
            pst.setString(10, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Trainer updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void DeleteRecord() {
        try {
            String id = txtTrainerId.getText();
            PreparedStatement pst = conn.prepareStatement("DELETE FROM Trainer WHERE Trainer_id=?");
            pst.setString(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Trainer deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void displayAllRecords() {
        try {
            model.setRowCount(0); // Clear existing rows
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Trainer");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("Trainer_id"),
                        rs.getString("Tname"),
                        rs.getInt("Tage"),
                        rs.getString("Tgender"),
                        rs.getString("Tskill"),
                        rs.getString("basicpay"),
                        rs.getString("Tmailid"),
                        rs.getString("joindate")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void clearText() {
        txtTrainerId.setText("");
        txtTrainerId.setText(DBConnect.alphanum_autoId("Trainer", "Trainer_id"));
        txtName.setText("");
        cbAge.setSelectedIndex(0);
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        txtSkill.setText("");
        txtBasicPay.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtJoiningDate.setText(LocalDate.now().toString());
        txtQualification.setText("");
    }

    public static void main(String[] args) {
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new Trainer(screenSize);
    }
}
