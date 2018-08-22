/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.img_hash.AverageHash;
import org.opencv.img_hash.BlockMeanHash;
import org.opencv.img_hash.ColorMomentHash;
import org.opencv.img_hash.ImgHashBase;
import org.opencv.img_hash.MarrHildrethHash;
import org.opencv.img_hash.PHash;
import org.opencv.img_hash.RadialVarianceHash;

/**
 *
 * @author Cameron
 */
public class Block {
    
    public byte[] hash;         //The hash of this block
    public byte[] previousHash; //The hash of the previous block
    
    private final String imagePath;        //The image of the block
    public Mat hashAV;      //Average Hash
    public Mat hashPH;      //PHash Hash
    public Mat hashBM;      //Block Mean Hash
    public Mat hashCM;      //Color Moment Hash
    public Mat hashMH;      //Marr Hildreth Hash
    public Mat hashRV;      //Radial Variance Hash
    
    
    private final long timeStamp;     //Timestamp of the block
    private int nonce;
    
    //Constructor for a blank block. Used to create the genisis block
    public Block(){        
        this.imagePath = "N/A";
        this.timeStamp = new Date().getTime();
        this.nonce = 0;
        
        this.hash = calculateHash();
    }
    
    public Block(byte[] previousHash, String imagePath) throws IOException{
        this.previousHash = previousHash;
        this.imagePath = imagePath;
        this.timeStamp = new Date().getTime();
        this.nonce = 0;
        
        //this.imageHash = StringUtil.ImageToSha256(Files.readAllBytes(Paths.get(imagePath)));
        this.hash = calculateHash();
    }
    
    //Calculates the hash of the block
    public byte[] calculateHash(){
        byte[] output = StringUtil.StringToSha256(
            StringUtil.byteToHex(previousHash) + 
            Long.toString(timeStamp) +
            imagePath +
            StringUtil.matToHex(hashAV) +
            StringUtil.matToHex(hashPH) +
            StringUtil.matToHex(hashBM) +
            StringUtil.matToHex(hashCM) +
            StringUtil.matToHex(hashMH) +
            StringUtil.matToHex(hashRV) +
            Integer.toString(nonce));       
        return output;
    }
    
    //Increments nonce untill the block's hash starts with a particular amount of 0's
    public void mineBlock(int difficulty) {
        System.out.println("Mining block...");
        
	String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
	while(!StringUtil.byteToHex(hash).substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
	}
	System.out.println("Block Mined!!! : " + StringUtil.byteToHex(hash));
    }
    
    public Image getImage() throws IOException{
        Image output = ImageIO.read(new File(imagePath));
        return output;
    }
    
    //Returns one of the hashes stored in the block, depending on what kind of 
    //hash algorithm is given (e.g. Giving Average Hash will return hashAV)
    public Mat getImageHash(ImgHashBase hasher){
        if(hasher instanceof AverageHash){
            //System.out.println("Accessing Average Hash");
            return hashAV;
        }
        else if(hasher instanceof PHash){
            //System.out.println("Accessing PHash");
            return hashPH;
        }
        if(hasher instanceof BlockMeanHash){
            //System.out.println("Accessing Block Mean Hash");
            return hashBM;
        }
        else if(hasher instanceof ColorMomentHash){
            //System.out.println("Accessing Color Moment Hash");
            return hashCM;
        }
        if(hasher instanceof MarrHildrethHash){
            //System.out.println("Accessing Marr Hildreth Hash");
            return hashMH;
        }
        else if(hasher instanceof RadialVarianceHash){
            //System.out.println("Accessing Radial Variance Hash");
            return hashRV;
        }
             
        System.out.println("Invalid Hash; Access Denied");
        return null;    
    }
    
    
//    public void printDetails(int i){
//        System.out.println("Block " + i + " -- \n\t"
//                + "Image: " + imagePath + "\n\t"
//                + "Image Hash: " + StringUtil.Sha256ToString(imageHash) + "\n\t"
//                + "Block Hash: " + StringUtil.Sha256ToString(hash) + "\n\t"
//                + "Previous Hash: " + StringUtil.Sha256ToString(previousHash) + "\n\t"
//                + "Nonce: " + nonce);
//    }
    
}