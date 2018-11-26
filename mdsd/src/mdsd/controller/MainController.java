package mdsd.controller;

import mdsd.model.Area;
import mdsd.model.Environment;

import java.util.List;

public class MainController {

	private IControllableRover [] rovers;

	private Environment environment;

	// TODO
	public void getAllRoverPositions(){

	}

	// TODO
	public void getRoverPositions(Area area){

	}

	// TODO
	public int getScore(){
		return 0;
	}

	// TODO, return copy?
	public Environment getEnvironment(){
		return environment;
	}

	// TODO, return type
	public void getRoverStatus(){

	}

	// TODO
	public List<IControllableRover> getRovers(){
		return null;
	}


	// TODO
	public void stopRover (IControllableRover rover){

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
