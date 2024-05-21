package plantsvszombies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import plantsvszombies.plantClasses.*;

public class scenecontroller1 {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Button exitbtn;
    Node targetNode;
    private StackPane stackpane;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private ImageView GameBackground;
    @FXML
    private ImageView loanMower1;
    @FXML
    private Button menubtn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    public ImageView sunflowerPlant;
    @FXML
    public Label sunCounterLabel;
    @FXML
    private RadioButton easybtn, mediumbtn, hardbtn;
    @FXML
    public GridPane gridPane = new GridPane();
    @FXML
    public Pane gamelanes = new Pane();

    public LawnMower lawnMower;
    public static boolean menuOn = false;
    public static Plants plants;
    private Sun sun;
    private Sun sun1;
    private final int nutCost = 50;
    private final int sunFlowerCost = 50;
    private final int nightSunPlantCost = 25;
    private final int cherryBombCost = 150;
    private final int potatoMineCost = 25;
    private final int peaShooterCost = 100;
    private final int CactusCost = 125;
    private final int repeaterCost = 200;
    private static int thisCost = 50;

    public static Zombie zombie=new Zombie();

    public void initialize(Parent root) throws IOException, InterruptedException {
        Pane pane = (Pane) root;
        lawnMower=new LawnMower();
        sunCounterLabel.setText("50");
        sunCounterLabel.setAlignment(Pos.CENTER);
        sun = new Sun(pane, sunCounterLabel);
        sun.setSunCount(50);
        sun1 = new Sun(pane);
        zombie = new Zombie();
        zombie.createZombies(gamelanes, lawnMower);
        initializeProgressBar(zombie.getNUM_ZOMBIES());
        lawnMower.setLawnMowers(gamelanes);
        plants = new Plants();
        plants.setGamelanes(gamelanes);
    }

    public void RadioButton(ActionEvent event) throws IOException, InterruptedException {
        stage = (Stage) easybtn.getScene().getWindow();
        scene = null;

        if (easybtn.isSelected()) {
            zombie.setNUM_ZOMBIES(Zombie.EASY_ZOMBIES);
            switchToScenes(event, "pvzGame.fxml");

        } else if (mediumbtn.isSelected()) {
            zombie.setNUM_ZOMBIES(Zombie.MEDIUM_ZOMBIES);
            switchToScenes(event, "NightBackGround.fxml");

        } else if (hardbtn.isSelected()) {
            zombie.setNUM_ZOMBIES(Zombie.HARD_ZOMBIES);
            switchToScenes(event, "hardScene.fxml");
        }

        initializeProgressBar(zombie.getNUM_ZOMBIES());

        if (scene != null) {
            stage.setScene(scene);
        }
    }

    public void switchToScenes(ActionEvent event, String fxmlFile) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
        scenecontroller1 scenecontroller1 = loader.getController();
        scenecontroller1.initialize(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pvzscene_1.fxml")));
        menuOn = true;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ImageView potato;
    @FXML
    private ImageView peashooterPlant;
    @FXML
    private ImageView explosivePlant;
    @FXML
    private VBox plantsImagesVbox;

    private ImageView dragImageView;
    private double offsetX;
    private double offsetY;

    private String objectType;

    public void Glow(Node node, double level) {
        Glow glow = new Glow();
        glow.setLevel(level);
        node.setEffect(glow);
    }

    @FXML
    public void handleMousePressed(MouseEvent event) throws FileNotFoundException {
        if (sun.getSunCounter() == 0) {
            return;
        }

        if (plantsImagesVbox != null && !plantsImagesVbox.getChildren().isEmpty()) {
            targetNode = (Node) event.getTarget();
            if (targetNode instanceof ImageView) {
                objectType = targetNode.getId();
                switch (objectType) {
                    case "potato":
                        if (sun.getSunCounter() >= nutCost) {
                            thisCost = nutCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/WallNutGif.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "peashooterPlant":
                        if (sun.getSunCounter() >= peaShooterCost) {
                            thisCost = peaShooterCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/PeaShooterGif.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "sunflowerPlant":
                        if (sun.getSunCounter() >= sunFlowerCost) {
                            thisCost = sunFlowerCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/SunflowerGif.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "explosivePlant":
                        if (sun.getSunCounter() >= potatoMineCost) {
                            thisCost = potatoMineCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/PotatoMineGif.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "NightSunPlant":
                        if (sun.getSunCounter() >= nightSunPlantCost) {
                            thisCost = nightSunPlantCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/nightgSunPlant.png"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "doublePeaShooter":
                        if (sun.getSunCounter() >= repeaterCost) {
                            thisCost = repeaterCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/RepeaterGif.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "cherryBomb":
                        if (sun.getSunCounter() >= cherryBombCost) {
                            thisCost = cherryBombCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/cherryBomb.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    case "Cactus":
                        if (sun.getSunCounter() >= CactusCost) {
                            thisCost = CactusCost;
                            Glow(targetNode, 0.4);
                            dragImageView = new ImageView(new Image("plantsvszombies/plants/cactus.gif"));
                        } else {
                            dragImageView = null;
                        }
                        break;
                    default:
                        System.out.println("Invalid image ID: " + objectType);
                        break;
                }
            }
        }

        if (dragImageView != null) {
            dragImageView.setFitHeight(100);
            dragImageView.setFitWidth(100);
            if (objectType.equals("potato")) {
                dragImageView.setFitHeight(80);
                dragImageView.setFitWidth(80);
            }
            dragImageView.setX(event.getSceneX() - 50);
            dragImageView.setY(event.getSceneY() - 50);
            scenePane.getChildren().add(dragImageView);
        }
    }

    @FXML
    public void handleMouseDragged(MouseEvent event) {
        if (dragImageView != null) {
            dragImageView.setX(event.getSceneX() - 50);
            dragImageView.setY(event.getSceneY() - 50);
        }
    }

    private int posX;
    private int posY;
    private int plantsPlaces[][] = new int[5][9];

    @FXML
    public void handleMouseReleased(MouseEvent event) throws FileNotFoundException {
        if (sun.getSunCounter() == 0) {
            return;
        }
        if (dragImageView != null) {
            Glow(targetNode, 0);
            Bounds gridBounds = gridPane.localToScene(gridPane.getBoundsInLocal());
            double gridX_max = gridBounds.getMaxX();
            double gridY_max = gridBounds.getMaxY();
            double cellWidth = gridPane.getWidth() / gridPane.getColumnConstraints().size();
            double gridX = gridBounds.getMinX();
            double gridY = gridBounds.getMinY();
            double cellHeight = gridPane.getHeight() / gridPane.getRowConstraints().size();
            boolean onLeftBoundary = event.getSceneX() >= gridX;
            boolean onRightBoundary = event.getSceneX() <= gridX_max;
            boolean onTopBoundary = event.getSceneY() >= gridY;
            boolean onBottomBoundary = event.getSceneY() <= gridY_max;
            if (onLeftBoundary && onRightBoundary && onTopBoundary && onBottomBoundary) {
                int coulmnIndex = (int) ((event.getSceneX() - gridX) / cellWidth);
                int rowIndex = (int) ((event.getSceneY() - gridY) / cellHeight);
                posX = posX(coulmnIndex);
                posY = posY(rowIndex);
                if (plantsPlaces[rowIndex][coulmnIndex] == 0) {
                    plantsPlaces[rowIndex][coulmnIndex] = 1;
                    switch (objectType) {
                        case "potato":
                            Nut nut = new Nut(gamelanes, posX, posY);
                            Plants.plantsList.add(nut.getNutImageView());
                            break;
                        case "peashooterPlant":
                            PeaShooter peashooter = new PeaShooter(gamelanes, posX, posY);
                            Plants.plantsList.add(peashooter.getShooterImageView());
                            break;
                        case "sunflowerPlant":
                            sun = new Sun(gamelanes, sunCounterLabel);
                            SunFlower sunFlower = new SunFlower(gamelanes, posX, posY, sun);
                            Plants.plantsList.add(sunFlower.getSunflowerImageView());
                            break;
                        case "explosivePlant":
                            potatomine pm = new potatomine(gamelanes, posX, posY);
                            Plants.plantsList.add(pm.getMineImageView());
                            break;
                        case "NightSunPlant":
                            sun = new Sun(gamelanes, sunCounterLabel);
                            NightSunPlant nightSunPlant = new NightSunPlant(gamelanes, posX, posY, sun);
                            Plants.plantsList.add(nightSunPlant.getNightPlantImageView());
                            break;
                        case "doublePeaShooter":
                            repeater doublePeaShooter = new repeater(gamelanes, posX, posY);
                            Plants.plantsList.add(doublePeaShooter.getRepeaterImageView());
                            break;
                        case "cherryBomb":
                            CherryBomb cherryBomb = new CherryBomb(gamelanes, posX, posY);
                            plantsPlaces[rowIndex][coulmnIndex] = 0;
                            Plants.plantsList.add(cherryBomb.getCherryBombImageView());
                            break;
                        case "Cactus":
                            Cactus cactus = new Cactus(gamelanes, posX, posY);
                            Plants.plantsList.add(cactus.getCactusImageView());
                            break;
                        default:
                            System.out.println("Invalid image ID: " + objectType);
                            return;
                    }
                    sun.setSunCounter(thisCost);
                    sunCounterLabel.setText(String.valueOf(sun.getSunCounter()));
                }
            }
            scenePane.getChildren().remove(dragImageView);
        }
    }

    public int posX(int j) {
        int x = 0;
        switch (j) {
            case 0:
                x = 150;
                break;
            case 1:
                x = 250;
                break;
            case 2:
                x = 365;
                break;
            case 3:
                x = 475;
                break;
            case 4:
                x = 590;
                break;
            case 5:
                x = 700;
                break;
            case 6:
                x = 800;
                break;
            case 7:
                x = 900;
                break;
            case 8:
                x = 980;
                break;
        }
        return x;
    }

    public int posY(int i) {
        int y = 135 * i;
        return y;
    }

    public void existScene(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pvzscene_1.fxml")));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.close();
        System.exit(0);
    }

    public void initializeProgressBar(int NUM_ZOMBIES) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < NUM_ZOMBIES; i++) {
                    updateProgress(i,NUM_ZOMBIES);
                    Thread.sleep(3000);
                }
                return null;
            }
        };
        if (progressBar != null) {
            progressBar.progressProperty().bind(task.progressProperty());
        }
        new Thread(task).start();
    }

    @FXML
    public TextField playerNameTxt;

    public void handleKeyTyped(KeyEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pvzscene_1.fxml")));

        if (playerNameTxt != null) {
            playerNameTxt.appendText(event.getCharacter());
            event.consume();
            scene = playerNameTxt.getScene();
            if (scene != null) {
                stage = (Stage) scene.getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
