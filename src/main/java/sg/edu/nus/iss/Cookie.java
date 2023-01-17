package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    String dirPath = "\\data2";
    //instantiate a file/ directory object
    String fileName = "cookie.txt";


    //read to memory variable
    List<String> cookieItems = null;

    public void readCookieFile() throws FileNotFoundException, IOException {
        //isntantiate cookie
        cookieItems = new ArrayList<String>();
        
        File file = new File(dirPath + File.separator + fileName);

        //reader to read the file
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String readString; 

        try {
            while((readString = br.readLine()) != null){
                cookieItems.add(readString);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //closes buffered reader and file reader
            br.close();
            fr.close();
        }
    
    }

    public String returnCookie(){
        Random rand = new Random();

        //gets the next cookie
        if (cookieItems != null){
            return cookieItems.get(rand.nextInt(cookieItems.size()));
        } else {
            return "There is no cookie found!";
        }

    }

    public void showCookies(){
        if (cookieItems != null){
            //prints all cookie items
            // cookieItems.forEach(ci -> System.out.println(ci););
            for (String s: cookieItems){
                System.out.println(s);
            }
        } else {
            System.out.println("There are no cookies here!");
        }
    }
}
