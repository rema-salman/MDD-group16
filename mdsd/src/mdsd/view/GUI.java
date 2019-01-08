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
import mdsd.model.Area;
import mdsd.model.Mission;
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
    private VBox vBoxDetails;

    private GraphicsContext gc;
    private ArrayList<IControllableRover> robots;
    private float w;
    private float h;
    private float halfX;
    private float halfY;
    private float scaleX;
    private float scaleY;
    private int selectedRover;

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
        gc.setStroke(Color.rgb(0, 0, 0, 0.4));
        gc.setLineWidth(0.3);
        w = canvasMap.widthProperty().floatValue(); // 460
        h = canvasMap.heightProperty().floatValue(); // 412
        halfX = w / 2f;
        halfY = h / 2f;

        mainController = MainController.getInstance();
        robots = new ArrayList<>();
        selectedRover = -1;

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

        scaleX = Math.abs(w / envW);
        scaleY = Math.abs(h / envH);

        System.out.println("Scaling: scaleX: " + scaleX + ", scaleY: " + scaleY);
    }

    /**
     * Draws the environment
     */
    private void drawEnvironment() {
        gc.clearRect(0, 0, w, h);

        gc.setFill(Color.rgb(120, 0, 0, 0.1));
        drawArea(mainController.getEnvironment().getPhysicalAreas());
        gc.setFill(Color.rgb(0, 120, 0, 0.1));
        drawArea(mainController.getEnvironment().getLogicalAreas());

        for (Obstacle o : mainController.getEnvironment().getObstacles()) {
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

    private void drawArea(List<Area> areas) {
        for (Area area : areas) {
            List<Point2f> areaPoints = area.getAreaPoints();

            double[] xPoints = new double[areaPoints.size()];
            double[] yPoints = new double[areaPoints.size()];

            for (int i = 0; i < xPoints.length; i++) {
                xPoints[i] = halfX + scaleX(areaPoints.get(i).x);
                yPoints[i] = halfY + scaleY(areaPoints.get(i).y);
                if (i > 0) {
                    gc.strokeLine(xPoints[i - 1], yPoints[i - 1], xPoints[i], yPoints[i]);
                }
            }
            gc.strokeLine(xPoints[xPoints.length - 1], yPoints[xPoints.length - 1], xPoints[0], yPoints[0]);
            gc.fillPolygon(xPoints, yPoints, xPoints.length);
        }
    }

    /**
     * Redraw the map and robot positions
     */
    private void updateGUI() {
        drawEnvironment();
        List<IControllableRover> roverList = mainController.getRoverList();
        for (int i = 0; i < roverList.size(); i++) {
            IControllableRover r = roverList.get(i);
            final float width = 15f;
            final float x = halfX + scaleX(r.getJavaPosition().getX()) - width / 2;
            final float y = halfY + scaleY(r.getJavaPosition().getY()) - width / 2;

            if (r.getStatus().stopped) {
                gc.setFill(Color.INDIANRED);
            } else {
                gc.setFill(Color.GREEN);
            }

            gc.fillOval(x, y, width, width);

            if (selectedRover == i) {
                gc.setFill(Color.CYAN);
                gc.fillOval(x + 2.5, y + 2.5, 10, 10);
                updateInfo(r.getStatus());
            }

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
        selectedRover = itemIndex;
    }

    /**
     * If a robot was clicked, show its details/status
     *
     * @param robotStatus the status of the clicked robot
     */
    private void updateInfo(Robot.Status robotStatus) {
        if (listViewRobotInfo.getItems().size() == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RobotListItemDetails.fxml"));
            try {
                vBoxDetails = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        Platform.runLater(() -> {
                    if (listViewRobotInfo.getItems().size() == 0) {
                        listViewRobotInfo.getItems().add(vBoxDetails);
                    }
                    Label id = (Label) vBoxDetails.lookup("#robotId");
                    Label name = (Label) vBoxDetails.lookup("#robotName");
                    Label position = (Label) vBoxDetails.lookup("#robotPosition");
                    Label missionPoints = (Label) vBoxDetails.lookup("#robotMPoints");
                    Label destination = (Label) vBoxDetails.lookup("#robotDestination");
                    Label stopped = (Label) vBoxDetails.lookup("#robotStopped");
                    id.setText(String.valueOf(robotStatus.id));
                    name.setText(robotStatus.name);
                    final Point2f point = robotStatus.position;
                    position.setText(point.x + ", " + point.y);
                    Mission mission = robotStatus.mission;
                    Point2f dest = robotStatus.destination;
                    if (mission != null) {
                        missionPoints.setText(String.valueOf(mission.getNumberOfPoints()));
                    } else {
                        missionPoints.setText("-");

                    }
                    if (dest != null) {
                        destination.setText(dest.x + ", " + dest.y);
                    } else {
                        destination.setText("-");
                    }
                    stopped.setText(String.valueOf(robotStatus.stopped));
                }
        );
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
                selectedRover = robots.indexOf(r);
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
                    Label name = (Label) root.lookup("#robotName");
                    id.setText(String.valueOf(robot.getId()));
                    name.setText(robot.toString());
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
