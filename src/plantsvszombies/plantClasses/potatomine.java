package plantsvszombies.plantClasses;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import plantsvszombies.scenecontroller1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class potatomine extends Plants{
     private static ImageView mineImageView;
    private Pane gamePane = new Pane();


    public potatomine(Pane gamePane, double xPos, double yPos) throws FileNotFoundException {
        this.gamePane = gamePane;
        createmine(xPos, yPos);}
       
    
    private void createmine(double xPos, double yPos) throws FileNotFoundException {
        Image potatomineimage = new Image("plantsvszombies/plants/PotatoMineGif.gif");
        mineImageView  = new ImageView(potatomineimage);
        mineImageView.setFitHeight(100);
        mineImageView.setFitWidth(100);
        mineImageView.setPreserveRatio(true);
        mineImageView.setLayoutX(xPos);
        mineImageView.setLayoutY(yPos);
        gamePane.getChildren().add(mineImageView);
    }

    public static ImageView getMineImageView() {
        return mineImageView;
    }
}