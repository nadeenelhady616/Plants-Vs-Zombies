package plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import plantsvszombies.plantClasses.Nut;
import plantsvszombies.plantClasses.Plants;

import static plantsvszombies.scenecontroller1.*;

public class Zombie {


    private Stage stage;
    private Scene scene;
    private Parent root;
    private static final int WINDOW_WIDTH = 1100;
    public static final int EASY_ZOMBIES = 10;
    public static final int MEDIUM_ZOMBIES = 15;
    public static final int HARD_ZOMBIES = 20;
    private static int NUM_ZOMBIES;
    private LawnMower lawnMower;
    public List<ZombieGraphic> zombieGraphicsList = new CopyOnWriteArrayList<>();
    private double[] lanes = {0,0,105,105,260,260,340,340,480, 480};
    private int score = 0;
    private Pane gamelanes;




    public void createZombies(Pane gamePane, LawnMower lawnMower) throws FileNotFoundException, InterruptedException {
        this.gamelanes = gamePane;
        this.lawnMower = lawnMower;
        Timeline zombieTimeline = new Timeline();
        KeyFrame zombieKeyFrame = new KeyFrame(Duration.seconds(12), event -> {
            int num2 = zombieGraphicsList.size();
            if (num2 < NUM_ZOMBIES) {
                try {
                    ZombieGraphic newZombie = new ZombieGraphic();
                    zombieGraphicsList.add(newZombie);
                    int laneNumber = new Random().nextInt(lanes.length);
                    newZombie.getZombieImageView().setLayoutX(WINDOW_WIDTH);
                    newZombie.getZombieImageView().setLayoutY(lanes[laneNumber]);
                    gamePane.getChildren().add(newZombie.zombieImageView);
                    moveZombies(newZombie);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                zombieTimeline.stop();
            }
        });
        zombieTimeline.getKeyFrames().add(zombieKeyFrame);
        zombieTimeline.setCycleCount(NUM_ZOMBIES);
        zombieTimeline.play();

    }
    public void moveZombies(ZombieGraphic zombieGraphic) {
        Timeline timeline = new Timeline();
        try {
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.02), event -> {
                try {
                    if (zombieGraphic != null) {
                        if (zombieGraphic.getZombieImageView().getLayoutX() > 0) {
                            // Move the zombie towards the left
                            zombieGraphic.getZombieImageView().setLayoutX(zombieGraphic.getZombieImageView().getLayoutX() - 1);
                        }
                        else if (zombieGraphic.getZombieImageView().getLayoutX() == 0) {
                            // Stop the timeline when the zombie reaches the left edge of the window
                            timeline.stop();
                        }
                        if (zombieGraphicsList.contains(zombieGraphic)) {
                            ActivateLM(zombieGraphic);
                            CollisionWithLawnM(zombieGraphic);
                            checkIsHit(zombieGraphic);
                            checkCollisionWithPlant(zombieGraphic,timeline);
                            checkLosingCondition();
                        }
                    } else {
                        System.out.println("zombiegraphic is null");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } catch (Exception e) {
            System.out.println("An error occurred during timeline setup: " + e.getMessage());
        }
    }

    public void ActivateLM(ZombieGraphic zombieGraphic){
        if(zombieGraphic.getZombieImageView().getLayoutX()==200){
            if(zombieGraphic.getZombieImageView().getLayoutY()==0)
                lawnMower.moveLawnM(lawnMower.lawnM[0]);
            else if(zombieGraphic.getZombieImageView().getLayoutY()==105)
                lawnMower.moveLawnM(lawnMower.lawnM[1]);
            else if(zombieGraphic.getZombieImageView().getLayoutY()==260)
                lawnMower.moveLawnM(lawnMower.lawnM[2]);
            else if(zombieGraphic.getZombieImageView().getLayoutY()==340)
                lawnMower.moveLawnM(lawnMower.lawnM[3]);
            else{
                lawnMower.moveLawnM(lawnMower.lawnM[4]);
            }}
    }
    public void CollisionWithLawnM(ZombieGraphic zombieGraphic){
            for (int i = 0; i < 5; i++) {
                if (lawnMower.lawnM[i].getLawnMImageView().getBoundsInParent().intersects(zombieGraphic.getZombieImageView().getBoundsInParent())) {
                    zombieDying(zombieGraphic);
                }
            }}
    public void checkIsHit(ZombieGraphic zombieGraphic) {
        for (int i = 0; i < Plants.bulletsList.size(); i++) {
            if(zombieGraphic.getZombieImageView().getBoundsInParent().intersects(Plants.bulletsList.get(i).getBoundsInParent())){
                zombieGraphic.setHealth((zombieGraphic.getHealth())-scenecontroller1.plants.checkBullet(Plants.bulletsList.get(i)));
                scenecontroller1.plants.removeBullet(Plants.bulletsList.get(i));
            }
        }
        if(zombieGraphic.getHealth()==0)
            zombieDying(zombieGraphic);
    }


    public void zombieEating(ZombieGraphic zombieGraphic,Timeline timeline) {
        timeline.pause();
        if(zombieGraphic.getzNum()==1)
            zombieGraphic.getZombieImageView().setImage(zombieGraphic.normalZombieEating);
        else if (zombieGraphic.getzNum()==2)
            zombieGraphic.getZombieImageView().setImage(zombieGraphic.rugbyZombieEating);
        else zombieGraphic.getZombieImageView().setImage(zombieGraphic.michaelJacksonZombieEating);


    }
    public void zombieWalkingAgain(ZombieGraphic zombieGraphic,Timeline timeline){
        timeline.play();
        if(zombieGraphic.getzNum()==1)
            zombieGraphic.getZombieImageView().setImage(zombieGraphic.normalZombie);
        else if (zombieGraphic.getzNum()==2)
            zombieGraphic.getZombieImageView().setImage(zombieGraphic.rugbyZombie);
        else zombieGraphic.getZombieImageView().setImage(zombieGraphic.michaelJacksonZombie);



    }
    public void checkCollisionWithPlant(ZombieGraphic zombieGraphic,Timeline ztimeline) throws InterruptedException {
        for (int i = 0; i < scenecontroller1.plants.plantsList.size(); i++) {
            if(zombieGraphic.getZombieImageView().getBoundsInParent().intersects(scenecontroller1.plants.plantsList.get(i).getBoundsInParent())) {
                if(scenecontroller1.plants.plantsList.contains(scenecontroller1.plants.plantsList.get(i))){
                if(plants.checkPlant(scenecontroller1.plants.plantsList.get(i))==1){
                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(6), event -> {
                        zombieEating(zombieGraphic,ztimeline);
                    }));
                    timeline.setCycleCount(1);
                    timeline.play();
                        Nut.nutDying(scenecontroller1.plants.plantsList.get(i));
                        zombieWalkingAgain(zombieGraphic, ztimeline);


                    //zombieWalkingAgain(zombieGraphic,ztimeline);
                } else if (plants.checkPlant(scenecontroller1.plants.plantsList.get(i))==2) {
                    fire(scenecontroller1.plants.plantsList.get(i));
                    zombieDying(zombieGraphic);
                }else if (plants.checkPlant(scenecontroller1.plants.plantsList.get(i))==3){
                    zombieDying(zombieGraphic);
                }else{
                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3), event -> {
                        zombieEating(zombieGraphic,ztimeline);
                    }));
                    timeline.setCycleCount(1);
                    timeline.play();
                    plants.plantDying(scenecontroller1.plants.plantsList.get(i));
                    zombieWalkingAgain(zombieGraphic,ztimeline);

                }}
            }
        }
    }

    public void zombieDying(ZombieGraphic zombieGraphic) {
        //zombie dying by plants or lawn mower
        gamelanes.getChildren().remove(zombieGraphic.getZombieImageView());
        zombieGraphicsList.remove(zombieGraphic);
        score+=20;
    }
    public void fire(ImageView pm){
        Image fireImage;
        fireImage = new Image("plantsvszombies/plants/fire.gif");

        pm.setImage(fireImage);
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            plants.plantDying(pm);
        }));
        timeline2.play();
    }

    public int getScore(){

        return score;
    }
    public void checkLosingCondition() throws IOException {
        final double losingThreshold = 10;

        Stage stage = (Stage) gamelanes.getScene().getWindow();
        if (gamelanes == null || zombieGraphicsList == null) {
            return; // Exit the method if any required objects are null
        }
        for (ZombieGraphic zombieGraphic : zombieGraphicsList) {
            if (zombieGraphic == null || zombieGraphic.getZombieImageView() == null) {
                continue; // Skip this iteration if the zombieGraphic or its image view is null
            }
            double zombieXPosition = zombieGraphic.getZombieImageView().getLayoutX();
            if (zombieXPosition == losingThreshold) {
                System.out.println("WHYWHYWHWY");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("LoseScene.fxml"));
                    root = loader.load();

               /* stackPane.getChildren().setAll(root);
                stackPane.setVisible(true);*/
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
    }
//    public void checkWinningCondition() {
//        final int winningThreshold = 0;
//
//        if (zombieGraphicsList.isEmpty()) {
//            Stage stage = (Stage) gamePane.getScene().getWindow();
//            try {
//                System.out.println("winwin");
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("WinScene.fxml"));
//                root = loader.load();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                System.err.println("Error loading WinScene.fxml: " + e.getMessage());
//            }
//        }
//    }

     @FXML
    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pvzscene_1.fxml")));
        menuOn = true;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    class ZombieGraphic {
        private ImageView zombieImageView;
        private static final int ZOMBIE_WIDTH = 150;
        private static final int ZOMBIE_HEIGHT = 150;
        private int health;
        private int zNum;
        private Image normalZombie = new Image("plantsvszombies/ZombiePics/ZombieGif.gif");
        private Image normalZombieEating = new Image("plantsvszombies/ZombiePics/eatingZombies2.gif");
        private Image rugbyZombie = new Image("plantsvszombies/ZombiePics/RugbyZombie.gif");
        private Image rugbyZombieEating = new Image("plantsvszombies/ZombiePics/RugbyZombieEating.gif");
        private Image michaelJacksonZombie = new Image("plantsvszombies/ZombiePics/michaeljackson.gif");
        private Image michaelJacksonZombieEating = new Image("plantsvszombies/ZombiePics/dancingZombie.gif");



        public ZombieGraphic() throws FileNotFoundException {
            Random random = new Random();
            this.zNum = random.nextInt(3) + 1;

            if (zNum== 1) {
                zombieImageView = new ImageView(normalZombie);
                this.health=7;
            } else if (zNum == 2) {
                zombieImageView = new ImageView(rugbyZombie);
                this.health=10;
            } else if (zNum == 3) {
                zombieImageView = new ImageView(michaelJacksonZombie);
                this.health=8;
            }

            zombieImageView.setFitWidth(ZOMBIE_WIDTH);
            zombieImageView.setFitHeight(ZOMBIE_HEIGHT);
            zombieImageView.setPreserveRatio(true);
            zombieImageView.setSmooth(true);
            zombieImageView.setCache(true);


        }

        public ImageView getZombieImageView() {
            return zombieImageView;
        }

        public int getzNum() {
            return zNum;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }
    }
    public int getNUM_ZOMBIES() {
        return NUM_ZOMBIES;
    }


    public void setNUM_ZOMBIES(int NUM_ZOMBIES) {
        this.NUM_ZOMBIES = NUM_ZOMBIES;
    }
}
