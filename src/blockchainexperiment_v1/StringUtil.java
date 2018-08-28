/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.security.MessageDigest;
import java.util.Base64;
import org.opencv.core.Mat;

/**
 *
 * @author Cameron
 */
public class StringUtil {
    //Converts data stored into bytes into its Base64 representation
    public static String byteToBase64(byte[] input){
        String result = Base64.getEncoder().encodeToString(input);
        return result;
    }
    
    //Converts a Mat object into its Base64 representation
    public static String matToBase64(Mat input){
        if(input.type() == 6){
            System.out.println("Converting colour moment");
            input.convertTo(input, 0);
        }
        int length = (int) (input.total() * input.elemSize());
        byte buffer[] = new byte[length];
        input.get(0, 0, buffer);
        return byteToBase64(buffer);
    }
    
    //Converts a Mat object into its Hex representation
    public static String matToHex(Mat input){
        if(input == null) return "";
        
        if(input.type() == 6)
            input.convertTo(input, 0);
        
        int length = (int) (input.total() * input.elemSize());
        byte buffer[] = new byte[length];
        input.get(0, 0, buffer);
        return byteToHex(buffer);
    }
    
    
    //Converts data stored into bytes into its hex representation
    public static String byteToHex(byte[] input) {
        if(input == null) return "";
        
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            String hex = Integer.toHexString(0xff & input[i]);
        if(hex.length() == 1) output.append('0');
            output.append(hex);
        }
        return output.toString();
    }
    
    //converts a string into a SHA256 hash
    public static byte[] StringToSha256(String input){
        try {
            //Converts the input to SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            return hash;
            
        }   
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
//    public static String byteToString(byte[] input){
//        if(input == null)
//            return "N/A";
//        
//        StringBuilder output = new StringBuilder();
//            
//        for (int i = 0; i < input.length; i++) {
//            String hex = Integer.toHexString(0xff & input[i]);
//            if(hex.length() == 1) 
//                output.append('0');
//		output.append(hex);
//            }
//        return output.toString();
//    }
}
