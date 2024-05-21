package plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import java.io.FileNotFoundException;

public class LawnMower {
    private Pane gamePane;
    public LawnMowerImage[] lawnM = new LawnMowerImage[5];
    private Timeline timeline;

    public void setLawnMowers(Pane gamePane) throws FileNotFoundException {
        this.gamePane = gamePane;
        lawnM[0] = new LawnMowerImage(40, 40);
        lawnM[1] = new LawnMowerImage(40, 155);
        lawnM[2] = new LawnMowerImage(40, 270);
        lawnM[3] = new LawnMowerImage(40, 400);
        lawnM[4] = new LawnMowerImage(40, 540);
        for (int i = 0; i < 5; i++) {
            gamePane.getChildren().add(lawnM[i].getLawnMImageView());
        }
    }

    public void moveLawnM(LawnMowerImage lawnMowerImage) {
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.01), event -> {
            if (lawnMowerImage != null) {
                if (lawnMowerImage.getLawnMImageView().getLayoutX() < 1100) {
                    lawnMowerImage.getLawnMImageView().setLayoutX(lawnMowerImage.getLawnMImageView().getLayoutX() + 5);
                } else {
                    timeline.stop();
                    removeLM(lawnMowerImage);
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void removeLM(LawnMowerImage lawnMowerImage) {
        gamePane.getChildren().remove(lawnMowerImage.getLawnMImageView());
        lawnMowerImage.getLawnMImageView().setLayoutX(1600);
        lawnMowerImage.getLawnMImageView().setLayoutY(1600);
    }

    public class LawnMowerImage extends Region {
        private static final int LM_WIDTH = 100;
        private static final int LM_HEIGHT = 89;
        private ImageView lawnMImageView;

        public LawnMowerImage(int xLayout, int yLayout) throws FileNotFoundException {
            Image lmImage = new Image("plantsvszombies/LawnMower.png");
            lawnMImageView = new ImageView(lmImage);
            lawnMImageView.setFitWidth(LM_WIDTH);
            lawnMImageView.setFitHeight(LM_HEIGHT);
            lawnMImageView.setPreserveRatio(true);
            lawnMImageView.setSmooth(true);
            lawnMImageView.setCache(true);
            lawnMImageView.setLayoutX(xLayout);
            lawnMImageView.setLayoutY(yLayout);
        }

        public ImageView getLawnMImageView() {
            return lawnMImageView;
        }
    }
}
