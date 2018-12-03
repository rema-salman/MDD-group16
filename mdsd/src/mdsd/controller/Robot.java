package mdsd.controller;

import mdsd.model.Mission;
import project.AbstractRobotSimulator;
import project.Point;

import java.util.ArrayList;

import javax.vecmath.Point2f;

public class Robot extends AbstractRobotSimulator implements IControllableRover {
    private int id;
    private Point2f position;
    private Mission mission;
    private Point2f[] path;
    private ArrayList<Observer> observers;
    private String name;

    public Robot(Point2f position, String name) {
        super(new project.Point(position.getX(), position.getY()), name);
        this.position = position;
        this.name = name;
    }

    public Robot(Point position, String name) {
        super(position, name);
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
        return null;
    }

    public Point2f getJavaPosition() {
        return position;
    }

    public /*Status*/void getStatus() {
        // TODO Auto-generated method stub
        return /*null*/;
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
    public String getName() {
        return name;
    }
}
