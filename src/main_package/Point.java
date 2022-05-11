package main_package;

import java.util.ArrayList;
import java.util.Random;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	// not needed for now but i will leave it
	private static Model simulationModel = Model.SIR;
	// same as above
	private final Random random = new Random();

	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public void clicked() {
		currentState = (++currentState) % numStates;
	}

	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {

	}

	public void changeState() {
		currentState = nextState;
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	// Moore neighborhood
	// TODO: maybe dont assign to points at the edges, idk
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

	public void setNextState(int nextState) {
		this.nextState = nextState;
	}
}
