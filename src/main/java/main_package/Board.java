package main_package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 * main_package.Board with Points that may be expanded (with automatic change of cell
 * number) with mouse event listener
 */

public class Board extends JComponent implements MouseInputListener, ComponentListener {
	private Point[][] points;
	private int size = 14;
	private Statistics statistics;
	private int dayNumber = 0;

	private Model simulationModel = Model.SIR;

	public Board(int length, int height) {
		addMouseListener(this);
		addComponentListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setOpaque(true);
		statistics = new Statistics();
		statistics.setS(size*size);
	}

	// single iteration
	public void iteration() {
		for (Point[] point : points)
			for (Point value : point)
				value.setUsed(false);

		for (Point[] point : points) {
			for (Point value : point){
				value.calculateNewState(dayNumber);

				if (value.getState() == Site.S && value.getNextState() == Site.I) statistics.infectOne();
				else if (value.getState() == Site.I && value.getNextState() == Site.R) statistics.recoverOne();

			}
		}

		for (Point[] point : points)
			for (Point value : point)
				value.changeState();

		System.out.println("S " + statistics.getS() + " I " + statistics.getI() + " R " + statistics.getR() + " V " + statistics.getV());
		this.repaint();
		dayNumber += 1;
		statistics.setDays(dayNumber);
	}

	// clearing board
	public void clear() {
		for (Point[] point : points) {
			for (Point value : point) {
				value.setState(Site.S);
				value.setNextState(Site.S);
			}
		}
		this.repaint();
		dayNumber = 0;
	}

	private void initialize(int length, int height) {
		points = new Point[length][height];

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y)
				points[x][y] = new Point();

		assignAllNeighbors();
	}

	//paint background and separators between cells
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g.setColor(Color.GRAY);
		drawNetting(g, size);
	}

	// draws the background netting
	private void drawNetting(Graphics g, int gridSpace) {
		Insets insets = getInsets();
		int firstX = insets.left;
		int firstY = insets.top;
		int lastX = this.getWidth() - insets.right;
		int lastY = this.getHeight() - insets.bottom;

		int x = firstX;
		while (x < lastX) {
			g.drawLine(x, firstY, x, lastY);
			x += gridSpace;
		}

		int y = firstY;
		while (y < lastY) {
			g.drawLine(firstX, y, lastX, y);
			y += gridSpace;
		}

		for (x = 0; x < points.length; ++x) {
			for (y = 0; y < points[x].length; ++y) {
				switch (points[x][y].getState()) {
					case S -> g.setColor(new Color(0xbb00ff));
					case I -> g.setColor(new Color(0xff0000));
					case R -> g.setColor(new Color(0xffffff));
					case V -> g.setColor(new Color(0x00ff00));
				}
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));

			}
		}
		int init_susceptible = 0, init_infected = 0, init_recovered = 0, init_vaccinated = 0;
		for (x = 0; x < points.length; ++x) {
			for (y = 0; y < points[x].length; ++y) {
				switch(points[x][y].getState()) {
					case S -> init_susceptible++;
					case I -> init_infected++;
					case R -> init_recovered++;
					case V -> init_vaccinated++;
				}
			}
		}
		statistics.setS(init_susceptible);
		statistics.setI(init_infected);
		statistics.setR(init_recovered);
		statistics.setV(init_vaccinated);

	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
			points[x][y].clicked();
			this.repaint();
		}
	}

	public void componentResized(ComponentEvent e) {
		int width = (this.getWidth() / size) + 1;
		int height = (this.getHeight() / size) + 1;
		initialize(width, height);
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
			points[x][y].setState(points[x][y].getState().next(simulationModel));
			this.repaint();
		}
	}

	public void setSimulationModel(Model simulationModel) {
		this.simulationModel = simulationModel;
	}

	// TODO: prob not needed, jak będziemy chcieli zmieniać typ neigbhorhood to wtedy
	public void assignAllNeighbors()
	{
		for (int x = 0; x < points.length; ++x) {
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].assignNeighbors(points, points.length, points[0].length, x, y);
			}
		}
	}

	public Statistics getStatistics(){
		return statistics;
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

}
