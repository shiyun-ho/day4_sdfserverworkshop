package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
        Socket s = new ss.accept(); //establish connection and waiting for client to connect

        try (InputStream is = s.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is); 
            DataInputStream dis = new DataInputStream(bis);
            String msgReceived = " ";

            while (!msgReceived.equals("close")){
                msgReceived = dis.readUTF(); //reads utf 
                //clients receive get cookie

                if (msgReceived.equalsIgnoreCase("get-cookie")){
                    String cookieValue = cookie.returnCookie(); 
                }

            }
        } catch (EOFException ex){
            s.close(); //close the second
            ss.close(); //closes the first server socket 
        }

    }
}
