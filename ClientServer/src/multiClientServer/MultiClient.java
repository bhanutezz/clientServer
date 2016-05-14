package multiClientServer;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MultiClient extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;


    /**
     * Launch the application.
     */

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    public static void main(String[] args) {
        Employee no1 = new Employee();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MultiClient frame = new MultiClient();
                    frame.setVisible(true);
                    frame.setTitle("Work Hard Client");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public MultiClient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTextArea jta = new JTextArea();
        jta.setBounds(12, 391, 408, 199);
        contentPane.add(jta);

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(
                    socket.getInputStream());

            // Create an output stream to send data to the server
            toServer =
                    new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            jta.append(ex.toString() + '\n');
        }

        JLabel lblEnterMonths = new JLabel("Enter months:");
        lblEnterMonths.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEnterMonths.setBounds(12, 36, 119, 29);
        contentPane.add(lblEnterMonths);

        JLabel lblEnterDays = new JLabel("Enter days:");
        lblEnterDays.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEnterDays.setBounds(12, 100, 119, 29);
        contentPane.add(lblEnterDays);

        JLabel lblEnterPayrate = new JLabel("Enter payrate:");
        lblEnterPayrate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEnterPayrate.setBounds(12, 154, 119, 29);
        contentPane.add(lblEnterPayrate);

        JLabel lblEnterHours = new JLabel("Enter hours:");
        lblEnterHours.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEnterHours.setBounds(12, 208, 119, 29);
        contentPane.add(lblEnterHours);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.setBounds(233, 28, 187, 46);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_1.setColumns(10);
        textField_1.setBounds(233, 92, 187, 46);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_2.setColumns(10);
        textField_2.setBounds(233, 146, 187, 46);
        contentPane.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_3.setColumns(10);
        textField_3.setBounds(233, 200, 187, 46);
        contentPane.add(textField_3);

        JButton btnCalulatePay = new JButton("Calulate Pay");

        btnCalulatePay.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                try {
                    int noMonths = (int)
                            ((Double.parseDouble(textField.getText()))
                                    );
                    toServer.writeInt(noMonths);

                    int noDays = (int)
                            ((Double.parseDouble(textField_1.getText()))
                                    );
                    toServer.writeInt(noDays);

                    double payRate = (double)
                            ((Double.parseDouble(textField_2.getText()))
                                    );      
                    toServer.writeDouble(payRate);

                    double  hours = (double)
                            ((Double.parseDouble(textField_3.getText()))
                                    );
                    toServer.writeDouble(hours);

                    double sum = (noMonths*noDays)*(payRate*hours);
                    // Send the pay to the server
                    toServer.flush();
                    // Get area pay the server
                    double Pay = fromServer.readDouble();

                    jta.append("Pay is " + sum+ "\n");
                    jta.append("Pay received from the server is "
                            + sum + '\n');
                }
                catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        });
        btnCalulatePay.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCalulatePay.setBounds(12, 305, 119, 25);
        contentPane.add(btnCalulatePay);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                textField.setText(null);
                textField_1.setText(null);
                textField_2.setText(null);
                textField_3.setText(null);
            }
        });
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnClear.setBounds(143, 305, 75, 25);
        contentPane.add(btnClear);

        JButton btnHelp = new JButton("Help");
        btnHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog (null, "Message", "Title", JOptionPane.INFORMATION_MESSAGE);  

            }
        });
        btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnHelp.setBounds(233, 305, 75, 25);
        contentPane.add(btnHelp);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExit.setBounds(332, 306, 75, 25);
        contentPane.add(btnExit);

    }
}