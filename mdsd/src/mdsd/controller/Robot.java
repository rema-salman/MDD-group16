package mdsd.controller;

import mdsd.model.EnvironmentAdoptee;
import mdsd.model.Mission;
import project.AbstractRobotSimulator;
import project.Point;

import javax.vecmath.Point2f;
import java.util.ArrayList;

public class Robot extends AbstractRobotSimulator implements IControllableRover {
    private int id;
    private int rewardPoints;
    private static int idCount = 0;
    private Mission mission;
    private Point2f[] path;
    private ArrayList<Observer> observers;

    public Robot(Point2f position, String name) {
        super(new project.Point(position.getX(), position.getY()), name);
        id = idCount++;
    }

    public Robot(Point position, String name) {
        super(position, name);
        id = idCount++;
    }

    @Override
    public String toString() {
        return "Robot " + this.getName();
    }

    @Override
    public void setMission(Mission mission) {
        // TODO Auto-generated method stub

    }

    @Override
    public Mission getMission() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Point getPosition() {
        // TODO Auto-generated method stub
        return super.getPosition();
    }

    public Point2f getJavaPosition() {
        Point point = super.getPosition();
        return new Point2f((float) point.getX(), (float) point.getZ());
    }

    public /* Status */void getStatus() {
        // TODO Auto-generated method stub
        return /* null */;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

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
        setDestination(new project.Point(dest.getX(), dest.getY()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Robot) {
            Robot r2 = (Robot) obj;
            return this.id == r2.id;
        }
        return false;
    }

    @Override
    public int getRewardPoints() {
        return rewardPoints;

    }

    @Override
    /**
     * 
     * @param points
     */
    public void addRewardPoints(int newRewardPoints) {
        rewardPoints += newRewardPoints;
    }

    public EnvironmentAdoptee inEnvironment;

    public EnvironmentAdoptee getEnvironment() {
        return this.inEnvironment;

    }

}
