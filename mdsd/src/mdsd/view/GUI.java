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
import mdsd.model.Environment;
import mdsd.model.Obstacle;

import javax.vecmath.Point2f;
import java.awt.*;
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
    private List<Obstacle> obstacles;
    private float w;
    private float h;
    private float halfX;
    private float halfY;
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
        w = canvasMap.widthProperty().floatValue(); // 460
        h = canvasMap.heightProperty().floatValue(); // 412
        halfX = w / 2f;
        halfY = h / 2f;

        mainController = MainController.getInstance();
        environment = mainController.getEnvironment();
        obstacles = new ArrayList<>();
        robots = new ArrayList<>();

        obstacles.addAll(environment.getObstacles());

        setScaling();
        drawEnvironment();
        setClickListeners();
        start();
    }

    /**
     * Scales the environment to fit in the canvas
     */
    private void setScaling() {
        final float envW = mainController.getEnvironment().getWidth() + 1;
        final float envH = mainController.getEnvironment().getHeight() + 1;

        System.out.println("Environment: envW: " + envW + ", envH: " + envH);

        scaleX = Math.abs(w / envW);
        scaleY = Math.abs(h / envH);

        System.out.println("Scaling: scaleX: " + scaleX + ", scaleY: " + scaleY);
    }

    /**
     * Draws the environment
     */
    private void drawEnvironment() {
        gc.clearRect(0, 0, w, h);

        for (Obstacle o : obstacles) {
            java.awt.Color awtColor = o.color;
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;

            javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);
            gc.setFill(fxColor);

            float width = 5;
            float height = 5;
            float x = halfX + scaleX(o.x) - width / 2f;
            float y = halfY + scaleY(o.y) - height / 2f;

            if (o.horizontal) {
                width = scaleX(o.length);
            } else {
                height = scaleY(o.length);
            }

            gc.fillRect(x, y, width, height);
        }
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
            Rectangle rect = new Rectangle((int) (halfX + scaleX(r.getJavaPosition().x) - 5),
                    (int) (halfY + scaleY(r.getJavaPosition().y) - 5), 10, 10);
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
        List<IControllableRover> roverList = mainController.getRoverList();
        for (IControllableRover r : roverList) {
            final float x = halfX + scaleX(r.getJavaPosition().getX()) - 5;
            final float y = halfY + scaleY(r.getJavaPosition().getY()) - 5;
            gc.setFill(Color.GREEN);
            gc.fillOval(x, y, 15, 15); // Draw

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
