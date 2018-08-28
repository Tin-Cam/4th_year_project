/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import org.opencv.img_hash.ImgHashBase;

/**
 *
 * @author Cameron
 */
public class Device implements Runnable {
    
    public ImgHashBase hasher;
    private Mat image;
    
    public ArrayList<Block> blockchain = new ArrayList<>(); //The Blockchain
    private int difficulty;
    
    public Device (ImgHashBase hasher, int difficulty){
        this.hasher = hasher;
        this.difficulty = difficulty;
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
    
    
    public String getHashName(){
        return hasher.getClass().getSimpleName();
    }
    
    public void setImage(Mat input){
        image = input;
    }
    
    public Mat hashImage(){
        Mat hash = new Mat();
        hasher.compute(image, hash);
        return hash;
    }
    
    public boolean verifyBlockchain(){
        Block current;
        Block previous;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        
        for(int i = 1; i < blockchain.size(); i++){
            current = blockchain.get(i);
            previous = blockchain.get(i-1);            
            
            //Compare registered hash and calculated hash:
            if(!Arrays.equals(current.hash, current.calculateHash()) ){
               	System.out.println("Block " + i + "'s hash is incorrect");			
		return false;
            }
            
            //Compare previous hash and registered previous hash
            if(!Arrays.equals(previous.hash, current.previousHash) ) {
		System.out.println("Block " + i + " does not contain the previous hash");
		return false;
            }
            
            //Check if hash is solved
            if(!StringUtil.byteToHex(current.hash).substring( 0, difficulty).equals(hashTarget)) {
		System.out.println("Block " + i + " has not been mined");
		return false;
            }
        }
        return true;
    }
    
    public byte[] getLastHash(){
        return blockchain.get(blockchain.size() - 1).hash;
    }
}
