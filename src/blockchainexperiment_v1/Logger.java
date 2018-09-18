/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Cameron
 */
public class Logger {
    
    public static String imageName = "";
    public static int testNumber = 1;
    private static OutputStreamWriter testFile;
    
    public static void initialise(String image) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        imageName = image;
        
        testFile = new OutputStreamWriter(new FileOutputStream("experiment_logs/" + getTimeStamp()+ ".txt"), "UTF-8");
        testFile.write("Image: " + imageName);
        newLine();
        writeBlank();
        testFile.flush();
    }
    
    public static void writeTest(String image, double score, String algorithm, String result) throws IOException{
        testFile.write(image + ":");
        newLine();
        testFile.write("\tBest Score: " + score + " using " + algorithm);
        newLine();
        testFile.write("\tImage Found: " + result);
        newLine();
         
        writeBlank();
        testFile.flush();
    }
    
    
    private static String getTimeStamp(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd-HHmmss");
        String result = format.format(new Date());
        return result;
    }
    
    private static void writeBlank() throws IOException{
        testFile.write("---------------------------------------------------");
        newLine();
    }
    
    private static void newLine() throws IOException{
        testFile.write(System.getProperty("line.separator"));
    }
}
