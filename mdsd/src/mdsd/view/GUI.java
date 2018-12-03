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
import mdsd.controller.IControllableRover;
import mdsd.controller.MainController;
import mdsd.controller.Robot;
import mdsd.model.Environment;

import javax.vecmath.Point2f;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private Label rewardPoints;

    private GraphicsContext gc;
    private ArrayList<IControllableRover> robots;
    private Environment environment;
    private List<Line2D.Double> obstacles;
    private float w;
    private float h;
    private float scaleX;
    private float scaleY;

    private static MainController mainController = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Robot GUI");
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
        loader.setController(this);

        VBox root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        gc = canvasMap.getGraphicsContext2D();
        w = canvasMap.widthProperty().floatValue();
        h = canvasMap.heightProperty().floatValue();

        mainController = MainController.getInstance();
        environment = mainController.getEnvironment();
        obstacles = new ArrayList<>();
        robots = new ArrayList<>();

        addRobot(new Robot(new Point2f(10, 4), "1")); // TODO remove
        addRobot(new Robot(new Point2f(-4, 10), "2"));

        for (Line2D.Double line : environment.getObstacles()) {
            if (line != null) {
                obstacles.add(line);
            }
        }

        setScaling();
        drawEnvironment();
        setClickListeners();
        start();
    }

    /**
     * Scales the environment to fit in the canvas
     */
    private void setScaling() {
        double xMin = 0;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0;
        for (Line2D.Double obstacle : obstacles) {
            if (obstacle.x1 > xMax) {
                xMax = obstacle.x1;
            }
            if (obstacle.x2 > xMax) {
                xMax = obstacle.x2;
            }
            if (obstacle.x1 < xMin) {
                xMin = obstacle.x1;
            }
            if (obstacle.x2 < xMin) {
                xMin = obstacle.x2;
            }
            if (obstacle.y1 > yMax) {
                yMax = obstacle.y1;
            }
            if (obstacle.y2 > yMax) {
                yMax = obstacle.y2;
            }
            if (obstacle.y1 < yMin) {
                yMin = obstacle.y1;
            }
            if (obstacle.y2 < yMin) {
                yMin = obstacle.y2;
            }
        }

        final float envW = (float) (xMax - xMin);
        final float envH = (float) (yMax - yMin);

        scaleX = w / envW;
        scaleY = h / envH;
    }

    /**
     * Draws the environment
     */
    private void drawEnvironment() {
        // TODO draw from environment

        // The environment from the mission
        // TODO remove
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, w, h);
        // Center +
        gc.fillRect(w / 2 - 1, w / 8 - 1 + w / 4, 2, w / 4);
        gc.fillRect(w / 8 - 1 + w / 4, w / 2 - 1, w / 4, 2);

        // Top and bottom lines
        gc.fillRect(w / 8 - 1, w / 8 - 1, w - (w / 4), 2);
        gc.fillRect(w / 8 - 1, (w * 7) / 8 - 1, w - (w / 4), 2);

        // Side lines
        gc.fillRect(w / 8 - 1, w / 8 - 1, 2, w / 8);
        gc.fillRect(w / 8 - 1, (w * 6) / 8 - 1, 2, w / 8);
        gc.fillRect((w * 7) / 8 - 1, w / 8 - 1, 2, w / 8);
        gc.fillRect((w * 7) / 8 - 1, (w * 6) / 8 - 1, 2, w / 8);

        // |- shapes
        gc.fillRect(w / 8 - 1, w / 8 - 1 + w / 4, 2, w / 4);
        gc.fillRect((w * 7) / 8 - 1, w / 8 - 1 + w / 4, 2, w / 4);
        gc.fillRect(w / 8 - 1, w / 2 - 1, w / 8 - 1, 2);
        gc.fillRect((w * 6) / 8 - 1, w / 2 - 1, w / 8 - 1, 2);
        gc.fillRect(w / 2 - 1, w / 8 - 1, 2, w / 8 - 1);
        gc.fillRect(w / 2 - 1, (w * 6) / 8 - 1, 2, w / 8 - 1);
    }

    private void setClickListeners() {
        btnStartRobots.setOnAction(event -> startRobots());
        btnStopRobots.setOnAction(even -> stopRobots());
        listViewRobots.setOnMouseClicked(event -> onRoverClicked());
        canvasMap.setOnMouseClicked(this::onMapClicked);
    }

    /**
     * When a rover in the list was clicked
     */
    private void onRoverClicked() {
        Integer itemIndex = listViewRobots.getSelectionModel().getSelectedIndices().get(0);
        if (itemIndex == null) {
            return;
        }
        IControllableRover robot = robots.get(itemIndex);

        updateInfo(robot);
    }

    /**
     * If a robot was clicked, show its details/status
     *
     * @param robot the clicked robot
     */
    private void updateInfo(IControllableRover robot) {
        listViewRobotInfo.getItems().clear();
        Point2f point = robot.getJavaPosition();
        if (point == null) {
            point = new Point2f(0, 0);
        }
        VBox vBox = new VBox(
                new Label("Id: " + robot.getId()),
                new Label("Name: " + robot.toString()),
                new Label("Position: " + (int) point.x + ", " + (int) point.y));
        // TODO

        listViewRobotInfo.getItems().add(vBox);
    }

    /**
     * Handles clicks on the map, checking if any robot was clicked
     *
     * @param e the MouseEvent
     */
    private void onMapClicked(MouseEvent e) {
        int xPos = (int) e.getX();
        int yPos = (int) e.getY();
        for (IControllableRover r : robots) {
            Rectangle rect = new Rectangle((int) scaleX(r.getJavaPosition().x) - 5, (int) scaleY(r.getJavaPosition().y) - 5, 10, 10);
            if (rect.contains(xPos, yPos)) {
                updateInfo(r);
                break;
            }
        }
    }

    private float scaleX(float x) {
        return x * scaleX;
    }

    private float scaleY(float y) {
        return y * scaleY;
    }

    /**
     * Starts all the robots
     */
    private void startRobots() {
        mainController.start();
    }

    /**
     * Stops all the robots
     */
    private void stopRobots() {
        mainController.stop();
    }

    /**
     * Starts a task that updates the GUI
     */
    private void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateGUI();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 0, 300);
    }

    /**
     * Redraw the map and robot positions
     */
    private void updateGUI() {
        drawEnvironment();
        List<IControllableRover> roverList = mainController.getRovers();
        if (roverList != null) {
            for (IControllableRover r : roverList) {
                float x = scaleX(r.getJavaPosition().getX());
                float y = scaleY(r.getJavaPosition().getY());
                gc.setFill(Color.GREEN);
                gc.fillRect(x - 5, y - 5, 10, 10); // Draw

                if (!robots.contains(r)) {
                    try {
                        addRobot(r);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int i = 0; i < robots.size(); i++) {
                if (!roverList.contains(robots.get(i))) {
                    removeRobot(robots.get(i), i);
                }
            }
        }

        int score = mainController.getScore();
        Platform.runLater(() -> rewardPoints.setText(String.valueOf(score)));
    }

    /**
     * Adds a robot to the list of robots
     *
     * @param robot the robot to add
     * @throws IOException if the .fxml file is not found
     */
    private void addRobot(IControllableRover robot) throws IOException {
        robots.add(robot);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RobotListItem.fxml"));
        HBox root = loader.load();

        Platform.runLater(() -> {
                    listViewRobots.getItems().add(root);
            Label id = (Label) root.lookup("#robotId");
                    Label position = (Label) root.lookup("#robotPosition");
            id.setText(String.valueOf(robot.getId()));
            Point2f point = robot.getJavaPosition();
            if (point != null) {
                position.setText(point.x + ", " + point.y);
            }

                }
        );
    }

    /**
     * Removes a robot from the list of robots
     *
     * @param robot the robot to remove
     * @param index the robot's index in the list
     */
    private void removeRobot(IControllableRover robot, int index) {
        robots.remove(robot);
        Platform.runLater(() -> listViewRobots.getItems().remove(index));
    }

}
