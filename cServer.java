
import javax.swing.*;
import javax.swing.text.PlainDocument;
import javax.xml.validation.Validator;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Calendar;
import java.io.*;
import javax.swing.border.*;
import java.text.SimpleDateFormat;

public class cServer implements ActionListener

{
    JPanel p1;
    JTextField t1;
    JButton b1;
    //static JtextArea a1;
    static JPanel a1;   //new
    static JFrame f1 = new JFrame();
    static Box vertical = Box.createVerticalBox();
    static ServerSocket soc;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    cServer()
    {
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(54,120,200));
        p1.setBounds(0, 0,400,70);
        f1.add(p1);

       ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/3.png"));
         Image i2 = i1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
         ImageIcon i3 = new ImageIcon(i2);
       JLabel l1 = new JLabel(i3);
       l1.setBounds(5,17,30,30);
       p1.add(l1);
       
       l1.addMouseListener(new MouseAdapter() 
       {
           public void mouseClicked(MouseEvent ae)
           {
               System.exit(0);
           }
        });

       ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/2.png"));
         Image i5 = i4.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
         ImageIcon i6 = new ImageIcon(i5);
       JLabel l2 = new JLabel(i6);
       l2.setBounds(50,5,60,60);
       p1.add(l2);

       //a1 = new JTextArea();
       //setEditable(false);
       a1 = new JPanel  ();  //new
       a1.setFont(new Font("SANS_SHERIF",Font.PLAIN,16));
       a1.setBounds(2,72,380,492);
       //a1.setBackground(Color.BLACK);
       f1.add(a1);

       JLabel l3= new JLabel("User 1");
       l3.setFont(new Font("SANS_SHERIF",Font.PLAIN,20));
       l3.setBounds(110,20,100,25);
       l3.setForeground(Color.WHITE);
       p1.add(l3);    

        t1 = new JTextField();
        t1.setBounds(5,570,320,30);
        t1.setFont(new Font("SANS_SHERIF",Font.PLAIN,16));
        f1.add(t1);
        b1= new JButton(">>>");
        b1.setFont(new Font("SANS_SHERIF",Font.PLAIN,8));
        b1.setBounds(330,570,50,30);
        b1.addActionListener(this);
        f1.add(b1);

        f1.setLayout(null);
        f1.setSize(400,650);
        f1.setLocation(400,50);
        f1.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
        String out = t1.getText();
       // a1.setText(a1.getText()+"\n"+out);
        JPanel p2 = formatLabel(out);//new
        a1.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        a1.add(vertical, BorderLayout.PAGE_START);
        //a1.add(p2);
        dout.writeUTF(out);
        t1.setText("");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
       
    }
    public static JPanel formatLabel(String out)
    {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        JLabel l1 = new JLabel(out);
        l1.setBackground(new Color(255,192,203));
        l1.setOpaque (true); 
        l1.setFont(new Font("SANS_SHERIF",Font.PLAIN,16));
        l1.setBorder(new EmptyBorder(15,15,15,50));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH:mm");
        JLabel l2= new JLabel();
        l2.setText(date.format(cal.getTime()));
        p3.add(l1);
        p3.add(l2);
        return p3;
    }
    public static void main(String[] args)
    {
        new cServer().f1.setVisible(true);
        String msginput = "";
        try
        {
            soc = new ServerSocket(6001);
            while(true)
            {
            s = soc.accept();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true)X
            {
            msginput = din.readUTF();
            JPanel p2 = formatLabel(msginput);
            JPanel left = new JPanel(new BorderLayout());
            left.add(p2,BorderLayout.LINE_START);
            vertical.add(left);
            f1.validate();
            //a1.setText(a1.getText()+"\n"+out);
            //soc.close();
            //s.close();
            }
        }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
