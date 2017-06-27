
package remoteclient;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientInitiator {

    Socket sc = null;

    public static void main(String[] args){
        String server_ip = JOptionPane.showInputDialog("Please Enter Server IP");
        String server_port = JOptionPane.showInputDialog("Please Enter Server Port");
        new ClientInitiator().initialize(server_ip, Integer.parseInt(server_port));
    }

    public void initialize(String server_ip, int server_port ){

        Robot robot = null;
        Rectangle rect = null;

        try {
            System.out.println("Connecting to server......");
            sc = new Socket(server_ip, server_port);
            System.out.println("Connection Established.");

            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd=gEnv.getDefaultScreenDevice();

            Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
            rect = new Rectangle(dimen);

            robot = new Robot(gd);

            drawGUI();
            new ScreenSpyer(sc,robot,rect);
            new ServerDelegate(sc,robot);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
                e.printStackTrace();
        }
    }

    private void drawGUI() {
        JFrame jframe = new JFrame("Remote Administrator");
        JButton btn= new JButton("Terminate");
        
        jframe.setBounds(100,100,150,150);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(btn);
        btn.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
      );
      jframe.setVisible(true);
    }
}
