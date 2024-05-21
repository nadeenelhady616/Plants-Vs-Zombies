package plantsvszombies.plantClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NightSunPlant extends Plants {
    private ImageView NightPlantImageView;

    private Pane gamePane = new Pane();


    public NightSunPlant(Pane gamePane, double xPos, double yPos, Sun sun) throws FileNotFoundException {
        this.gamePane = gamePane;
        createNightSunflower(xPos, yPos);
        sun.generateSunAboveSunflower(xPos,yPos);

    }

    public void setXPos(double xPos) {
        NightPlantImageView.setX(xPos);
    }

    public void setYPos(double yPos) {
        NightPlantImageView.setY(yPos);
    }

    private void createNightSunflower(double xPos, double yPos) throws FileNotFoundException {
        Image NightPlantImage = new Image("plantsvszombies/plants/nightgSunPlant.png");
        NightPlantImageView = new ImageView(NightPlantImage);
        NightPlantImageView.setFitHeight(70);
        NightPlantImageView.setFitWidth(70);
        NightPlantImageView.setPreserveRatio(true);
        NightPlantImageView.setLayoutX(xPos);
        NightPlantImageView.setLayoutY(yPos);
        gamePane.getChildren().add(NightPlantImageView);
    }
    public ImageView getNightPlantImageView() {
        return NightPlantImageView;
    }


}

