package Server;

import Client.*;
import java.awt.Robot;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ServerDriver extends Thread {

    Robot robot = null;
    boolean loop = true;
    Socket socket = null;

    public ServerDriver(Socket socket, Robot robot) {
        this.socket = socket;
        this.robot = robot;
        start();
    }

    public void run(){
        Scanner scanner = null;
        try {
            System.out.println("Starting InStream");
            scanner = new Scanner(socket.getInputStream());

            while(loop){
                System.out.println("Waiting...");
                int command = scanner.nextInt();
                System.out.println("New command: " + command);
                switch(command){
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
