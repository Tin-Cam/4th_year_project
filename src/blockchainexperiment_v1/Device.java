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
        for(int i = 0; i < 10; i++){
            System.out.println("I am using " + getHashName());
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getHashName(){
        return hasher.getClass().getSimpleName();
    }
    
    public void setImage(Mat input){
        image = input;
    }
}
