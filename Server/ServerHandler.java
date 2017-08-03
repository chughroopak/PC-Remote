package Server;

import Chatexpress.SDrawInit;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerHandler extends Thread {

    private ServerSocket sc = null;
    private Socket client = null;
    private int port;
    DataInputStream dIn;
    DataOutputStream dOut;
    boolean receive = true;
    int pass;
    String pswd;

    /**
     * 
     * Constructor used to initialize
     * pass and port and start the
     * thread.
     * 
     */
    
    public ServerHandler(int port, int pass) {
        this.port = port;
        this.pass = pass;
        start();
    }
    /**
     * 
     * Thread used to receive and 
     * authenticate the random password
     * generated at server.
     * 
     */
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
                    dIn = new DataInputStream(client.getInputStream()); // To receive the entered password
                    dOut = new DataOutputStream(client.getOutputStream());// Send response back to authenticate password
                    byte type = dIn.readByte();
                    System.out.println("Read Byte");
                    if (type == 1) {
                        pswd = dIn.readUTF();
                        System.out.println("Password Recieved:"+pswd);
                        /**
                         * If password is correct then 
                         * code of following if statement
                         * will be executed.
                         */
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
            System.out.println("Connection Established.");
            /**
             * 
             * The screen size and resolution of the screen
             * is captured and ScreenSpyer of that
             * resolution is created.
             * 
             */
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
