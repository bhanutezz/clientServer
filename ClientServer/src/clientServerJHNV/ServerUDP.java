package clientServerJHNV;

//Server.java: The server accepts data from the client, processes it
//and returns the result back to the client

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerUDP extends JFrame {

  DatagramSocket socket = null;

 //Text area for displaying contents
 private JTextArea jta = new JTextArea();

 public static void main(String[] args){

 new ServerUDP();

}


public ServerUDP() {

 //Place text area on the frame
 getContentPane().setLayout(new BorderLayout());
 getContentPane().add(new JScrollPane(jta),BorderLayout.CENTER);

 setTitle("Server");
 setSize(500,300);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setVisible(true); //It is necessary to show the frame
 
 try{

 //Create a server socket
 socket = new DatagramSocket(9876);
 jta.append("Server started at" +new Date()+ '\n');

byte[] incomingData1 = new byte [1024];

 while (true) {
              DatagramPacket incomingPacket = new DatagramPacket(incomingData1, incomingData1.length);	
              socket.receive(incomingPacket);
              byte[] data1 = incomingPacket.getData();

              ByteArrayInputStream in1 = new ByteArrayInputStream(data1);
              ObjectInputStream is1 = new ObjectInputStream(in1);
              try {
                 Loan loan = (Loan) is1.readObject();
                 jta.append("The Annual Interest Rate received from client is "+ loan.getAnnualInterestRate()+'\n');
   jta.append("The Number Of Years received from client is "+ loan.getNumberOfYears()+'\n');
   jta.append("The Loan Amount received from client is "+ loan.getLoanAmount()+'\n');
   jta.append("The Monthly Payment is "+ loan.getMonthlyPayment()+'\n') ;
   jta.append("The Total Payment is"+ loan.getTotalPayment()+'\n');

              InetAddress IPAddress = incomingPacket.getAddress();	
              int port = incomingPacket.getPort();	
              String reply = "Thank you for the message" + loan;	
              byte[] replyBytea = reply.getBytes();	
              DatagramPacket replyPacket =	
                      new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);	
              socket.send(replyPacket);

                  }

              catch (ClassNotFoundException e) {	
                  e.printStackTrace();	
              }	
              	
          }	
      } catch (SocketException e) {

          e.printStackTrace();
	
      } catch (IOException i) {
          i.printStackTrace();
      }      
}
}

