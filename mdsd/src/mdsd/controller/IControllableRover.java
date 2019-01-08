package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Mission;

import javax.vecmath.Point2f;
import java.util.List;

/**
 * A rover that can be controlled by an operator.
 */
public interface IControllableRover extends Observable {

    /**
     * Set a new mission for the rover to execute.
     *
     * @param mission the new mission to execute
     */
    void setMission(Mission mission);

    /**
     * "Main loop" function for the rover, updates rover with data from simulator.
     */
    void update();

    /**
     * Get the current mission of the rover.
     *
     * @return the current mission
     */
    Mission getMission();

    /**
     * Get the current position for the rover.
     *
     * @return the rover's current position
     */
    Point2f getJavaPosition();

    /**
     * Starts a rover if the rover is not waiting to enter an area
     */
    void start();

    /**
     * Stops the rover until start() is called
     */
    void stop();

    /**
     * If a fault has been detected in the rover
     *
     * @return true if a fault is detected
     */
    boolean isFaulty();

    /**
     * Returns the id of the rover
     *
     * @return the id
     */
    int getId();

    /**
     * Returns the rooms the rover is currently in
     *
     * @return a list of rooms
     */
    List<Area> getRooms();

    /**
     * This method is responsible of the rover performance of the mission and
     * checking the restrictions, such one rover per room and the waiting time until
     * the other leaves
     */
    void run();

    /**
     * Returns the status of the rover
     *
     * @return the status
     */
    Robot.Status getStatus();

    /**
     * Returns a list of the current faults in the rover
     *
     * @return a non-empty list if faults are detected
     */
    List<String> getFaults();

}
