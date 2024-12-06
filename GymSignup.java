import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class GymSignup extends JFrame implements ActionListener {
    JLabel lbl_unm, lbl_email, lbl_pass, lbl_cap1, lbl_cap2, image1;
    JTextField txt_unm, txt_email, txt_cap;
    JPasswordField txt_pass;
    JButton btn_submit, btn_cancel;
    JPanel jp;
    String captcha;

    GymSignup()
    {
        jp = new JPanel();
        jp.setLayout(null);

        lbl_unm = new JLabel("Username :");
        lbl_unm.setBounds(10, 10, 100, 30);
        jp.add(lbl_unm);

        lbl_email = new JLabel("Email :");
        lbl_email.setBounds(10, 60, 100, 30);
        jp.add(lbl_email);

        lbl_pass = new JLabel("Password :");
        lbl_pass.setBounds(10, 110, 100, 30);
        jp.add(lbl_pass);

        lbl_cap1 = new JLabel("Captcha :");
        lbl_cap1.setBounds(10, 160, 100, 30);
        jp.add(lbl_cap1);

        // Generate random characters
        captcha = DBConnect.generateCaptcha(8);

        lbl_cap2 = new JLabel(captcha);
        lbl_cap2.setFont(new Font("Arial", Font.BOLD, 22));
        lbl_cap2.setBounds(150, 200, 200, 30);
        jp.add(lbl_cap2);

        txt_unm = new JTextField();
        txt_unm.setBounds(100,10, 200, 30);
        jp.add(txt_unm);

        txt_email = new JTextField();
        txt_email.setBounds(100,60, 200, 30);
        jp.add(txt_email);

        txt_pass = new JPasswordField();
        txt_pass.setBounds(100, 110, 200, 30);
        jp.add(txt_pass);

        txt_cap = new JTextField();
        txt_cap.setBounds(100, 160, 200, 30);
        jp.add(txt_cap);

        btn_submit = new JButton("Submit");
        btn_submit.setBounds(50, 260, 100, 30);
        btn_submit.addActionListener(this);
        jp.add(btn_submit);

        btn_cancel = new JButton("Back");
        btn_cancel.setBounds(180, 260, 100, 30);
        btn_cancel.addActionListener(this);
        jp.add(btn_cancel);

        add(jp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SignIn Window");
        setSize(350, 350);
        setLocationRelativeTo(null);
         setResizable(false);
        setVisible(true);
    }
   //To insert record in a table
    void InsertRecord(String unm,String pas,String email)
    {
		String sql = "INSERT INTO user_log(uname,password,email) VALUES(?, ?, ?)";
        try{
			Connection conn = DBConnect.setConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,unm);
			statement.setString(2, pas);
			statement.setString(3,email);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0)
			{
					System.out.println("Record inserted successfully!");
			}
		  }catch (SQLException ex) {
				  ex.printStackTrace();
	        }
	        DBConnect.closeConnection();
	}

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == btn_submit)
        {
			String unm = txt_unm.getText();
			String email = txt_email.getText();
            String pas = txt_pass.getText();
            String cap = txt_cap.getText();

            if(!DBConnect.isValidUsername(unm) || unm==null)
			{
			   JOptionPane.showMessageDialog(this, "Username must be valid");
			   txt_unm.setText("");
			}
			if(!DBConnect.isValidPassword(pas) || pas==null )
			{
			   JOptionPane.showMessageDialog(this, "Entered Neqw Password must be valid");
			   txt_pass.setText("");
			}

           if(!DBConnect.isValidEmail(email)|| email==null)
			{
			   JOptionPane.showMessageDialog(this, "Entered New Password must be valid");
			   txt_email.setText("");
			}
           if (captcha.equals(cap)|| cap!=null)
           {
			    InsertRecord(unm,pas,email);
                JOptionPane.showMessageDialog(this, "You are Registered");
                this.dispose();
                new GymSignin();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "captcha Error Occurred! Try Again");
            }
         }
        if (ae.getSource() == btn_cancel)
        {
            this.dispose();
            new GymSignin();
        }
    }
/*
    public static void main(String[] args) {
        new GymSignup();
    }*/
}
