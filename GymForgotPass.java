import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class GymForgotPass extends JFrame implements ActionListener {
    JLabel lbl_unm, lbl_NewPass, lbl_ConfirmPass, lbl_Cap1, lbl_Cap2;
    JTextField txt_unm, txt_cap;
    JPasswordField txt_NewPass, txt_ConfirmPass;
    JButton btn_Confirm, btn_Cancel;
    JPanel jp;
    String captcha;

    public GymForgotPass() {
        jp = new JPanel();
        jp.setLayout(null);

        lbl_unm = new JLabel("Username :");
        lbl_unm.setBounds(15, 30, 100, 30);
        jp.add(lbl_unm);

        lbl_NewPass = new JLabel("New password :");
        lbl_NewPass.setBounds(15,80, 100, 30);
        jp.add(lbl_NewPass);

        lbl_ConfirmPass = new JLabel("Confirm password :");
        lbl_ConfirmPass.setBounds(15,130, 140, 30);
        jp.add(lbl_ConfirmPass);

        lbl_Cap1 = new JLabel("Captcha :");
        lbl_Cap1.setBounds(15,180, 100, 30);
        jp.add(lbl_Cap1);

        captcha = DBConnect.generateCaptcha(8);

        lbl_Cap2 = new JLabel(captcha);
        lbl_Cap2.setFont(new Font("Arial", Font.BOLD, 22));
        lbl_Cap2.setBounds(140, 230, 200, 30);
        jp.add(lbl_Cap2);

        txt_unm = new JTextField();
        txt_unm.setBounds(140,30, 200, 30);
        jp.add(txt_unm);

        txt_NewPass = new JPasswordField();
        txt_NewPass.setBounds(140, 80, 200, 30);
        jp.add(txt_NewPass);

        txt_ConfirmPass = new JPasswordField();
        txt_ConfirmPass.setBounds(140, 130, 200, 30);
        jp.add(txt_ConfirmPass);

        txt_cap = new JTextField();
        txt_cap.setBounds(140, 180, 200, 30);
        jp.add(txt_cap);

        btn_Confirm = new JButton("Confirm");
        btn_Confirm.setBounds(100, 300, 100, 30);
        btn_Confirm.addActionListener(this);
        jp.add(btn_Confirm);

        btn_Cancel = new JButton("Back");
        btn_Cancel.setBounds(250, 300, 100, 30);
        btn_Cancel.addActionListener(this);
        jp.add(btn_Cancel);

        add(jp);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Forgot Password Window");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    //to modify record by id
	void ModifyRecord(String unm,String pas)
	{
     String sql = "UPDATE user_log SET password= '"+pas+ "' WHERE uname= '"+unm +"'";
	try
	{
		 Connection conn = DBConnect.setConnection();
		 Statement stmt = conn.createStatement();
		 int rowsUpdated = stmt.executeUpdate(sql);
		 if (rowsUpdated > 0)
		 {
		     JOptionPane.showMessageDialog(this,"An existing User was updated successfully!");
		 }
	}catch (SQLException ex) {
			  ex.printStackTrace();
	}
	DBConnect.closeConnection();
	}

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == btn_Confirm)
        {
			String unm = txt_unm.getText();
			String npas = txt_NewPass.getText();
			String cpas = txt_ConfirmPass.getText();
            String cap = txt_cap.getText();
			if(!DBConnect.isValidUsername(unm) || unm==null)
			{
			   JOptionPane.showMessageDialog(this, "Username must be valid");
			   txt_unm.setText("");
			}
			if(!DBConnect.isValidPassword(npas) || npas==null )
			{
			   JOptionPane.showMessageDialog(this, "Entered Neqw Password must be valid");
			   txt_NewPass.setText("");
			}
			if((!DBConnect.isValidPassword(cpas) || cpas==null )||(npas.equals(cpas)== false))
			{
						   JOptionPane.showMessageDialog(this, "Confirmed Password must be valid and match with new password");
						   txt_ConfirmPass.setText("");
			}

			if (captcha.equals(cap) && cap!=null )
            {
				     ModifyRecord(unm,npas);
				     JOptionPane.showMessageDialog(this, "Password Reset Successful");
				     this.dispose();
                     new GymSignin();
             }
            else
            {
                JOptionPane.showMessageDialog(this, "Captcha Incorrect! Try Again");
            }
        }

        if (ae.getSource() == btn_Cancel)
        {
             this.dispose();
             new GymSignin();
        }
    }
/*
    public static void main(String[] args) {
        new GymForgotPass();
    }*/
}
