package main_package;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChartsBox extends VBox {

    private SingleChartBox sus;
    private SingleChartBox infected;
    private SingleChartBox recovered;
    private SingleChartBox vaccinated;
    private Board board;
    private Statistics statistics;

    public ChartsBox(Statistics statistics) {
        super();
        this.statistics = statistics;

        this.sus = new SingleChartBox( "sus number", "time", statistics.getS());
        this.infected = new SingleChartBox("infected number", "time", statistics.getI());
        this.recovered = new SingleChartBox("recovered number", "time", statistics.getR());
        this.vaccinated = new SingleChartBox("recovered number", "time", statistics.getV());

        VBox allCharts = new VBox(sus, infected, recovered, vaccinated);
        allCharts.setSpacing(10);
        allCharts.setAlignment(Pos.CENTER);
        this.getChildren().add(allCharts);
    }

    public void updateCharts() {
        this.sus.updateChart(statistics.getS());
        this.infected.updateChart(statistics.getI());
        this.recovered.updateChart(statistics.getR());
        this.vaccinated.updateChart(statistics.getV());
    }


}
