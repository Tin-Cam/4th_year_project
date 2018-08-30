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
        testFile.write("Experiment ");
        testFile.flush();
    }
    
    public static void writeTest(){
        
    }
    
    private static String getTimeStamp(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd-HHmmss");
        String result = format.format(new Date());
        return result;
    }
}
