package remoteclient;

import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ServerDelegate extends Thread {

    Socket socket = null;
    Robot robot = null;
    boolean continueLoop = true;

    public ServerDelegate(Socket socket, Robot robot) {
        this.socket = socket;
        this.robot = robot;
        start();
    }

    public void run(){
        Scanner sc = null;
        try {
            System.out.println("Preparing Input Stream..");
            sc = new Scanner(socket.getInputStream());

            while(continueLoop){
                System.out.println("Waiting for Command");
                int command = sc.nextInt();
                System.out.println("New command: " + command);
                switch(command){
                    case -1:
                        robot.mousePress(sc.nextInt());
                    break;
                    case -2:
                        robot.mouseRelease(sc.nextInt());
                    break;
                    case -3:
                        robot.keyPress(sc.nextInt());
                    break;
                    case -4:
                        robot.keyRelease(sc.nextInt());
                    break;
                    case -5:
                        robot.mouseMove(sc.nextInt(), sc.nextInt());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}