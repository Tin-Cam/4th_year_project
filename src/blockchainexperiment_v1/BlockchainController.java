/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.opencv.core.Mat;
import org.opencv.img_hash.AverageHash;
import org.opencv.img_hash.BlockMeanHash;
import org.opencv.img_hash.ColorMomentHash;
import org.opencv.img_hash.MarrHildrethHash;
import org.opencv.img_hash.PHash;
import org.opencv.img_hash.RadialVarianceHash;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Cameron
 */
public class BlockchainController {
    
    public ArrayList<Device> deviceList = new ArrayList<>();
    private final int difficulty = 4;
    
    private final String imageDirectory = "images/experiment"; //The directory of the images used in the blockcahin
    
    //Constructor and initialliser for the controller
    public BlockchainController() throws IOException{
        //blockchain.add(new Block());
        initialiseDevices();
        initialiseBlockchain();
    }
    
    
    private void initialiseDevices(){
        deviceList.add(new Device(AverageHash.create(), difficulty));
        deviceList.add(new Device(PHash.create(), difficulty));      
        deviceList.add(new Device(BlockMeanHash.create(), difficulty));
        //deviceList.add(new Device(ColorMomentHash.create(), difficulty));
        deviceList.add(new Device(MarrHildrethHash.create(), difficulty));
        deviceList.add(new Device(RadialVarianceHash.create(), difficulty));
        
    }
    
    public void findSimilarImage(Mat image, String testName) throws InterruptedException, IOException{
        setImage(image);
        runDevices(testName);
        System.out.println();
    }
    
    public void setImage(Mat image){
        for(int i = 0; i < deviceList.size(); i++)
            deviceList.get(i).setImage(image);
    }
    
    //Creates a blockchain on startup
    private void initialiseBlockchain() throws IOException{
        //Creates the genesis block
        Block genesis = new Block();
        genesis.mineBlock(difficulty);
        addBlock(genesis);
        
        //List of the images in the given image directory for the experiment
        String[] imageList = new File(imageDirectory).list();
        
        //Adds the other blocks
        for(int i = 0; i < imageList.length; i++){
            String image = imageDirectory + "/" + imageList[i];
            Block block = new Block(deviceList.get(0).blockchain.get(i).hash, image);
            
            Mat blockImage = Imgcodecs.imread(block.imagePath);
            setImage(blockImage);
            //Sets the image hashes for the blocks
            for(int j = 0; j < deviceList.size(); j++){
                Mat hash = deviceList.get(j).hashImage();
                block.setImageHash(deviceList.get(j).hasher, hash);
            }
            
            addBlock(block);
        }
        System.out.println("Blockchain has been built!");
        System.out.println("Blockchain is legit? " + verifyBlockchain());
    }
    
    private void runDevices(String testName) throws InterruptedException, IOException{
        //Starts the devices (Threads)
        Thread threads[] = new Thread[deviceList.size()];
        for(int i = 0; i < deviceList.size(); i++){
            threads[i] = new Thread(deviceList.get(i));
            threads[i].start();
        }
        //Waits for the threads to finish
        for(int i = 0; i < deviceList.size(); i++){
            threads[i].join();
        }
        //System.out.println("Best score is " + bestScore());
        System.out.println("Most Occurring index is " + mostOccurringIndex());
        System.out.println("The best score is " + bestScore().score + " using " + bestScore().getHashName());
        
        Device device = bestScore();
        Logger.writeTest(testName, device.score, device.getHashName(), getBlock(device.scoreIndex).imagePath);
    }
    
    private Block getBlock(int index){
        return deviceList.get(0).blockchain.get(index);
    }
    
    private int mostOccurringIndex(){       
        //Creates a map that contains the frequencies of the indexes
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < deviceList.size(); i++){
            
            int key = deviceList.get(i).scoreIndex;
            if(map.containsKey(key)){
                int frequency = map.get(key);
                map.put(key, frequency++);
            }
            else
                map.put(deviceList.get(i).scoreIndex, 1);
        }
        
        //Finds the most occuring index
        int max = 0;
        int result = 0;
        for(Entry<Integer, Integer> i : map.entrySet()){
            if (max < i.getValue())
            {
                result = i.getKey();
                max = i.getValue();
            }
        }
            
        return result;
    }
    
    private Device bestScore(){
        double bestScore = 1000.0;
        Device result = null;
        
        for(int i = 0; i < deviceList.size(); i++)
            if(deviceList.get(i).score < bestScore){
                bestScore = deviceList.get(i).score;
                result = deviceList.get(i);
            }
        
        return result;
    }
    
    public void addBlock(Block block){
        for(int i = 0; i < deviceList.size(); i++)
            deviceList.get(i).addBlock(block);
    }
    
    public boolean verifyBlockchain(){
        //Checks if every blockchain is legitiment
        for(int i = 0; i < deviceList.size(); i++)
            if(!deviceList.get(i).verifyBlockchain())
                return false;
        //Checks if every blockchain is the same
        for(int i = 1; i < deviceList.size(); i++)
            if(!deviceList.get(i).blockchain.equals(deviceList.get(i - 1).blockchain))
                return false;
        
        return true;
    }
    
    
}
