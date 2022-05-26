package main_package;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
public class ChartsBox extends VBox {

    private SingleChartBox sus;
    private SingleChartBox infected;
    private SingleChartBox recovered;
    private SingleChartBox vaccinated;
    private Board board;

    public ChartsBox(Board board) {
        super();
        this.board = board;

        this.sus = new SingleChartBox( board, "sus number", "time", board.getStatistics().getS());
        this.infected = new SingleChartBox(board, "infected number", "time", board.getStatistics().getI());
        this.recovered = new SingleChartBox(board,  "recovered number", "time", board.getStatistics().getR());
        this.vaccinated = new SingleChartBox(board, "recovered number", "time", board.getStatistics().getV());

        VBox allCharts = new VBox(sus, infected, recovered, vaccinated);
        allCharts.setSpacing(10);
        allCharts.setAlignment(Pos.CENTER);
        this.getChildren().add(allCharts);
    }

    public void updateCharts() {
        this.sus.updateChart(board.getStatistics().getS());
        this.infected.updateChart(board.getStatistics().getI());
        this.recovered.updateChart(board.getStatistics().getR());
        this.vaccinated.updateChart(board.getStatistics().getV());
    }


}