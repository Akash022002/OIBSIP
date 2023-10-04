import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login {

    private JFrame frame;
    private JTextField t1;
    private JPasswordField t2;
    
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/akash";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Akash@2002";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login window = new Login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Login() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(224, 255, 255));
        frame.getContentPane().setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
        frame.setBounds(100, 100, 723, 491);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Login Form");
        lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 24));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBackground(new Color(153, 50, 204));
        lblNewLabel.setBounds(207, 43, 229, 40);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel lblUsername = new JLabel("USERNAME");
        lblUsername.setForeground(new Color(220, 20, 60));
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
        lblUsername.setBackground(new Color(153, 50, 204));
        lblUsername.setBounds(81, 135, 229, 40);
        frame.getContentPane().add(lblUsername);
        
        JLabel lblPassword = new JLabel("PASSWORD");
        lblPassword.setForeground(new Color(220, 20, 60));
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
        lblPassword.setBackground(new Color(153, 50, 204));
        lblPassword.setBounds(81, 198, 229, 40);
        frame.getContentPane().add(lblPassword);
        
        t1 = new JTextField();
        t1.setForeground(new Color(127, 255, 0));
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
        t1.setBounds(351, 134, 204, 40);
        frame.getContentPane().add(t1);
        t1.setColumns(10);
        
        
        JButton b1 = new JButton("Login");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = t1.getText();
                String password = new String(t2.getPassword());
                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successfully....");
                    System.out.println(""+username+"\t"+password);
                    t1.setText("");
                    t2.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to login");
                    t1.setText("");
                    t2.setText("");
                }
            }
        });
        b1.setBackground(new Color(123, 104, 238));
        b1.setForeground(new Color(0, 128, 0));
        b1.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
        b1.setBounds(270, 275, 142, 40);
        frame.getContentPane().add(b1);
        
        t2 = new JPasswordField();
        t2.setForeground(new Color(127, 255, 0));
        t2.setHorizontalAlignment(SwingConstants.CENTER);
        t2.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
        t2.setBounds(351, 195, 204, 43);
        frame.getContentPane().add(t2);
        
        JButton b2 = new JButton("Register");
        b2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Register r1 = new Register();
        		r1.frame.setVisible(true);
        		frame.setVisible(false);
        	}
        });
        b2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        b2.setForeground(new Color(128, 0, 0));
        b2.setBounds(280, 325, 117, 26);
        frame.getContentPane().add(b2);
    }
    
    // Validate login credentials by querying the database
    private boolean validateLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isValid = false;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                isValid = true;
                System.out.println("username: "+rs.getString(1));
            	System.out.println("Password: "+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return isValid;
    }
}
