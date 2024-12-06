import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JOptionPane;

class GymAdmin extends JFrame implements ActionListener {
    JButton btn_member, btn_Trainer, btn_package, btn_attendance, btn_payment, btn_logout;
    JLabel Lb1_welcome;
    JPanel jp;

    GymAdmin(Dimension screenSize) {
        jp = new JPanel();
        jp.setLayout(null);

        int frameWidth = (int) screenSize.getWidth();
        int frameHeight = (int) screenSize.getHeight();


        ImageIcon i1 = new ImageIcon("Banner3.png");
        Image img1 = i1.getImage();
        Image resizedImg1 = img1.getScaledInstance(frameWidth, 250, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);
        JLabel image1 = new JLabel(resizedIcon1);
        image1.setBounds(0, 0, frameWidth, 250);
        jp.add(image1);


        Lb1_welcome = new JLabel("Welcome to Gym Management System");
        Lb1_welcome.setBounds(frameWidth / 2 - 150, 220, 300, 30);
        jp.add(Lb1_welcome);


        ImageIcon ic1 = new ImageIcon("members.png");
        Image icm1 = ic1.getImage();
        Image resizedIcm1 = icm1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon1 = new ImageIcon(resizedIcm1);
        btn_member = new JButton("Members", icon1);
        btn_member.setBounds(frameWidth / 4 - 50, 300, 200, 200);
        btn_member.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_member.setHorizontalTextPosition(SwingConstants.CENTER);
        jp.add(btn_member);
        btn_member.addActionListener(this);


        ImageIcon ic2 = new ImageIcon("Trainer.png");
        Image icm2 = ic2.getImage();
        Image resizedIcm2 = icm2.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(resizedIcm2);
        btn_Trainer = new JButton("Trainers", icon2);
        btn_Trainer.setBounds(frameWidth / 2 - 50, 300, 200, 200);
        btn_Trainer.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_Trainer.setHorizontalTextPosition(SwingConstants.CENTER);
        jp.add(btn_Trainer);
         btn_Trainer.addActionListener(this);


        ImageIcon ic3 = new ImageIcon("package.png");
        Image icm3 = ic3.getImage();
        Image resizedIcm3 = icm3.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon3 = new ImageIcon(resizedIcm3);
        btn_package = new JButton("Package", icon3);
        btn_package.setBounds(3 * frameWidth / 4 - 50, 300, 200, 200);
        btn_package.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_package.setHorizontalTextPosition(SwingConstants.CENTER);
        jp.add(btn_package);
         btn_package.addActionListener(this);


        ImageIcon ic4 = new ImageIcon("attendance.png");
        Image icm4 = ic4.getImage();
        Image resizedIcm4 = icm4.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon4 = new ImageIcon(resizedIcm4);
        btn_attendance = new JButton("Attendance", icon4);
        btn_attendance.setBounds(frameWidth / 4 - 50, 600, 200, 200);
        btn_attendance.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_attendance.setHorizontalTextPosition(SwingConstants.CENTER);
        jp.add(btn_attendance);
          btn_attendance.addActionListener(this);

        ImageIcon ic5 = new ImageIcon("payments.png");
        Image icm5 = ic5.getImage();
        Image resizedIcm5 = icm5.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon5 = new ImageIcon(resizedIcm5);
        btn_payment = new JButton("Payment", icon5);
        btn_payment.setBounds(frameWidth / 2 - 50, 600, 200, 200);
        btn_payment.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_payment.setHorizontalTextPosition(SwingConstants.CENTER);
        jp.add(btn_payment);
        btn_payment.addActionListener(this);


        ImageIcon ic6 = new ImageIcon("logout.png");
        Image icm6 = ic6.getImage();
        Image resizedIcm6 = icm6.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon icon6 = new ImageIcon(resizedIcm6);
        btn_logout = new JButton("Logout", icon6);
        btn_logout.setBounds(3 * frameWidth / 4 - 50, 600, 200, 200);
        btn_logout.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_logout.setHorizontalTextPosition(SwingConstants.CENTER);
        jp.add(btn_logout);
        btn_logout.addActionListener(this);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        add(jp);
        schedule();

        setTitle("Admin Panel");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {


          if (ae.getSource() == btn_member) {

			   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new GymReg(screenSize);


		        }
		              if (ae.getSource() == btn_Trainer)
						          {
	       						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                                new Trainer(screenSize);

				  }

		          if (ae.getSource() == btn_package)
		          {
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    new Packagecurd(screenSize);
				  }

				  if(ae.getSource()== btn_attendance)
				  {
					 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		            new Attendance(screenSize);
				  }

				  if(ae.getSource()==btn_payment)
				  {
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    new GymPay(screenSize);

				  }

				if (ae.getSource() == btn_logout) {

				              System.exit(0);
        }




    }
    public void schedule()
    {
		LocalDate currentDate =LocalDate.now();
		String dayName = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		System.out.println("Day name: "+dayName);
		if(dayName.equals("Sunday"))
		JOptionPane.showMessageDialog(null,"Enter schedule of trainer");
	}

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new GymAdmin(screenSize);
    }
}
