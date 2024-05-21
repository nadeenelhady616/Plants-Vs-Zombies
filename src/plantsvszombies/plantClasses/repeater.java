package plantsvszombies.plantClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class repeater extends Plants{
    @FXML
    private static Pane gamelanes;
    private final List<ImageView> bullets = new ArrayList<>();
    private final double bulletSpeed = 3;
    private long lastShootTime = 0;
    private double shooterX, shooterY;
    private boolean firstTwoBulletsShot = false;
    private ImageView shooter;
    private static ImageView peaBullet;

    public static ImageView getPeaBullet() {
        return peaBullet;
    }

    public repeater(Pane gamePane, double shooterX, double shooterY) throws FileNotFoundException {
        gamelanes = gamePane;
        this.shooterX = shooterX;
        this.shooterY = shooterY;
        createShooter();
    }

    public void createShooter() throws FileNotFoundException {
        Image shooterImage = new Image("plantsvszombies/plants/RepeaterGif.gif");
        shooter = new ImageView(shooterImage);
        shooter.setFitWidth(100);
        shooter.setFitHeight(100);
        shooter.setPreserveRatio(true);
        shooter.setX(shooterX);
        shooter.setY(shooterY);
        shooterX = shooter.getX() + shooter.getFitWidth() / 2;
        shooterY = shooter.getY() + shooter.getFitHeight() / 2;
        gamelanes.getChildren().add(shooter);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!firstTwoBulletsShot && now - lastShootTime >= 60_000_000) {
                    try {
                        shoot();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    lastShootTime = now;
                    firstTwoBulletsShot = true;
                }

                if (firstTwoBulletsShot && now - lastShootTime >= 2_000_000_000) {
                    try {
                        shoot();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        shoot();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    lastShootTime = now;
                    firstTwoBulletsShot = false;
                }

                List<ImageView> bulletsToRemove = new ArrayList<>();
                for (ImageView bullet : bulletsList) {
                    bullet.setX(bullet.getX() + bulletSpeed * 3);
                    if (bullet.getX() > gamelanes.getWidth()) {
                        bulletsToRemove.add(bullet);
                    }
                }

                bulletsToRemove.forEach(bullet -> gamelanes.getChildren().remove(bullet));
                bulletsList.removeAll(bulletsToRemove);
            }
        };
        timer.start();
    }

    private void shoot() throws FileNotFoundException {
        Image bulletImage = new Image(new FileInputStream("LASTHOPFULLY/plantsVSzombiesFinale/src/plantsvszombies/plants/Pea.png"));
        peaBullet = new ImageView(bulletImage);
        peaBullet.setFitWidth(35);
        peaBullet.setFitHeight(35);
        peaBullet.setX(shooterX - peaBullet.getFitWidth() / 2 + 15);
        peaBullet.setY(shooterY - peaBullet.getFitHeight() / 2 - 15);
        bulletsList.add(peaBullet);
        gamelanes.getChildren().add(peaBullet);
    }
    public static void remove(ImageView peaBullet){
        gamelanes.getChildren().remove(peaBullet);
    }
    public ImageView getRepeaterImageView(){

        return shooter;
    }
}