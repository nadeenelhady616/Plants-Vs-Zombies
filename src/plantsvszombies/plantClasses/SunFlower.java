package plantsvszombies.plantClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SunFlower extends Plants {
    private ImageView sunflowerImageView;
    private Pane gamePane = new Pane();

    public SunFlower(Pane gamePane, double xPos, double yPos, Sun sun) throws FileNotFoundException {
        this.gamePane = gamePane;
        createSunflower(xPos, yPos);
        sun.generateSunAboveSunflower(xPos, yPos);
    }
    private void createSunflower(double xPos, double yPos) throws FileNotFoundException {
        Image sunflowerImage = new Image("plantsvszombies/plants/SunflowerGif.gif");
    
        sunflowerImageView = new ImageView(sunflowerImage);
        sunflowerImageView.setFitHeight(100);
        sunflowerImageView.setFitWidth(100);
        sunflowerImageView.setPreserveRatio(true);
        sunflowerImageView.setLayoutX(xPos);
        sunflowerImageView.setLayoutY(yPos);
        gamePane.getChildren().add(sunflowerImageView);
    }

    public ImageView getSunflowerImageView() {
        return sunflowerImageView;
    }

public void removeSunflower(ImageView sunf) {
    gamePane.getChildren().remove(sunf);
    plantsList.remove(sunf);
}
}

