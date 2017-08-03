/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chatexpress;

import java.io.*;
import java.net.*;import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Archit Garg
 */
public class FileReceiver extends Thread {
    Socket s;
    String server_ip;
    DataInputStream dis,dis1;
    PrintWriter pw;
     JFrame f;
    
    /*
    *parameterised constructor
    *set connection with server file transfer window
    *create file according to name and path sent by server file transfer window
    *starts the thread
    */
    public FileReceiver(String server_ip) throws IOException{
        try{
            this.server_ip=server_ip;
             s=new Socket(server_ip,2700);
             dis=new DataInputStream(s.getInputStream());
             DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            int a=JOptionPane.showConfirmDialog(f=new JFrame(),"Do you want to receive file?");
            if(a==JOptionPane.YES_OPTION){
            dout.writeInt(8);
            dout.flush();
            String s2=dis.readUTF();
            FileWriter fw=new FileWriter(s2);
            pw=new PrintWriter(fw);
            
            start();
            }
            if(a==JOptionPane.NO_OPTION){
            f.dispose();
            dout.writeInt(7);
            dout.flush();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /*
    *receive file data from server side and store it 
    */
    public void run(){
        String str="";
                try{
                    dis1=new DataInputStream(s.getInputStream());
                    
                    do{
                    
                    str=dis1.readUTF();
                    pw.println(str);
                    pw.flush();
                    
                    }while(str!=null);
                    JOptionPane.showMessageDialog(new JFrame(),"file received");
                }catch(Exception e){
                System.out.println(e);
                    }
                
    }
}
