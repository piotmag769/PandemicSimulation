package main_package;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class Point {
	private ArrayList<Point> neighbors;
	private Site currentState;
	private Site nextState;
	private static double p = 0.5;
	private static double q = 0.1;
	private static double pRecover = q;
	private static double pInfect = 1.0 - Math.pow(1.0 - p, 8);
	private boolean used = false;

	// not needed for now but I will leave it
	private static Model simulationModel = Model.SIR;
	// same as above
	private final Random random = new Random();

	public Point() {
		currentState = Site.S;
		nextState = Site.S;
	}


	public void clicked() {
		currentState = currentState.next(simulationModel);
	}

	public Site getState() {
		return currentState;
	}

	public Site getNextState(){ return nextState; }

	public void setState(Site s) {
		currentState = s;
	}

	public void calculateNewState() {
		if (this.used)
			return;

		switch(simulationModel)
		{
			case SIR -> {
				if(this.currentState == Site.I)
				{
					if (random.nextDouble() < pRecover)
						this.nextState = Site.R;

					else {
						Point randomNeighbor = neighbors.get(random.nextInt(neighbors.size()));
						// if not used (!)
						if (!randomNeighbor.used && randomNeighbor.currentState == Site.S && random.nextDouble() < pInfect)
							randomNeighbor.nextState = Site.I;

						randomNeighbor.used = true;
					}
					this.used = true;
				}
			}
			case SIS -> {
				if(this.currentState == Site.I)
				{
					if (random.nextDouble() < pRecover)
						this.nextState = Site.S;
					else {
						Point randomNeighbor = neighbors.get(random.nextInt(neighbors.size()));
						// if not used (!)
						if (!randomNeighbor.used && randomNeighbor.currentState == Site.S && random.nextDouble() < pInfect)
							randomNeighbor.nextState = Site.I;

						randomNeighbor.used = true;
					}
					this.used = true;
				}
			}
			case SIRV -> {
			}
		}
		// idk
	}

	public void changeState() {
		currentState = nextState;
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	// Moore neighborhood
	// TODO: maybe don't assign to points at the edges, idk
	public void assignNeighbors(Point[][] points, int length, int height, int x, int y) {
		neighbors = new ArrayList<>();
		for (int a = Math.max(0, x - 1); a <= Math.min(length - 1, x + 1); a++)
			for (int b = Math.max(0, y - 1); b <= Math.min(height - 1, y + 1); b++)
				if (a != x || b != y) {
//						System.out.println(x + " " + y + ": " + a + " " + b);
					this.addNeighbor(points[a][b]);
				}
	}

	public static void setSimulationModel(Model simulationModel) {
		Point.simulationModel = simulationModel;
	}

	public void setNextState(Site nextState) {
		this.nextState = nextState;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
