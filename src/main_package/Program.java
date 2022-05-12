package main_package;

import javax.swing.JFrame;

public class Program extends JFrame {

	private GUI gof;

	public Program() {
		setTitle("Pandemic Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gof = new GUI(this);
		gof.initialize(this.getContentPane());

		this.setSize(900, 600);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Program();
	}

}
