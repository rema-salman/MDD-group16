package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Mission;
import javax.vecmath.Point2f;

/**
 * A rover that can be controlled by an operator.
 *
 */
public interface IControllableRover extends Observable {

    /*
     * Set a new mission for the rover to execute.
     */
    void setMission(Mission mission);

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
     * Get a description of all faults of the rover, if any.
     */
    String[] getFaults();

    /*
     * Check if the rover is faulty or not.
     */
    boolean isFaulty();

    int getId();

    public void setRoom(Area newArea);

    public Area getRoom();
}
