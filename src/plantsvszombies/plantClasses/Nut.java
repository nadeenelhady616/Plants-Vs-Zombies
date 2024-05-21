package plantsvszombies.plantClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Nut extends Plants{
    private static ImageView nutImageView;
    private static Pane gamePane;

    public Nut(){
    }
    public Nut(Pane gamePane, double xPos, double yPos) throws FileNotFoundException {
        this.gamePane = gamePane;
        createnut(xPos, yPos);
    }

    private void createnut(double xPos, double yPos) throws FileNotFoundException {

        Image nut = new Image("plantsvszombies/plants/WallNutGif.gif");
        nutImageView = new ImageView(nut);
        nutImageView.setFitHeight(80);
        nutImageView.setFitWidth(80);
        nutImageView.setPreserveRatio(true);
        nutImageView.setLayoutX(xPos);
        nutImageView.setLayoutY(yPos);
        gamePane.getChildren().add(nutImageView);
    }

    public static void nutDying(ImageView nut) {
       gamePane.getChildren().remove(nut);
       plantsList.remove(nut);
    }
    public static ImageView getNutImageView() {
        return nutImageView;
    }
}
