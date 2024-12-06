import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 import java.sql.*;



class GymSignin extends JFrame implements ActionListener
{
JLabel lbl_unm, lbl_pass, lbl_cap1,lbl_cap2, image1;
JTextField txt_unm, txt_cap;
JPasswordField txt_pass;
JButton btn_login, btn_pass, btn_forgot,btn_cancel,btn_acc;
JPanel jp;
String captcha;
GymSignin() {

// Get screen dimensions to set image on full screen
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
int screenWidth = screenSize.width;
int screenHeight = screenSize.height;

jp = new JPanel();
jp.setLayout(null);
lbl_unm = new JLabel("Username :");
lbl_unm.setBounds(10, 10, 100, 30);
jp.add(lbl_unm);

lbl_pass = new JLabel("Password :");
lbl_pass.setBounds(10,50, 100, 30);
jp.add(lbl_pass);

lbl_cap1 = new JLabel("Captcha :");
lbl_cap1.setBounds(10,100, 100, 30);
jp.add(lbl_cap1);

// Generate random characters
captcha = DBConnect.generateCaptcha(8);

lbl_cap2 = new JLabel(captcha);
lbl_cap2.setFont(new Font("Arial", Font.BOLD, 22));
lbl_cap2.setBounds(50, 140, 200, 30);
jp.add(lbl_cap2);


txt_unm = new JTextField();
txt_unm .setBounds(120, 10, 200, 30);
jp.add(txt_unm);

txt_pass = new JPasswordField();
txt_pass.setBounds(120,50, 200, 30);
jp.add(txt_pass);

txt_cap = new JTextField();
txt_cap.setBounds(120,100, 200, 30);
jp.add(txt_cap);

btn_login = new JButton("Login");
btn_login.setBounds(50,200, 100, 40);
btn_login.addActionListener(this);
jp.add(btn_login);

btn_cancel = new JButton("Cancel");
btn_cancel.setBounds(190,200, 100, 40);
btn_cancel.addActionListener(this);
jp.add(btn_cancel);

btn_forgot = new JButton("Forget Password?");
btn_forgot.setBounds(90,250, 180, 30);
btn_forgot.addActionListener(this);
jp.add(btn_forgot);

btn_acc = new JButton("New Registration");
btn_acc.setBounds(90,290, 180, 30);
btn_acc.addActionListener(this);
jp.add(btn_acc);

add(jp);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setTitle("SignIn Window");
//setReSizeable(false);
setSize(400,380);
setLocation((screenWidth/2)-200,(screenHeight/2)-250);
 setResizable(false);
setVisible(true);

}

boolean isCheckLogin(String unm,String pas)
{
	boolean flag=false;
	Statement statement;
	ResultSet result;

   Connection conn=DBConnect.setConnection();
   String sql = "SELECT uname,password FROM user_log where uname = '"+ unm +"'";
   try{
   	   statement = conn.createStatement();
   	   result = statement.executeQuery(sql);
   	   while (result.next())
   	   {
   	     String uname = result.getString(1);
         String pass = result.getString(2);
         System.out.println(uname +"   "+pass);
         if(uname.equals(unm))
          {
			if(pass.equals(pas))
			    flag = true;
			else
			    flag = false;
	      }
	     else
	         flag = false;
       }
   }catch (SQLException ex) {
		 			  ex.printStackTrace();
	}
   DBConnect.closeConnection();
   System.out.println(flag);
   return(flag);
}



public void actionPerformed(ActionEvent ae)
{
   if(ae.getSource()==btn_login)
     {
      String unm = txt_unm.getText();
      String pas = txt_pass.getText();
      String cap = txt_cap.getText();
      System.out.println(cap);
      if(!DBConnect.isValidUsername(unm) || unm==null)
       {
		   JOptionPane.showMessageDialog(this, "Username must be valid");
		   txt_unm.setText("");
       }
     if(!DBConnect.isValidPassword(pas) || pas==null)
       {
		   JOptionPane.showMessageDialog(this, "Password must be valid");
           txt_pass.setText("");
	   }
     if(captcha.equals(cap)&& cap!=null)
      {
		  boolean flag =  isCheckLogin(unm,pas);
		  if(flag)
		  {
			JOptionPane.showMessageDialog(this, "Login Successful");
		    this.dispose();
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            new GymAdmin(screenSize);
		  }
	      else
		  {
			JOptionPane.showMessageDialog(this, "Login Not Successful");
		 }
	 }
	 else{
	       JOptionPane.showMessageDialog(this, "Capcha must be valid and entered");
           txt_cap.setText("");
     }
   }

 if(ae.getSource()==btn_cancel)
     {
		  System.exit(0);
     }

 if(ae.getSource()==btn_forgot)
     {
       this.dispose();
       new GymForgotPass();
   }
   if(ae.getSource()==btn_acc)
        {
           this.dispose();
           new GymSignup();
   }
}

public static void main(String[] args)
{
 GymSignin frame = new GymSignin();
}
}
