package mdsd.controller;

import mdsd.model.Mission;
import project.Point;

/**
 * A rover that can be controlled by an operator.
 * @author bondi
 *
 */
public interface ControllableRover {
	
	/*
	 * Set a new mission for the rover to execute.
	 */
	public void setMission(Mission mission);
	
	/*
	 * Get the current mission of the rover.
	 */
	public Mission getMission();
	
	/*
	 * Get the current position for the rover.
	 */
	public Point getPosition();
	
	/*
	 * Start executing the current mission, if there is any.
	 */
	public void start();
	
	/*
	 * Stop whatever the rover is doing.
	 */
	public void stop();
	
	/*
	 * Get a description of all faults of the rover, if any.
	 */
	public String[] getFaults();
	
	/*
	 * Check if the rover is faulty or not.
	 */
	public Boolean isFaulty();
}
