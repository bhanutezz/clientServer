package clientServerTCP;

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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/* CS5800_Wednesday_12:20-3PM_Group007 
 * Rajeshwari Ramavath - 700641464  
 * Jahnavi Kocharla - 700646124
 * Bhanuteja Kuppili -700641597
 * 
 * This Client class is used to communicate with MultiThreadServer class for the calculation of interest rate.
 * It contains Swing components such as JTextField, JLabel, JButton, JTextArea to display User interface as well as action listener to parse
 * input values to server as well as handles incoming values from server and display them in text area.
 */
public class Client extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//Defining Swing components 
	private JPanel contentPane;
    private JTextField annualInterestRate;
    private JTextField numberOfYears;
    private JTextField loanAmount;
    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client frame = new Client();
                    frame.setVisible(true);
                    frame.setTitle("Client");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public Client() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTextArea jta = new JTextArea();
        jta.setBounds(12, 391, 408, 199);
        contentPane.add(jta);

        JLabel annualInrstRate = new JLabel("Annual Interest Rate:");
        annualInrstRate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        annualInrstRate.setBounds(12, 36, 119, 29);
        contentPane.add(annualInrstRate);

        JLabel noOfYears = new JLabel("Number Of Years:");
        noOfYears.setFont(new Font("Tahoma", Font.PLAIN, 16));
        noOfYears.setBounds(12, 100, 119, 29);
        contentPane.add(noOfYears);

        JLabel loanAmnt = new JLabel("Loan Amount:");
        loanAmnt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        loanAmnt.setBounds(12, 154, 119, 29);
        contentPane.add(loanAmnt);

        annualInterestRate = new JTextField();
        annualInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        annualInterestRate.setBounds(233, 28, 187, 46);
        contentPane.add(annualInterestRate);
        annualInterestRate.setColumns(10);

        numberOfYears = new JTextField();
        numberOfYears.setFont(new Font("Tahoma", Font.PLAIN, 15));
        numberOfYears.setColumns(10);
        numberOfYears.setBounds(233, 92, 187, 46);
        contentPane.add(numberOfYears);

        loanAmount = new JTextField();
        loanAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
        loanAmount.setColumns(10);
        loanAmount.setBounds(233, 146, 187, 46);
        contentPane.add(loanAmount);
        
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 5555);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            jta.append(ex.toString() + '\n');
        }
        // Creating "Clear" button to clear all field values
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                annualInterestRate.setText(null);
                numberOfYears.setText(null);
                loanAmount.setText(null);
                System.out.println("testing");
            }
        });
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnClear.setBounds(143, 305, 75, 25);
        contentPane.add(btnClear);

        // Creating "Calculate Pay" button to calculate interest amount
        JButton btnCalulatePay = new JButton("Calulate Pay");
        btnCalulatePay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double annualIntRate = (double)
                            ((Double.parseDouble(annualInterestRate.getText())));
                    toServer.writeDouble(annualIntRate);
                    int noOfYrs = (int)((Double.parseDouble(numberOfYears.getText())));
                    toServer.writeInt(noOfYrs);
                    double loanAmt = (double)((Double.parseDouble(loanAmount.getText())));      
                    toServer.writeDouble(loanAmt);                    
                    // Getting payment values from server
                    double monthlyPayment = fromServer.readDouble();
                    double totalPayment = fromServer.readDouble();
                    // displaying them in text area
        	        jta.append("Annual Interest Rate: " + annualIntRate + '\n');
        	        jta.append("Number of Years: " + noOfYrs + '\n');
            	    jta.append("Loan Amount: " + loanAmt + '\n');
                    jta.append("Monthly Payment calculated is: " + monthlyPayment + '\n');
      	            jta.append("Total payment calculated is: " + totalPayment + '\n');
      	            toServer.flush();
                }
                catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        });
        btnCalulatePay.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCalulatePay.setBounds(12, 305, 119, 25);
        contentPane.add(btnCalulatePay);      

    }
}