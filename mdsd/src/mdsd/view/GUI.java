package mdsd.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mdsd.controller.MainController;
import mdsd.controller.Robot;

import javax.vecmath.Point2f;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GUI extends Application {
    @FXML
    private ListView<HBox> listViewRobots;
    @FXML
    private Button btnStartRobots;
    @FXML
    private Button btnStopRobots;
    @FXML
    private Canvas canvasMap;
    @FXML
    private ListView<VBox> listViewRobotInfo;

    private int count;
    private ArrayList<Robot> robots;
    private GraphicsContext gc;
    private float w;
    private float h;

    private static MainController mainController = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Robot view");
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
        loader.setController(this);

        VBox root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        robots = new ArrayList<>();
        gc = canvasMap.getGraphicsContext2D();
        w = canvasMap.widthProperty().floatValue();
        h = canvasMap.heightProperty().floatValue();

        initMap();

        test();

        initClickListeners();
    }

    private void initMap() {
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, w, h);
        // Center +
        gc.fillRect(w / 2 - 1, h / 8 - 1 + h / 4, 2, h / 4);
        gc.fillRect(w / 8 - 1 + w / 4, h / 2 - 1, w / 4, 2);

        // Top and bottom lines
        gc.fillRect(w / 8 - 1, h / 8 - 1, w - (w / 4), 2);
        gc.fillRect(w / 8 - 1, (h * 7) / 8 - 1, w - (w / 4), 2);

        // Side lines
        gc.fillRect(w / 8 - 1, h / 8 - 1, 2, h / 8);
        gc.fillRect(w / 8 - 1, (h * 6) / 8 - 1, 2, h / 8);
        gc.fillRect((w * 7) / 8 - 1, h / 8 - 1, 2, h / 8);
        gc.fillRect((w * 7) / 8 - 1, (h * 6) / 8 - 1, 2, h / 8);

        // |- shapes
        gc.fillRect(w / 8 - 1, h / 8 - 1 + h / 4, 2, h / 4);
        gc.fillRect((w * 7) / 8 - 1, h / 8 - 1 + h / 4, 2, h / 4);
        gc.fillRect(w / 8 - 1, h / 2 - 1, w / 8 - 1, 2);
        gc.fillRect((w * 6) / 8 - 1, h / 2 - 1, w / 8 - 1, 2);
        gc.fillRect(w / 2 - 1, h / 8 - 1, 2, h / 8 - 1);
        gc.fillRect(w / 2 - 1, (h * 6) / 8 - 1, 2, h / 8 - 1);
    }

    public static void init(MainController mainController) {
        GUI.mainController = mainController;
    }

    private void initClickListeners() {
        btnStartRobots.setOnAction(event -> startRobots());
        btnStopRobots.setOnAction(even -> stopRobots());
        listViewRobots.setOnMouseClicked(event -> {
            HBox item = listViewRobots.getSelectionModel().getSelectedItem();
            if (item == null) {
                return;
            }
            Label name = (Label) item.lookup("#robotName");
            if (name == null) {
                return;
            }
            Robot robot = null;
            for (Robot r : robots) {
                if (r.getName().equals(name.getText())) {
                    robot = r;
                    break;
                }
            }
            if (robot != null) {
                updateInfo(robot);
            }
        });
        canvasMap.setOnMouseClicked(this::onMapClicked);
    }

    private void updateInfo(Robot robot) {
        listViewRobotInfo.getItems().clear();
        VBox vBox = new VBox(
                new Label("Name: " + robot.getName()),
                new Label("Position: " + (int) robot.getJavaPosition().x + ", " + (int) robot.getJavaPosition().y),
                new Label("Mission: " + robot.getMission()));

        listViewRobotInfo.getItems().add(vBox);
    }

    private void onMapClicked(MouseEvent e) {
        int xPos = (int) e.getX();
        int yPos = (int) e.getY();
        for (Robot r : robots) {
            Rectangle rect = new Rectangle((int) r.getJavaPosition().x - 5, (int) r.getJavaPosition().y - 5, 10, 10);
            if (rect.contains(xPos, yPos)) {
                updateInfo(r);
                break;
            }
        }
    }

    private void startRobots() {
        mainController.startAllRovers();
        System.out.println("Clicked start");
    }

    private void stopRobots() {
        mainController.stopAllRovers();
        System.out.println("Clicked stop");
    }

    private void test() {
        count = 0;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    float x = (float) (Math.random() * w);
                    float y = (float) (Math.random() * h);
                    addRobot(new Robot(new Point2f(x, y), "Robot " + count++));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateGUI();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 0, 3000);
    }

    private void updateGUI() {
        initMap();
        for (Robot r : robots) {
            float x = r.getJavaPosition().getX();
            float y = r.getJavaPosition().getY();
            gc.setFill(Color.GREEN);
            gc.fillRect(x - 5, y - 5, 10, 10);
        }
    }

    private void addRobot(Robot robot) throws IOException {
        this.robots.add(robot);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RobotListItem.fxml"));
        HBox root = loader.load();

        Platform.runLater(() -> {
                    listViewRobots.getItems().add(root);
                    Label name = (Label) root.lookup("#robotName");
                    Label position = (Label) root.lookup("#robotPosition");
                    name.setText(robot.getName());
                    int x = (int) robot.getJavaPosition().getX();
                    int y = (int) robot.getJavaPosition().getY();
                    position.setText(x + ", " + y);
                }
        );

    }

}
