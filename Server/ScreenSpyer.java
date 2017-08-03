package Server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;

/**
*	
*	This class is used to capture
*	screenshot of server screen and 
*	send them to client.
*	
*/

class ScreenSpyer extends Thread {

    Socket socket = null;
    Robot robot = null;
    Rectangle rectangle = null;
    boolean continueLoop = true;
    /**
    *
    *   Constructor to initialize 
    *   socket, robot rectangle
    *   and start the thread.
    *
    */
    public ScreenSpyer(Socket socket, Robot robot, Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

    /**
     * 
     * Thread used to send screenshot
     * of server to the client after
     * every 100ms.
     *
     */
    @Override
    public void run() {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(rectangle);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try{
            while (continueLoop) {
            BufferedImage image = robot.createScreenCapture(rectangle); // Take Screenshot
            ImageIcon imageIcon = new ImageIcon(image);
                System.out.println("Before sending image");
                oos.writeObject(imageIcon);
                oos.reset();
                System.out.println("New screenshot sent");
                Thread.sleep(100);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}