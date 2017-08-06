/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chatexpress;

import java.io.*;
import java.net.*;import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author Archit Garg
 */
public class FileReceiver extends Thread {
    Socket s;
    String server_ip;
    DataInputStream dis;
    String file;
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
            file = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\"+s2 ;
            start();
            JOptionPane.showMessageDialog(null,"File recieved at:+\n"+file);
            }
            if(a==JOptionPane.NO_OPTION){
            f.dispose();
            dout.writeInt(7);
            dout.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /*
    *receive file data from server side and store it 
    */
    public void run(){
        try{
            InputStream in = null;
            OutputStream out = null;
            try {
                in = s.getInputStream();
            } catch (IOException ex) {
                System.out.println("Can't get socket input stream. ");
            }

            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found.");
            }

            byte[] bytes = new byte[8192];

            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();
            s.close();
                
        }
        catch(Exception e){
            e.printStackTrace();
        }
                
    }
}
