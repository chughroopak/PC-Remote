package Server;

import Chatexpress.SDrawInit;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;

class ServerHandler extends Thread {

    private ServerSocket sc = null;
    private Socket client = null;
    private int port;
    DataInputStream dIn;
    DataOutputStream dOut;
    boolean receive = true;
    int pass;
    String pswd;

    public ServerHandler(int port, int pass) {
        this.port = port;
        this.pass = pass;
        start();
    }

    @Override
    public void run() {
        Robot robot = null;
        Rectangle rect = null;

        try {
            System.out.println("Connecting to server......");
            sc = new ServerSocket(port);
            int clients = 0;
            while (clients == 0) {
                client = sc.accept();
                if(client!=null){
                    System.out.println("Client Recieved!");
                    dIn = new DataInputStream(client.getInputStream());
                    dOut = new DataOutputStream(client.getOutputStream());// Send response back to authenticate
                    byte type = dIn.readByte();
                    System.out.println("Read Byte");
                    if (type == 1) {
                        pswd = dIn.readUTF();
                        System.out.println("Password Recieved:"+pswd);
                        if (pswd.equals(String.valueOf(pass))) {
                            System.out.println("Password Matched!");
                            dOut.writeByte(1);
                            dOut.flush();
                            dIn.close();
                            dOut.close();
                            client = sc.accept();
                            clients++;
                        }
                        else{
                            dOut.writeByte(2);
                            dOut.flush();
                            dIn.close();
                            dOut.close();
                            client = null;
                            clients=0;
                        }
                    }
                    
                }
            }
            // Check whether password is correct or not
            System.out.println("Connection Established.");
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = gEnv.getDefaultScreenDevice();
            Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
            rect = new Rectangle(dimen);
            robot = new Robot(gd);
            new SDrawInit();
            new ScreenSpyer(client, robot, rect);
            new ServerDriver(client, robot);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
}
