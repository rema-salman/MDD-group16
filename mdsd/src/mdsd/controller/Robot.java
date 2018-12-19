package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Environment;
import mdsd.model.Mission;
import project.AbstractRobotSimulator;
import project.Point;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.List;

public class Robot extends AbstractRobotSimulator implements IControllableRover {
    private int id;
    private static int idCount = 0;
    private Mission mission;
    private Point2f[] path;
    private ArrayList<Observer> observers;
    private Point destination;
    private List<Area> currentRooms;
    private Environment environment;
    private boolean stopped;

    public Robot(Point position, String name, Environment environment) {
        super(position, name);
        this.destination = position;
        this.environment = environment;
        currentRooms = new ArrayList<>();
        stopped = false;
        synchronized (this) {
            id = idCount++;
        }
    }

    public Robot(Point2f position, String name, Environment environment) {
        this(new project.Point(position.getX(), position.getY()), name, environment);
    }

    @Override
    public String toString() {
        return "Robot " + this.getName();
    }

    @Override
    public void setMission(Mission mission) {
        this.mission = mission;
        if (mission != null) {
            setDestination(mission.getNextPoint());
        }
    }

    @Override
    public Mission getMission() {
        return mission;
    }

    @Override
    public Point getPosition() {
        return super.getPosition();
    }

    public void update() {
        if (mission != null) {
            if (this.isAtPosition(destination)) {
                Point2f newPoint = mission.getNextPoint();
                if (newPoint != null) {
                    setDestination(newPoint);
                    start();
                }
            }
        }
    }

    @Override
    public Point2f getJavaPosition() {
        Point point = super.getPosition();
        return new Point2f((float) point.getX(), (float) point.getZ());
    }

    public Status getStatus() {
        return new Status(this);
    }

    @Override
    public void start() {
        stopped = false;
        setDestination(destination);
    }

    /**
     * Stops the rover until start() is called
     */
    @Override
    public void stop() {
        stopped = true;
        setDestination(getPosition());
    }

    @Override
    public String[] getFaults() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isFaulty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void reportEvent(Object event) {
        // TODO
    }

    public void setDestination(Point2f dest) {
        destination = new project.Point(dest.getX(), dest.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Robot) {
            Robot r2 = (Robot) obj;
            return this.id == r2.id;
        }
        return false;
    }

    @Override
    public List<Area> getRooms() {
        return currentRooms;
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (true) {
                update();
                Point2f roverPos = getJavaPosition();
                List<Area> lastRooms = new ArrayList<>(currentRooms);
                boolean newRoom = false;
                List<Area> newAreas = new ArrayList<>();

                for (Area area : environment.getAreas()) { // Check all areas if a new has been entered or left
                    if (area.contains(roverPos)) {
                        if (!lastRooms.contains(area)) {
                            // Entered a new room
                            currentRooms.add(area);
                            area.enter(this);
                            for (Area area2 : environment.getAreas()) {
                                if (!area2.equals(area)) { // Leave all old areas
                                    area2.leave(this);
                                }
                            }
                            newRoom = true;
                            newAreas.add(area);
                        }
                    } else {
                        if (lastRooms.contains(area)) {
                            // Left a room
                            currentRooms.remove(area);
                            area.leave(this);
                        }
                    }
                }
                if (newRoom) { // If entered a new room
                    setDestination(getPosition()); // Stop without setting the stopped boolean
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (stopped) { // If the stop button was pressed in the GUI, wait until start is pressed
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (!newAreas.isEmpty()) { // Wait for all the new areas to be empty of rovers
                        newAreas.removeIf(area -> area.canEnter(this));
                        if (!newAreas.isEmpty()) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    start();
                }
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class Status {
        public final int id;
        public final Mission mission;
        public final ArrayList<Observer> observers;
        public final Point destination;
        public final List<Area> currentRooms;
        public final Environment environment;
        public final boolean stopped;

        private Status(Robot robot) {
            this.id = robot.id;
            this.mission = robot.mission;
            this.observers = robot.observers;
            this.destination = robot.destination;
            this.currentRooms = robot.currentRooms;
            this.environment = robot.environment;
            this.stopped = robot.stopped;
        }
    }

}
