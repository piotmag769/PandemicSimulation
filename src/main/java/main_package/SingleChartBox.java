package main_package;

//import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class SingleChartBox extends VBox {

    protected final NumberAxis xAxis = new NumberAxis();
    protected final NumberAxis yAxis = new NumberAxis();
    protected final LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);
    protected final XYChart.Series series = new XYChart.Series();
    protected double yDataToAdd;
    private Board board;

    public SingleChartBox(Board board, String chartName, String xAxisName, double yDataToAdd) {
        super();
        this.board = board;
        this.xAxis.setLabel(xAxisName);
        this.series.setName(chartName);
        this.yDataToAdd = yDataToAdd;
        this.series.getData().add(new XYChart.Data(board.getStatistics().getDay(), yDataToAdd));
        this.chart.getData().add(this.series);
        this.chart.setCreateSymbols(false);
        //this.chart.setLegendVisible(false);
        this.chart.setMaxSize(300, 230);
        this.getChildren().add(this.chart);
    }

    public void updateChart(double yDataToAdd) {
        XYChart.Data newData = new XYChart.Data(board.getStatistics().getDay(), yDataToAdd);
        this.yDataToAdd = yDataToAdd;
        this.series.getData().add(newData);
        this.chart.setMaxSize(300, 230);

    }

}
