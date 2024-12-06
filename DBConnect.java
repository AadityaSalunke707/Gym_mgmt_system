import javax.swing.JOptionPane;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.*;

public class DBConnect
 {
    public static Connection con=null;
    public static Connection setConnection()
      {

         try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Gymdb","root","root@123");
                  if (con != null) {
				  			System.out.println("Connected....");
		             }
			  }catch (SQLException s) {

                // Display message when SQLException is
                // encountered
                System.out.println("SQL statement is not executed!");
            }
            catch (Exception e) {
                          System.out.println("inter.DBConnect.connect()");
                        //  JOptionPane.showMessageDialog(null, e);
        }
       return con;
    }
	//validate entered name of trainer,member,package
	public static boolean isValidName(String name)
	{
		String regex = "^[a-zA-Z\\s]+{1,30}$";
		return name.matches(regex);
	}

	public static boolean isValidPrice(String price)
	{
		String regex="^\\d$";
		return price.matches(regex);
		}
	//validate entered address
	public static boolean isValidAddress(String addr)
	{
		String regex = "^[0-9a-zA-Z\\s,.-]+ $";
		return addr.matches(regex);
	}

	//validate entered contact number
	public static boolean isValidcontact(String cno)
	{
		String regex = "^\\d{10}$";
		return cno.matches(regex);
    }

    //validate emailid
     public static boolean isValidEmail(String email)
     {
		    String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
	        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

	//validate entered username
	public static boolean isValidUsername(String username)
	{
		// Username must be 5-15 characters long and contain only alphanumeric characters
		String regex = "^[a-zA-Z0-9]{5,15}$";
		return username.matches(regex);
	}

	//validate entered password
	public static boolean isValidPassword(String password)
	{
		String regex = "^[a-zA-Z0-9@#$]{5,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
public static void fillCombo(JComboBox<String> comboBox,String tblnm,String fldnm)
{
	try
	{
		Statement statement = con.createStatement();

		ResultSet resultSet = statement.executeQuery("SELECT "+ fldnm + " FROM " + tblnm);

		while (resultSet.next())
		{
			comboBox.addItem(resultSet.getString(fldnm));

		}

	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

}
	//autogeneration
    public static int numeric_autoId(String tblnm,String fldnm)
    {
       int id=0;
       PreparedStatement pst=null;
	   ResultSet rs=null;
       Statement statement;
    try {

		 statement = con.createStatement();
		//LAST_INSERT_ID() this is mysql function
        rs = statement.executeQuery("SELECT LAST_INSERT_ID()");

        if (rs.next()) {
              id = rs.getInt(1);
         }
       if (rs == null)
       {
           id=1;
       }
       else
         {
              id =id+1;
         }

    } catch (Exception e)
	               {
                      JOptionPane.showMessageDialog(null, e);
			   }
   return (id);
 }

  public static String alphanum_autoId(String tblnm,String fldnm)
    {
		int numPart=0;
		String lastId=null;
		PreparedStatement pst=null;
	    ResultSet rs=null;

        try {
                String sql="SELECT "+ fldnm + "  FROM " +tblnm+"  ORDER BY "+ fldnm +" DESC LIMIT 1";
                pst=con.prepareStatement(sql);
                rs=pst.executeQuery();
                if (rs.next()) {
                    lastId = rs.getString(fldnm);  //or lastId = rs.getString(1);
			    }
                if (lastId == null) {
                       lastId= tblnm.charAt(0)+"0001";
                  }
                  else
                  {
                      numPart = Integer.parseInt(lastId.substring(1));
                      numPart++;
                      lastId= tblnm.charAt(0)+ String.format("%04d", numPart);
                 }

             } catch (Exception e)
               {
                           JOptionPane.showMessageDialog(null, e);
               }
       return(lastId);
 }


//To close connection
	public static void closeConnection(){
		try{
		 con.close();
		 } catch (SQLException ex) {
		 			  ex.printStackTrace();
	}
	}


	//To delete record by book id
	public static void DeleteRecord(String tblnm,String fldnm,String id)
	{
       PreparedStatement pst=null;
	   ResultSet rs=null;

	   String sql = "DELETE FROM "+ tblnm + " WHERE "+ fldnm+"=?";
       try{
	        pst = con.prepareStatement(sql);
	        pst.setString(1,id+"");

			int rowsDeleted = pst.executeUpdate();
			if (rowsDeleted > 0)
			{
					System.out.println("Record was deleted successfully!");
			}
		 }catch (SQLException ex) {
				  ex.printStackTrace();
	     }
	}


	public static String generateCaptcha(int length) {
	        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        Random random = new Random();
	        StringBuilder captcha = new StringBuilder();

	        for (int i = 0; i < length; i++) {
	            captcha.append(chars.charAt(random.nextInt(chars.length())));
	        }

	        return captcha.toString();
	    }

}


