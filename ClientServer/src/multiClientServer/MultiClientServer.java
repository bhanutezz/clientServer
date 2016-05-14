package multiClientServer;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MultiClientServer extends JFrame {
  private JTextArea jta = new JTextArea();

  public static void main(String[] args) {
      Employee employee = null;
    new MultiClientServer();

  }

  public MultiClientServer() {
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);

    setTitle("Work Hard Server");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      ServerSocket serverSocket = new ServerSocket(8000);
      jta.append("MultiThreadServer started at " + new Date() + '\n');

      int clientNo = 1;

      while (true) {
        Socket socket = serverSocket.accept();

        HandleAClient task = new HandleAClient(socket);


        new Thread(task).start();

        clientNo++;
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }


  class HandleAClient implements Runnable {
    private Socket socket; 

    /** Construct a thread */
    public HandleAClient(Socket socket) {
      this.socket = socket;
    }

    /** Run a thread */
    public void run() {
      try {

        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());


        while (true) {

          int noMonths = inputFromClient.readInt();
          int noDays = inputFromClient.readInt();
          double payRate = inputFromClient.readDouble();
          double hours = inputFromClient.readDouble();


        double sum = (noMonths*noDays)*(payRate*hours);

          outputToClient.writeDouble(sum);
          jta.append("Months received from client: " +
                  noMonths + '\n');
          jta.append("Days received from client: " +
                  noDays + '\n');
          jta.append("Rate received from client: " +
                  payRate + '\n');
          jta.append("Hours received from client: " +
                  hours + '\n');

          jta.append("pay calculated and sent is: " +
                  sum + '\n');
        }
      }
      catch(IOException e) {
        System.err.println(e);
      }
    }
  }
}