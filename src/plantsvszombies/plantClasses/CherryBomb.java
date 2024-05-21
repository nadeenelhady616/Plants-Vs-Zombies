
package plantsvszombies.plantClasses;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.util.Duration;

public class CherryBomb extends Plants {
    private static ImageView cherryBombImageView;

    private Pane gamePane = new Pane();
    private Image explosionImage;

    public CherryBomb(Pane gamePane, double xPos, double yPos) throws FileNotFoundException {
        this.gamePane = gamePane;
        createCherryBomb(xPos, yPos);
    }

    private void createCherryBomb(double xPos, double yPos) {

        // Load the initial image (GIF)
        Image cherryBombImage = new Image("plantsvszombies/plants/cherryBomb.gif");
        cherryBombImageView = new ImageView(cherryBombImage);
        cherryBombImageView.setFitHeight(100);
        cherryBombImageView.setFitWidth(100);
        cherryBombImageView.setPreserveRatio(true);
        cherryBombImageView.setLayoutX(xPos);
        cherryBombImageView.setLayoutY(yPos);
        gamePane.getChildren().add(cherryBombImageView);
        plantsList.add(cherryBombImageView);

        // Load the explosion image

        explosionImage = new Image("plantsvszombies/plants/Cherryexplosion.PNG");

        // Set up a Timeline to handle image visibility
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> {
                // Show the cherry bomb GIF
                cherryBombImageView.setVisible(true);
            }),
            /*new KeyFrame(Duration.seconds(1), event -> {
                // Hide the cherry bomb GIF after 1 second
                cherryBombImageView.setVisible(false);
            }),*/
            new KeyFrame(Duration.seconds(0.5), event -> {
                // Show the explosion image after 2 seconds
            cherryBombImageView.setImage(explosionImage);
            cherryBombImageView.setVisible(true);

            }),
            new KeyFrame(Duration.seconds(1), event -> {
                // Remove the cherry bomb ImageView after 3 seconds
                 cherryBombImageView.setVisible(false);
               //  setAlive(false);
               /* gamePane.getChildren().remove(explosionImage);*/
            })
        );

        // Play the Timeline
        timeline.play();
    }
    public static ImageView getCherryBombImageView() {
        return cherryBombImageView;
    }


}
