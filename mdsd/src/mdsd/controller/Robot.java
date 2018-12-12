package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Environment;
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
    private Point destination;
    private Area currentArea;

    public Robot(Point position, String name) {
        super(position, name);
        this.destination = position;
        id = idCount++;
    }

    public Robot(Point2f position, String name) {
        this(new project.Point(position.getX(), position.getY()), name);
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Point getPosition() {
        // TODO Auto-generated method stub
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
        setDestination(destination);
    }

    @Override
    public void stop() {
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

    public Environment inEnvironment;

    public Environment getEnvironment() {
        return this.inEnvironment;

    }

    public Area getArea() {
        return currentArea;
    }
}
