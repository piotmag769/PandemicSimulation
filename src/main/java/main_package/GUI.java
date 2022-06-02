package main_package;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class containing main_package.GUI: board + buttons
 */
public class GUI extends JPanel implements ActionListener, ChangeListener {

	private Timer timer;
	private Board board;

	private JComboBox<String> simulationList;
	private JSlider pSlider;
	private JSlider qSlider;

	private JButton start;
	private JButton clear;
	private JSlider pred;

	private ChartsBox statsBox;

	private final JFrame frame;
	private int iterNum = 0;
	private final int maxDelay = 500;
	private final int initDelay = 100;
	private boolean running = false;
	private boolean allCreated = false;

	public GUI(JFrame jf) {
		frame = jf;
		timer = new Timer(initDelay, this);
		timer.stop();
	}

	/**
	 * @param container to which main_package.GUI and board is added
	 */
	public void initialize(Container container) {
		container.setLayout(new BorderLayout());
		container.setSize(new Dimension(1024, 768));

		JPanel buttonPanel = new JPanel();

		// TODO: maybe more, idk
		simulationList = new JComboBox<>(new String[]{"SIR", "SIS", "SIRV"});
		simulationList.setActionCommand("simulation changed");
		simulationList.addActionListener(this);

		// WHY DOESN'T IT WORK
		pSlider = new JSlider(0, 100, 20);
		pSlider.setToolTipText("p value");
		pSlider.setMajorTickSpacing(20);
		pSlider.setMinorTickSpacing(5);
		pSlider.setPaintTicks(true);
		pSlider.setPaintLabels(true);
		pSlider.addChangeListener(this);

		qSlider = new JSlider(0, 100, 20);
		qSlider.setToolTipText("p value");
		qSlider.setMinorTickSpacing(20);
		qSlider.setMinorTickSpacing(5);
		qSlider.setPaintTicks(true);
		qSlider.setPaintLabels(true);
		qSlider.addChangeListener(this);

		start = new JButton("Start");
		start.setActionCommand("Start");
		start.setToolTipText("Starts clock");
		start.addActionListener(this);


		clear = new JButton("Clear");
		clear.setActionCommand("clear");
		clear.setToolTipText("Clears the board");
		clear.addActionListener(this);

		pred = new JSlider();
		pred.setMinimum(0);
		pred.setMaximum(maxDelay);
		pred.setToolTipText("Time speed");
		pred.addChangeListener(this);
		pred.setValue(maxDelay - timer.getDelay());

		buttonPanel.add(pSlider);
		buttonPanel.add(qSlider);

		buttonPanel.add(simulationList);

		buttonPanel.add(start);
		buttonPanel.add(clear);
		buttonPanel.add(pred);

		board = new Board(1024, 768 - buttonPanel.getHeight());


		JFXPanel panel = new JFXPanel();

		Platform.runLater(() -> {
			statsBox  = new ChartsBox(board);
			StackPane stack = new StackPane();
			Scene scene = new Scene(stack,300,300);
			panel.setScene(scene);
			stack.getChildren().add(statsBox);

		});

		container.add(board, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);


		container.add(panel, BorderLayout.WEST);

		allCreated = true;
	}

	/**
	 * handles clicking on each button
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(timer)) {
			iterNum++;
			frame.setTitle(simulationList.getSelectedItem() + " (" + Integer.toString(iterNum) + " iteration)");
			board.iteration();

			Platform.runLater(() -> statsBox.updateCharts());

		} else {
			String command = e.getActionCommand();
			switch (command) {
				case "Start" -> {
					if (!running) {
						timer.start();
						start.setText("Pause");
					} else {
						timer.stop();
						start.setText("Start");
					}
					running = !running;
					clear.setEnabled(true);
				}
				case "clear" -> {
					iterNum = 0;
					timer.stop();
					running = false;
					start.setEnabled(true);
					board.clear();
					frame.setTitle("Pandemic Simulation");
					start.setText("Start");
				}
				case "simulation changed" -> {
					// reset simulation
					iterNum = 0;
					timer.stop();
					running = false;
					start.setEnabled(true);
					board.clear();
					frame.setTitle("Pandemic Simulation");
					start.setText("Start");
					String s = (String) simulationList.getSelectedItem();
					Model simulationModel = switch (s) {
						case "SIR" -> Model.SIR;
						case "SIS" -> Model.SIS;
						case "SIRV" -> Model.SIRV;
						default -> throw new IllegalStateException("Unexpected demo box value: " + s);
					};

					this.board.setSimulationModel(simulationModel);
					Point.setSimulationModel(simulationModel);
					// TODO: prob not needed, jak będziemy chcieli zmieniać typ neigbhorhood to wtedy
//					board.assignAllNeighbors();
				}
			}
		}
	}

	/**
	 * slider to control simulation speed
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		// TODO
		if(allCreated){
		timer.setDelay(maxDelay - pred.getValue());
		}
	}
}
