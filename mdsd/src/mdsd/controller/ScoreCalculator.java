package mdsd.controller;

/**
 * Thread for calculating the score every 20 seconds
 */
public class ScoreCalculator implements Runnable {

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
