package main_package;

import com.sun.javafx.charts.Legend;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
        this.vaccinated = new SingleChartBox(board, "vaccinated number", "time", board.getStatistics().getV());

        for(Node n : this.sus.chart.getChildrenUnmodifiable()){
            if(n instanceof Legend){
                for(Legend.LegendItem legendItem : ((Legend)n).getItems()){ legendItem.getSymbol().setStyle("-fx-background-color: #bb00ff, white;");}
            }
        }
        for(Node n : this.infected.chart.getChildrenUnmodifiable()){
            if(n instanceof Legend){
                for(Legend.LegendItem legendItem : ((Legend)n).getItems()){ legendItem.getSymbol().setStyle("-fx-background-color: #ff0000, white;");}
            }
        }
        for(Node n : this.recovered.chart.getChildrenUnmodifiable()){
            if(n instanceof Legend){
                for(Legend.LegendItem legendItem : ((Legend)n).getItems()){ legendItem.getSymbol().setStyle("-fx-background-color: #000000, white;");}
            }
        }
        for(Node n : this.vaccinated.chart.getChildrenUnmodifiable()){
            if(n instanceof Legend){
                for(Legend.LegendItem legendItem : ((Legend)n).getItems()){ legendItem.getSymbol().setStyle("-fx-background-color: #00ff00, white;");}
            }
        }

        Node line_sus = this.sus.series.getNode().lookup(".chart-series-line");
        Node line_inf = this.infected.series.getNode().lookup(".chart-series-line");
        Node line_rec = this.recovered.series.getNode().lookup(".chart-series-line");
        Node line_vac = this.vaccinated.series.getNode().lookup(".chart-series-line");
        String rgb_sus = String.format("%d, %d, %d", 187, 0, 255);
        String rgb_inf = String.format("%d, %d, %d", 255, 0, 0);
        String rgb_rec = String.format("%d, %d, %d", 0, 0, 0);
        String rgb_vac = String.format("%d, %d, %d", 0, 255, 0);
        line_sus.setStyle("-fx-stroke: rgba(" + rgb_sus + ", 1.0);");
        line_inf.setStyle("-fx-stroke: rgba(" + rgb_inf + ", 1.0);");
        line_rec.setStyle("-fx-stroke: rgba(" + rgb_rec + ", 1.0);");
        line_vac.setStyle("-fx-stroke: rgba(" + rgb_vac + ", 1.0);");

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