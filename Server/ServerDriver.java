package Server;

import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ServerDriver extends Thread {

    Robot robot = null;
    boolean loop = true;
    Socket socket = null;

    /**
     * 
     * Constructor used to initialize socket
     * robot and start the thread.
     * 
     */
    public ServerDriver(Socket socket, Robot robot) {
        this.socket = socket;
        this.robot = robot;
        start();
    }

    /**
     * 
     * Thread used to start InStream 
     * and drive the server according 
     * to the commands given by the
     * client.
     * 
     */
    @Override
    public void run() {
        Scanner scanner = null;
        try {
            System.out.println("Starting InStream");
            scanner = new Scanner(socket.getInputStream());

            while (loop) {
                System.out.println("Waiting...");
                int command = scanner.nextInt();
                System.out.println("New command: " + command);
                switch (command) {
                    case 1:
                        robot.mousePress(scanner.nextInt());
                        break;
                    case 2:
                        robot.mouseRelease(scanner.nextInt());
                        break;
                    case 3:
                        robot.keyPress(scanner.nextInt());
                        break;
                    case 4:
                        robot.keyRelease(scanner.nextInt());
                        break;
                    case 5:
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
