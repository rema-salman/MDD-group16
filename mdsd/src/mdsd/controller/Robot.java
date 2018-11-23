package mdsd.controller;

import mdsd.model.Mission;
import project.AbstractRobotSimulator;
import project.Point;

public class Robot extends AbstractRobotSimulator implements IControllableRover {

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

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
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
	public Boolean isFaulty() {
		// TODO Auto-generated method stub
		return null;
	}

}