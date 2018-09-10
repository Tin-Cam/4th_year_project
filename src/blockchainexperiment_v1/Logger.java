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
    
    public static String testName = "";
    public static int testNumber = 1;
    private static OutputStreamWriter testFile;
    
    public static void initialise() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        
        testFile = new OutputStreamWriter(new FileOutputStream("experiment_logs/" + getTimeStamp()+ ".txt"), "UTF-8");
        testFile.write("		|Image Name	|Most Similar	|Best Result	|Average	|PHash		|Block Mean	|Color Moment	|Marr Hildreth	|Radial Variance");
        newLine();
        writeBlank();
        testFile.flush();
    }
    
    public static void writeTest(String image, String mostSimilar, double av) throws IOException{
        testFile.write("Test " + testNumber + "\t\t|" + image + "\t|" + mostSimilar + "\t|" + av + "\t\t|" + av + "\t\t|" + av + "\t\t|" + av + "\t\t|" + av + "\t\t|" + av + "\t\t|" + av + "\t\t");
        newLine();
        writeBlank();
        testNumber++;
        testFile.flush();
    }
    
    private static String getTimeStamp(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd-HHmmss");
        String result = format.format(new Date());
        return result;
    }
    
    private static void writeBlank() throws IOException{
        testFile.write("________________|_______________|_______________|_______________|_______________|_______________|_______________|_______________|_______________|_______________");
        newLine();
    }
    
    private static void newLine() throws IOException{
        testFile.write(System.getProperty("line.separator"));
    }
}
