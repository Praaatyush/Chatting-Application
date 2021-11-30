import java.awt.event.*;
import java.net.ServerSocket;
import javax.swing.*;
import java.io.*;
import javax.swing.border.*;
import java.net.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.Calendar;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class server extends JFrame implements ActionListener {

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f1 = new JFrame ();

    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
    Boolean typing ;

    server() {

        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(54, 120, 200));
        p1.setBounds(0, 0, 450, 50);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(4, 12, 25, 25);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/2.png"));
        Image i5 = i4.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(32, 3, 45, 45);
        p1.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));
        Image i8 = i7.getImage().getScaledInstance(10, 20, Image.SCALE_AREA_AVERAGING);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l6 = new JLabel(i9);
        l6.setBounds(360, 12, 10, 20);
        p1.add(l6);

        JLabel l3 = new JLabel("Server");
        l3.setFont(new Font("SAN_SERIT", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(85, 0, 140, 28);
        p1.add(l3);

        JLabel l4 = new JLabel("Active now");
        l4.setFont(new Font("SAN_SERIT", Font.PLAIN, 12));
        l4.setForeground(Color.WHITE);
        l4.setBounds(85, 20, 140, 28);
        p1.add(l4);
        
        Timer t = new Timer(1,new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText("Active now");
                }
            }
        });
        
        t.setInitialDelay(2000);

        t1 = new JTextField();
        t1.setBounds(5, 500, 320, 35);
        t1.setFont(new Font("SAN_SERIT", Font.PLAIN, 16));
        f1.add(t1);
        
        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("typing...");
                t.stop();
                typing = true;
            }
            public void keyReleased(KeyEvent ke){
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(330, 500, 65, 35);
        b1.setBackground(new Color(54, 120, 200));
        b1.setFont(new Font("SAN_SERIT", Font.PLAIN, 12));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        f1.add(b1);

        a1 = new JPanel();
//        a1.setBounds(5, 55, 389, 438);
        a1.setFont(new Font("SAN_SERIT", Font.PLAIN, 16));       
//        a1.setBackground(Color.WHITE);
//        f1.add(a1);
        
        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(5, 55, 389, 438);
        sp.setBorder(BorderFactory.createEmptyBorder());
        f1.add(sp);
      
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(400, 550);
        f1.setLocation(100, 50);
        f1.setUndecorated(true);
        f1.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = t1.getText();
            
            sendTextToFile(out);
            
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));
            a1.add(vertical, BorderLayout.PAGE_START);
            
//            a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
            
        } catch (Exception e) {

        }
    }
    // for ading text to txt file
   /* public void sendTextToFile(String msg) throws FileNotFoundException{
        try( FileWriter f = new FileWriter("chat.txt",true);
                PrintWriter p = new PrintWriter(new BufferedWriter(f));){
            p.println("Vatsal : "+ msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
    public void sendTextToFile(String msg) throws FileNotFoundException
    {
        String chatrec = t1.getText();
        try
        {
           Connection con = DBConnection.getConnection();
           String sql = "insert into chat(chat_server) values(?)";
           PreparedStatement pst = con.prepareStatement(sql);
           pst.setString(1,chatrec);
           int updatedRowCount = pst.executeUpdate();
        
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p> </html>");
        l1.setFont(new Font("Fahoma", Font.PLAIN, 15));
        l1.setBackground(new Color (250,180,180));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(10,10,10,10));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args) {
        new server().f1.setVisible(true);
        String msginput = "";

        try {
            skt = new ServerSocket(6005);
        while(true){    
            s = skt.accept();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true){
            msginput = din.readUTF();
            JPanel p2 = formatLabel(msginput);
            JPanel left = new JPanel(new BorderLayout());
            left.add(p2,BorderLayout.LINE_START);
            vertical.add(left);
            f1.validate();
            }

        }
        } catch (Exception e) {

        }

    }
}