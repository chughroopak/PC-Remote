/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chatexpress;

import Chatexpress.SDrawGUI;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Archit garg
 */
public class SDrawInit extends Thread{ 
    String server_ip;
    SDrawGUI dg;
    
    public SDrawInit(String server_ip) {
        this.server_ip=server_ip;
        dg=new SDrawGUI(server_ip);
        dg.setVisible(true);
        start();
    }
    
    public void run(){
         try {
             
             do{
                
               dg.cmessage=dg.dis.readUTF();
                dg.jTextArea1.append("client:"+dg.cmessage+"\n");
                 
                 }while(true);
            } catch (IOException ex) {
                Logger.getLogger(CDrawInit.class.getName()).log(Level.SEVERE, null, ex);
            }
           
    }
    
}
