package plantsvszombies;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class PlantsVsZombies extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("pvzscene_1.fxml"));
        Parent root1 = loader1.load();


        Scene scene1 = new Scene(root1);

        primaryStage.setScene(scene1);

        primaryStage.setTitle("Plants VS Zombies");
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}