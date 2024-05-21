package plantsvszombies.plantClasses;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Sun extends ImageView {

    private static final int SUN_WIDTH = 70;
    private static final int SUN_HEIGHT = 70;
    private static final int SUN_DROP_DURATION = 5000;
    private static final int FADE_DURATION = 500;
    private static final Duration SPAWN_INTERVAL = Duration.seconds(3);

    private Pane gamePane;
    private static int sunCounter=50;
    private static Label sunCounterLabel = new Label("50");

    public Sun(Pane gamePane, Label sunCounterLabel) {
        this.gamePane = gamePane;
        Sun.sunCounterLabel = sunCounterLabel;
    }
 public Sun(Pane gamePane) {
        this.gamePane = gamePane;
        startSunSpawning();
    }
    private void startSunSpawning() {
        Timeline timeline = new Timeline(new KeyFrame(SPAWN_INTERVAL, event -> {
            try {
                spawnSun();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void spawnSun() throws FileNotFoundException {
        Image sunImage = new Image("plantsvszombies/Sun.png");
        ImageView sun = new ImageView(sunImage);
        sun.setFitWidth(SUN_WIDTH);
        sun.setFitHeight(SUN_HEIGHT);

        sun.setLayoutX(getRandomXPosition());
        sun.setLayoutY(0);

        gamePane.getChildren().add(sun);
        fall(sun);
    }

    private void fall(ImageView sun) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(SUN_DROP_DURATION), sun);
        transition.setToY(getRandomYPosition());

        sun.setOnMousePressed(e -> {
            collectSun(sunCounterLabel);
            gamePane.getChildren().remove(sun);
        });
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(event -> {
            FadeTransition fade = new FadeTransition(Duration.millis(FADE_DURATION), sun);
            fade.setToValue(0);
            fade.setOnFinished(e -> gamePane.getChildren().remove(sun));
            fade.play();
        });

        transition.setOnFinished(event -> pause.play());
        transition.play();
    }

    private void collectSun(Label sunCounterLabel) {
        sunCounter += 800;
        sunCounterLabel.setText(String.valueOf(sunCounter));
        sunCounterLabel.setAlignment(Pos.CENTER);
    }

    private double getRandomXPosition() {
        Random random = new Random();
        double maxXPosition = gamePane.getWidth() - SUN_WIDTH;
        double startingXPosition = gamePane.getWidth() / 4.0;
        return startingXPosition + random.nextDouble() * (maxXPosition - startingXPosition);
    }

    public void generateSunAboveSunflower(double xPos, double yPos) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(14), event -> {
            try {
                ImageView sun = createSunAboveSunflower(xPos, yPos);
                gamePane.getChildren().add(sun);

                FadeTransition fade = new FadeTransition(Duration.millis(1000), sun);
                fade.setDelay(Duration.seconds(7));
                fade.setToValue(0);
                fade.setOnFinished(e -> gamePane.getChildren().remove(sun));
                fade.play();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private ImageView createSunAboveSunflower(double xPos, double yPos) throws FileNotFoundException {
        Image sunImage = new Image("plantsvszombies/Sun.png");
        ImageView sun = new ImageView(sunImage);
        sun.setFitWidth(SUN_WIDTH);
        sun.setFitHeight(SUN_HEIGHT);
        sun.setLayoutX(xPos + 10);
        sun.setLayoutY(yPos - 10);

        sun.setOnMousePressed(e -> {
            collectSun(sunCounterLabel);
            gamePane.getChildren().remove(sun);
        });

        return sun;
    }

    private double getRandomYPosition() {
        Random random = new Random();
        return random.nextDouble() * (gamePane.getHeight() - SUN_HEIGHT);
    }

    public int getSunCounter() {
        return sunCounter;
    }
    public void setSunCounter(int sunAmount) {
        sunCounter-=sunAmount;
    }
    public void setSunCount(int sunAmount) {
        sunCounter=sunAmount;
    }
}
