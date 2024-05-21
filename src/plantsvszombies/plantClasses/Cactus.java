package plantsvszombies.plantClasses;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Cactus extends Plants{


    @FXML
    public static Pane gamelanes;
    private ImageView Cactus;
    private final List<ImageView> bullets = new ArrayList<>();
    private final double bulletSpeed = 3;
    private long lastShootTime = 0;
    private final long shootInterval = 2_000_000_000;
    private double CactusX, CactusY;
    private static ImageView cactusBullet;

    public Cactus(Pane gamePane, double shooterX, double shooterY) throws FileNotFoundException {
        gamelanes= gamePane;
        this.CactusX=shooterX;
        this.CactusY=shooterY;
        CreateCactus();
    }

    public void CreateCactus() throws FileNotFoundException {
        Image shooterImage = new Image("plantsvszombies/plants/cactus.gif");
        Cactus = new ImageView(shooterImage);
        Cactus.setFitWidth(80);
        Cactus.setFitHeight(80);
        Cactus.setPreserveRatio(true);
        Cactus.setX(CactusX);
        Cactus.setY(CactusY);
        CactusX = Cactus.getX() + Cactus.getFitWidth() / 2;
        CactusY = Cactus.getY() + Cactus.getFitHeight() /1.5;
        gamelanes.getChildren().add(Cactus);

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
        Image bulletImage = new Image((new FileInputStream("LASTHOPFULLY/plantsVSzombiesFinale/src/plantsvszombies/plants/cactusBullets.png")));
        cactusBullet = new ImageView(bulletImage);
        cactusBullet.setFitWidth(50);
        cactusBullet.setFitHeight(50);
        cactusBullet.setX((CactusX - cactusBullet.getFitWidth() / 2) + 15);
        cactusBullet.setY((CactusY - cactusBullet.getFitHeight() / 2) - 15);
        bulletsList.add(cactusBullet);
        gamelanes.getChildren().add(cactusBullet);
    }
    public static void remove(ImageView peaBullet){
        gamelanes.getChildren().remove(peaBullet);
    }
    public static ImageView getCactusBullet() {
        return cactusBullet;
    }

    public ImageView getCactusImageView() {
        return Cactus;
    }
}
