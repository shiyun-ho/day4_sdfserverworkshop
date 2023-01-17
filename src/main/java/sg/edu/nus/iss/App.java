package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.plaf.synth.SynthDesktopIconUI;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String dirPath = "data2";
        String fileName = "";

        // instantiate a file/directory object
        File newDirectory = new File(dirPath);

        // if directory exists, print to console 'directory exists' message
        // else create the directory
        if (newDirectory.exists()) {
            System.out.println("Directory already exists");
        } else {
            newDirectory.mkdir();
        }

        //read cookie file

        Cookie cookie = new Cookie();
        cookie.readCookieFile();
        cookie.showCookies();

        //establish server socket

        ServerSocket ss = new ServerSocket(7000); 
        Socket s = ss.accept(); //establish connection and waiting for client to connect
        

        //input stream is at the server, we define a stream which allows an input
        try (InputStream is = s.getInputStream()) {
            //needs a buffer from input stream
            BufferedInputStream bis = new BufferedInputStream(is); 
            //data input stream to read 
            DataInputStream dis = new DataInputStream(bis);
            String msgReceived = "";

            try(OutputStream os = s.getOutputStream()){
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos); 
                
                //wait for data
                while (!msgReceived.equals("close")){
                    //when msg is received, read  as utf 
                    msgReceived = dis.readUTF(); //reads utf
                    //clients receive get cookie
                    //if it is get cookie, return cookie value
                    if (msgReceived.equalsIgnoreCase("get-cookie")){
                        String cookieValue = cookie.returnCookie();
                        System.out.println(cookieValue); 
                        
                        dos.writeUTF(cookieValue);
                        dos.flush();
                    }

                    

                }
            
                dos.close();
                bos.close();
                os.close();

            
        } catch (EOFException ex){
            s.close(); //close the second
            ss.close(); //closes the first server socket 
        }

    }
}}
