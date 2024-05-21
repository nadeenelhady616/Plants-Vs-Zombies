
package plantsvszombies.plantClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Plants {

    @FXML
    private static Pane gamelanes;
    private int row;
    private int column;
    private ImageView PlantImageView;
    public static List<ImageView> plantsList = new ArrayList<>();
    public static List<ImageView> bulletsList = new ArrayList<>();

    public void setGamelanes(Pane pane){
       this.gamelanes=pane;
    }

    public void removeBullet(ImageView bullet) {
        if(bullet==PeaShooter.getPeaBullet())
           PeaShooter.remove(bullet);
        else if(bullet==repeater.getPeaBullet())
            repeater.remove(bullet);
        else Cactus.remove(bullet);

        bulletsList.remove(bullet);
    }
    public int checkPlant(ImageView plant){
        if(plant == Nut.getNutImageView())
            return 1;    // 1 for nut
        else if (plant==potatomine.getMineImageView())
            return 2;   // 2 for potato
        else if (plant==CherryBomb.getCherryBombImageView())
            return 3;   // 3 for cherry
        else return 4;  // for other plants
    }
    public int checkBullet(ImageView bullet){
        if(bullet==PeaShooter.getPeaBullet()||bullet==repeater.getPeaBullet())
            return 1;
        else
            return 3;
    }
    public void plantDying(ImageView plantImageView) {
        gamelanes.getChildren().remove(this.PlantImageView);
        plantsList.remove(PlantImageView);

    }



}
