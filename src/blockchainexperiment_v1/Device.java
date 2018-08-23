/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import org.opencv.img_hash.ImgHashBase;

/**
 *
 * @author Cameron
 */
public class Device implements Runnable {
    
    private ImgHashBase hasher;
    private Mat image;
    public ArrayList<Block> blockchain = new ArrayList<>(); //The Blockchain
    
    public Device (ImgHashBase hasher){
        this.hasher = hasher;
    }
    
    @Override
    public void run() {
        compareImages();
    }
    
    private void compareImages(){
        //Check to ensure an image has been set
        if(image == null){
            System.out.println("No image given (" + getHashName() + ")");
            return;
        }
        
        Mat hashResult = new Mat();
        hasher.compute(image, hashResult);
        System.out.println("Using " + getHashName() + "\t\tI believe the hash of the image is " + StringUtil.matToHex(hashResult));
        
        //Sets the image to null so that the same image won't accidentally be used twice
        image = null;
    }
    
    public void addBlock(Block block){
        blockchain.add(block);
        verifyBlockchain();
    }
    
    public boolean verifyBlockchain(){
        return false;
    }
    
    public String getHashName(){
        return hasher.getClass().getSimpleName();
    }
    
    public void setImage(Mat input){
        image = input;
    }
}
