import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class GymViewSchdl implements ActionListener {
    JLabel image1;
    JPanel Panel1, Panel2;
    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;
    JButton btn_back;
    Connection conn;
    JFrame frm;

    GymViewSchdl(Dimension screenSize) {
        conn = DBConnect.setConnection();  // Connect to the database

        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        frm = new JFrame("Gym Schedule Viewer");
        frm.setUndecorated(true);
        frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLayout(null);

        // Banner image setup
        ImageIcon i1 = new ImageIcon("vsBanner.png");
        Image img1 = i1.getImage();
        Image resizedImg1 = img1.getScaledInstance(screenWidth, 250, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
        image1 = new JLabel(resizedIcon1);
        image1.setBounds(0, 0, screenWidth, 250);
        frm.add(image1);

        // Panel for time slots
        Panel1 = new JPanel();
        Panel1.setLayout(null);
        Panel1.setBounds(240, 300, 100, 520);

        String[] timeLabels = {"7AM", "8AM", "9AM", "10AM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM"};
        int yPos = 10;
        for (String timeLabel : timeLabels) {
            JLabel label = new JLabel(timeLabel);
            label.setBounds(10, yPos, 80, 30);
            Panel1.add(label);
            yPos += 30;
        }
        btn_back = new JButton("Back");
        btn_back.setBounds(0, 400, 100, 40);
        btn_back.addActionListener(this);
        Panel1.add(btn_back);
        frm.add(Panel1);

        // Panel for the schedule table
        Panel2 = new JPanel();
        Panel2.setLayout(null);
        Panel2.setBounds(360, 290, 670, 330);

        // Define table model with columns for each day
        model = new DefaultTableModel();
        model.addColumn("Monday");
        model.addColumn("Tuesday");
        model.addColumn("Wednesday");
        model.addColumn("Thursday");
        model.addColumn("Friday");
        model.addColumn("Saturday");
        model.addColumn("Sunday");

        // Add empty rows corresponding to each time slot
        for (String timeLabel : timeLabels) {
            model.addRow(new Object[]{"", "", "", "", "", "", ""});
        }

        // Initialize table and scroll pane
        table = new JTable(model);
         table.setFillsViewportHeight(true);
		        table.setRowHeight(30); // Set row height for better visibility
		        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 670, 320);
        Panel2.add(scrollPane);
        frm.add(Panel2);

        // Load and display schedule data from the database
        loadScheduleData(timeLabels);

        frm.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_back) {
            frm.dispose();
        }
    }

    // Method to load schedule data from the database
    private void loadScheduleData(String[] timeLabels) {
        try {
            String query = "SELECT Trainer_id, Day, TimeSlot FROM Schedule";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String trainerId = rs.getString("Trainer_id");
                String day = rs.getString("Day");
                String timeSlot = rs.getString("TimeSlot");

                int row = findTimeSlotRow(timeSlot, timeLabels);
                int col = getDayColumn(day);

                if (row != -1 && col != -1) {
                    model.setValueAt(trainerId, row, col);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frm, "Error loading schedule data.");
        }
    }

    // Helper method to find the correct row index based on time slot
    private int findTimeSlotRow(String timeSlot, String[] timeSlots) {
        for (int i = 0; i < timeSlots.length; i++) {
            if (timeSlots[i].equalsIgnoreCase(timeSlot)) {
                return i;
            }
        }
        return -1;
    }

    // Helper method to map days of the week to table column indexes
    private int getDayColumn(String day) {
        switch (day.toLowerCase()) {
            case "monday":
                return 0;
            case "tuesday":
                return 1;
            case "wednesday":
                return 2;
            case "thursday":
                return 3;
            case "friday":
                return 4;
            case "saturday":
                return 5;
            case "sunday":
                return 6;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new GymViewSchdl(screenSize);
    }
}
