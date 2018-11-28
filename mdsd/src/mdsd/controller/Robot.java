package mdsd.controller;

import mdsd.model.Mission;
import project.AbstractRobotSimulator;

import java.awt.*;
import java.util.ArrayList;

public class Robot extends AbstractRobotSimulator implements IControllableRover {
    private int id;
    private Point position;
    private Mission mission;
    private Point[] path;
    private ArrayList<Observer> observers;

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
}