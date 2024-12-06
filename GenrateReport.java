import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;

 /*class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}*/

 public class GenrateReport {

    public static void main(String[] args) {
        try (//Connection connection = DatabaseConnection.getConnection();
            Connection conn = DBConnect.setConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM RPayment");
             FileWriter writer = new FileWriter("report.doc")) {

            while (resultSet.next()) {
				writer.write("Reciept_no\tMember_id\tPackage_id\tpay_mode\tpay_Date\tAmount\n");
                writer.write( resultSet.getString(1) + "\t");
                writer.write( resultSet.getString(2) + "\t");
                writer.write( resultSet.getString(3) + "\t");
                writer.write( resultSet.getString(4) + "\t");
                writer.write( resultSet.getString(5) + "\t");
                writer.write(resultSet.getString(6) + "\n");
                // Add more columns as needed
                writer.write("\n");
            }
            System.out.println("Report generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





