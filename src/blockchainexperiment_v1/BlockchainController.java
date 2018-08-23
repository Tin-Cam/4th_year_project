/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.img_hash.AverageHash;
import org.opencv.img_hash.BlockMeanHash;
import org.opencv.img_hash.ColorMomentHash;
import org.opencv.img_hash.MarrHildrethHash;
import org.opencv.img_hash.PHash;
import org.opencv.img_hash.RadialVarianceHash;

/**
 *
 * @author Cameron
 */
public class BlockchainController {
    
    public ArrayList<Device> deviceList = new ArrayList<>();
    private final int difficulty = 5;
    
    //Constructor and initialliser for the controller
    public BlockchainController(){
        //blockchain.add(new Block());
        initialiseDevices();
        //runDevices();
    }
    
    
    private void initialiseDevices(){
        deviceList.add(new Device(AverageHash.create()));
        deviceList.add(new Device(PHash.create()));
        //deviceList.add(new Device(ColorMomentHash.create()));
        deviceList.add(new Device(BlockMeanHash.create()));
        deviceList.add(new Device(MarrHildrethHash.create()));
        deviceList.add(new Device(RadialVarianceHash.create()));
        
    }
    
    public void findSimilarImage(Mat image){
        setImage(image);
        runDevices();
    }
    
    private void runDevices(){
        for(int i = 0; i < deviceList.size(); i++)
            new Thread(deviceList.get(i)).start();
    }
    
    public void addBlock(Block block){
        for(int i = 0; i < deviceList.size(); i++)
            deviceList.get(i).addBlock(block);
    }
    
    public void setImage(Mat image){
        for(int i = 0; i < deviceList.size(); i++)
            deviceList.get(i).setImage(image);
    }
}
