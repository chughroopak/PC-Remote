/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chatexpress;

import Chatexpress.CDrawGUI;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Archit Garg
 */
public class CDrawInit extends Thread{
    String server_ip = null; 
    CDrawGUI dg;
    
    /*
    *starts the thread
    */
    public CDrawInit(String server_ip) {
        this.server_ip=server_ip;
        start();
        }
    
    /*
    *opens client chat window
    *receives messages from server and display it on client chat room
    */
    public void run(){
            try {
                dg=new CDrawGUI(server_ip);
        dg.setVisible(true);
                 do{
                     
                String smessage=dg.dis.readUTF();
                dg.jTextArea1.append("server:"+smessage+"\n");
                     
                     }while(true);
            } catch (IOException ex) {
                Logger.getLogger(CDrawInit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
    }
}
