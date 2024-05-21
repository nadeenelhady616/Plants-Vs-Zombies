package plantsvszombies.plantClasses;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import plantsvszombies.scenecontroller1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PeaShooter extends Plants{
    @FXML
    public static Pane gamelanes;
    private final double bulletSpeed = 5; // Fixed bullet speed
    private long lastShootTime = 0;
    private final long shootInterval = 2_000_000_000; // Shoot every 2 seconds
    private double shooterX, shooterY;
    private static ImageView shooter;
    private static ImageView peaBullet;

    public PeaShooter(Pane gamePane, double shooterX, double shooterY) throws FileNotFoundException {
        gamelanes = gamePane;
        this.shooterX = shooterX;
        this.shooterY = shooterY;
        shooter = new ImageView(new Image("plantsvszombies/plants/PeaShooterGif.gif"));
        createShooter();
    }

    private void createShooter() {
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
                if (now - lastShootTime >= shootInterval) {
                    try {
                        shoot();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    lastShootTime = now;
                }

                List<ImageView> bulletsToRemove = new ArrayList<>();
                for (ImageView bullet : bulletsList) {
                    bullet.setX(bullet.getX() + bulletSpeed);
                    if (bullet.getX() > gamelanes.getWidth()) {
                        try {
                            bulletsToRemove.add(bullet);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                bulletsToRemove.forEach(bullet -> gamelanes.getChildren().remove(bullet));
                bulletsList.removeAll(bulletsToRemove);
            }
        };
        timer.start();
    }

    private void shoot() throws FileNotFoundException {
        peaBullet = new ImageView(new Image("plantsvszombies/plants/Pea.png"));
        peaBullet.setFitWidth(35);
        peaBullet.setFitHeight(35);
        peaBullet.setX((shooterX - peaBullet.getFitWidth() / 2) + 15);
        peaBullet.setY((shooterY - peaBullet.getFitHeight() / 2) - 15);
        bulletsList.add(peaBullet);
        gamelanes.getChildren().add(peaBullet);
    }

    public void removePeaShooter() {
        gamelanes.getChildren().remove(shooter);
    }

    public double getBulletXCoordinate() {
        return peaBullet.getX();
    }

    public double getBulletYCoordinate() {
        return peaBullet.getY();
    }

    public double getShooterXCoordinate() {
        return shooterX;
    }

    public double getShooterYCoordinate() {
        return shooterY;
    }

    public static void remove(ImageView peaBullet) {
        gamelanes.getChildren().remove(peaBullet);
    }

    public static ImageView getPeaBullet() {
        return peaBullet;
    }

    public ImageView getShooterImageView() {
        return shooter;
    }
}
