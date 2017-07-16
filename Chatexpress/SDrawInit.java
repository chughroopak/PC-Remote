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
    SDrawGUI dg;
    
    public SDrawInit() {
        start();
    }
    
    public void run(){
         try {
            dg=new SDrawGUI();
        dg.setVisible(true);
         
             do{
                
               dg.cmessage=dg.dis.readUTF();
                dg.jTextArea1.append("client:"+dg.cmessage+"\n");
                 
                 }while(true);
            } catch (IOException ex) {
                Logger.getLogger(CDrawInit.class.getName()).log(Level.SEVERE, null, ex);
            }
           
    }
    
}
