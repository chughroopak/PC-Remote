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
public class SFileReceiver extends Thread {
    Socket s;
    String server_ip;
    DataInputStream dis;
    String file;
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
            file = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\"+s2 ;
            start();
            JOptionPane.showMessageDialog(null,"File recieved at:\n"+file);
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
