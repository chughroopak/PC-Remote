package Server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;

class ScreenSpyer extends Thread {

    Socket socket = null;
    Robot robot = null;
    Rectangle rectangle = null;
    boolean continueLoop = true;

    public ScreenSpyer(Socket socket, Robot robot, Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

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
            BufferedImage image = robot.createScreenCapture(rectangle);
            ImageIcon imageIcon = new ImageIcon(image);
                System.out.println("before sending image");
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
