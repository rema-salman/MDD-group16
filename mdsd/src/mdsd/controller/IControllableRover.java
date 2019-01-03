package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Mission;

import javax.vecmath.Point2f;
import java.util.List;

/**
 * A rover that can be controlled by an operator.
 */
public interface IControllableRover extends Observer {

    /*
     * Set a new mission for the rover to execute.
     */
    void setMission(Mission mission);

    /**
     * "Main loop" function for the rover, updates rover with data from simulator.
     */
    void update();

    /*
     * Get the current mission of the rover.
     */
    Mission getMission();

    /*
     * Get the current position for the rover.
     */
    Point2f getJavaPosition();

    /*
     * Start executing the current mission, if there is any.
     */
    void start();

    /*
     * Stop whatever the rover is doing.
     */
    void stop();

    /*
     * Check if the rover is faulty or not.
     */
    boolean isFaulty();

    int getId();

    List<Area> getRooms();

    void run();

    Robot.Status getStatus();

    String[] getFaults();

}
