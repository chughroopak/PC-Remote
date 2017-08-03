/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chatexpress;

import java.io.*;
import java.net.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Archit Garg
 */
public class SFileReceiver extends Thread {
    Socket s;
    String server_ip;
    DataInputStream dis,dis1;
    PrintWriter pw;
     JFrame f;
     ServerSocket ss;
 
    /**constructor
    *accept file name and its location from client
    *creates new file at server side
    */   
    public SFileReceiver() throws IOException{
        try{
            ss=new ServerSocket(2800);
            s=ss.accept();
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
    
    /**
    *used to accept file data from client
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
