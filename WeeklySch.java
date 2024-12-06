import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class WeeklySch extends JFrame implements ActionListener {

    private JPanel panel1, panel2, panel3;
    JComboBox<String> cb_TrainerList;
    JTable table;
    JLabel image1;
    JButton btn_save, btn_cancel, btn_back;
    DefaultTableModel model;
    public Connection conn;

    WeeklySch(Dimension screenSize) {
        conn = DBConnect.setConnection();

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Add Schedule");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        ImageIcon i1 = new ImageIcon("WBanner.png");
        Image img1 = i1.getImage().getScaledInstance(screenWidth, 250, Image.SCALE_SMOOTH);
        image1 = new JLabel(new ImageIcon(img1));
        image1.setBounds(0, 0, screenWidth, 250);
        add(image1);

        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(10, 290, 300, 400);
        add(panel1);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(320, 270, 1300, 700);
        add(panel2);

        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBounds(10, 700, 300, 800);
        add(panel3);

        String[] timeSlots = {"7am", "8am", "9am", "10am", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm"};
        for (int i = 0; i < timeSlots.length; i++) {
            JLabel label = new JLabel(timeSlots[i]);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(20, i * 30, 300, 30);
            panel1.add(label);
        }

        cb_TrainerList = new JComboBox<>(new String[]{});
        cb_TrainerList.setBounds(10, 350, 280, 30);
        panel1.add(cb_TrainerList);
        DBConnect.fillCombo(cb_TrainerList, "Trainer", "Trainer_id");

        model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                Object value = getValueAt(row, column);
                return value == null || !(boolean) value;
            }
        };

        model.addColumn("Monday");
        model.addColumn("Tuesday");
        model.addColumn("Wednesday");
        model.addColumn("Thursday");
        model.addColumn("Friday");
        model.addColumn("Saturday");
        model.addColumn("Sunday");

        for (int i = 0; i < timeSlots.length; i++) {
            model.addRow(new Object[]{false, false, false, false, false, false, false});
        }

        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, (screenWidth * 3) / 4, 700);
        panel2.add(scrollPane);

        btn_save = new JButton("Save");
        btn_save.setBounds(10, 10, 250, 40);
        btn_save.addActionListener(this);
        panel3.add(btn_save);

        btn_cancel = new JButton("Cancel");
        btn_cancel.setBounds(10, 60, 250, 40);
        btn_cancel.addActionListener(this);
        panel3.add(btn_cancel);

        btn_back = new JButton("Back");
        btn_back.setBounds(10, 110, 250, 40);
        btn_back.addActionListener(this);
        panel3.add(btn_back);
    }

    void displayAllRecords() {
        try {
            model.setRowCount(0); // Clear existing rows
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Trainer");
            while (rs.next()) {
                cb_TrainerList.addItem(rs.getString("Tname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_save) {
            saveSchedule();
        } else if (e.getSource() == btn_cancel) {
            resetTable();
        } else if (e.getSource() == btn_back) {
            dispose();
        }
    }

    void resetTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                model.setValueAt(false, i, j);
            }
        }
    }

    void saveSchedule() {
        try {
            String selectedTrainer = cb_TrainerList.getSelectedItem().toString();

            String deleteQuery = "DELETE FROM Schedule WHERE Trainer_id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setString(1, selectedTrainer);
            deleteStmt.executeUpdate();

            String insertQuery = "INSERT INTO Schedule (Trainer_id, Day, TimeSlot) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);

            for (int row = 0; row < model.getRowCount(); row++) {
                String timeSlot = getTimeSlot(row);

                for (int col = 0; col < model.getColumnCount(); col++) {
                    Boolean isSelected = (Boolean) model.getValueAt(row, col);

                    if (isSelected != null && isSelected) {
                        String day = model.getColumnName(col);

                        insertStmt.setString(1, selectedTrainer);
                        insertStmt.setString(2, day);
                        insertStmt.setString(3, timeSlot);
                        insertStmt.addBatch();

                        // Clear and disable only selected checkboxes
                        model.setValueAt(false, row, col);  // Reset to unselected
                        table.isCellEditable(row, col); // Prevent further editing for disabled cells
                    }
                }
            }

            insertStmt.executeBatch();
            insertStmt.close();

            JOptionPane.showMessageDialog(this, "Schedule saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving schedule. Please try again.");
        }
    }

    String getTimeSlot(int row) {
        String[] timeSlots = {"7am", "8am", "9am", "10am", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm"};
        return timeSlots[row];
    }

    public static void main(String args[]) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WeeklySch ws = new WeeklySch(screenSize);
        ws.setVisible(true);
    }
}