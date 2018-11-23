package mdsd;

import mdsd.controller.Robot;
import project.AbstractSimulatorMonitor;
import simbad.sim.EnvironmentDescription;

import java.util.Set;

public class SimulatorMonitor extends AbstractSimulatorMonitor<Robot> {

	public SimulatorMonitor(Set<Robot> robots, EnvironmentDescription e) {
		super(robots, e);
	}

	@Override
	public void update(Robot arg0) {
		System.out.println(arg0.getName());
		System.out.println(arg0.getPosition());
	}

}