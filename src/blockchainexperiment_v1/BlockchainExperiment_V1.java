/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainexperiment_v1;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.img_hash.AverageHash;
import org.opencv.img_hash.BlockMeanHash;
import org.opencv.img_hash.ColorMomentHash;
import org.opencv.img_hash.MarrHildrethHash;
import org.opencv.img_hash.PHash;
import org.opencv.img_hash.RadialVarianceHash;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Cameron
 */
public class BlockchainExperiment_V1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Logger.initialise();
        
        BlockchainController blockchain = new BlockchainController();
        Mat test = Imgcodecs.imread("images/test_2/dog2.png");
        Mat test2 = Imgcodecs.imread("images/test_2/dog2_edit.png");
        
        blockchain.findSimilarImage(test);
        blockchain.findSimilarImage(test2);
        //launch(args);
        System.exit(0);
    }
}