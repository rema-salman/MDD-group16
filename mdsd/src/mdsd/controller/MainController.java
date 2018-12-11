package mdsd.controller;

import mdsd.model.EnvironmentAdoptee;
import mdsd.model.Area;
import java.util.ArrayList;
import java.util.List;

public class MainController implements Observer {
	private List<IControllableRover> rovers;
	private EnvironmentAdoptee environment;
	private ScoreCalculator scoreCalculator;
	//private Procedure procedure;
	private static MainController mainController = null;

	private MainController() {
		rovers = new ArrayList<>();
		environment = new EnvironmentAdoptee();
		scoreCalculator = new ScoreCalculator();
	}

	public static MainController getInstance() {
		if (mainController == null) {
			mainController = new MainController();
		}
		return mainController;
	}

	/**
	 * Main loop for the MainController.
	 */
	public void loopForever() {
		while (true) {
			for (IControllableRover rover : rovers) {
				rover.update();
			}
		}
	}

	public void addRovers(List<IControllableRover> rovers) {
		this.rovers.addAll(rovers);
	}

	// TODO
	public void getAllRoverPositions() {

	}

	// TODO
	public void getRoverPositions(Area area) {

	}

	// TODO
	public int getScore() {
		if (scoreCalculator == null) {
			return 0;
		}
		return scoreCalculator.score;
	}

	// TODO, return copy?
	public EnvironmentAdoptee getEnvironment() {
		return environment;
	}

	// TODO, return type
	public void getRoverStatus() {

	}

	// TODO
	public List<IControllableRover> getRovers() {
		List<IControllableRover> list = new ArrayList<>();
		if (rovers == null) {
			return list;
		}
		for (IControllableRover r : rovers) {
			if (r != null) {
				list.add(r);
			}
		}
		return list;
	}

	// TODO
	public void stopRover(IControllableRover rover) {
		rover.stop();
	}

	public void stop() {
		if (rovers == null) {
			return;
		}
		for (IControllableRover r : rovers) {
			if (r == null) {
				continue;
			}
			r.stop();
		}
	}

	public void start() {
		if (rovers == null) {
			return;
		}
		for (IControllableRover r : rovers) {
			if (r == null) {
				continue;
			}
			r.start();
		}
	}

	@Override
	public void receiveEvent(Object event) {
		// TODO
	}

	protected class ScoreCalculator implements Runnable {
		private IProcedure[] procedures;
		public IProcedure activeProcedure;
		public int score = 0;
		public boolean running = true;

		private void changeProcedure(IProcedure procedure) {
			this.activeProcedure = procedure;
		}

		private void scoreLoop() {

		}

		public void run() {
			while (running) {

				// score += activeProcedure.calculateScore();

				try {
					this.wait(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
