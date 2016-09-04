package test;


import java.io.*;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// Client Class- testing git checkins in local
public class Client{
	
	
	JTextField tf1;
	JTextField tf2;
	JTextField tf3;
	JButton submit=new JButton("submit");
	JTextArea jta=new JTextArea();
	private DataOutputStream toServer;
	private DataInputStream fromServer;

	public static void main(String args[])
	{
new Client();
	}
		public Client()
		{
	JFrame hs=new JFrame();

		hs.setSize(400,200);
		hs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hs.setTitle("client");
		hs.setLayout(new FlowLayout());
		
		JLabel inr =new JLabel("enter interest rate   ");
		hs.add(inr);
		 tf1=new JTextField(20);
		hs.add(tf1);
		
		JLabel time =new JLabel("enter time                  ");
		hs.add(time);
		 tf2=new JTextField(20);
		hs.add(tf2);
		JLabel amt =new JLabel("enter actual amount");
		hs.add(amt);
		 tf3=new JTextField(20);
		hs.add(tf3);
		
		JButton submit=new JButton("submit");
		hs.add(submit);
		submit.addActionListener(new ButtonListener());
		hs.add(jta);
		
		hs.setVisible(true);
		try
		{
			Socket socket = new Socket("127.0.0.1", 2222);
		
		fromServer = new DataInputStream(
		        socket.getInputStream());
		toServer =
		        new DataOutputStream(socket.getOutputStream());
		
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	
	}
		
	

public  class ButtonListener implements ActionListener
{
		public void actionPerformed(ActionEvent ae)
		{
		
		try
		{
		
		Double inrrate=Double.parseDouble(tf1.getText().trim());
		Double years=Double.parseDouble(tf2.getText().trim());
		Double totamt=Double.parseDouble(tf3.getText().trim());
		 toServer.writeDouble(inrrate);
	        toServer.flush();
	        toServer.writeDouble(years);
	        toServer.flush();
	        toServer.writeDouble(totamt);
	        toServer.flush();
	        double monthlypayment = fromServer.readDouble();
	        double totalamount = fromServer.readDouble();
	        

	        //Display to the text area
	        jta.append("total sum is " + totamt + "\t");
	        jta.append("total rate is " + inrrate + "\t");
	        jta.append("total time is " + years + "\n");
	         jta.append("Monthly Payment  "+ monthlypayment +'\n');
	         jta.append("Total Payment  "+ totalamount + '\n');
		
	      
		}
		
		
	     catch(IOException ex) {
	         System.err.println(ex);
	        }
		}
		

		
		}

		
		}
		
		


		
	
	

