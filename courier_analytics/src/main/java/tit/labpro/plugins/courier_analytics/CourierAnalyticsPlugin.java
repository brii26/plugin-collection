package tit.labpro.plugins.courier_analytics;

import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import tit.labpro.core.api.Plugin;
import tit.labpro.core.api.PluginTemplate;
import tit.labpro.core.api.ResizablePlugin;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierAnalyticsPlugin extends PluginTemplate implements Plugin, ResizablePlugin {

    private Object shipmentRepository;
    private final BarChart<String, Number> chart;
    private final BorderPane root;

    public CourierAnalyticsPlugin() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Courier");
        yAxis.setLabel("Ongoing Shipments");
        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Courier Ongoing Shipments");
        chart.setLegendSide(Side.RIGHT);
        chart.setBarGap(5);
        chart.setCategoryGap(20);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeRowFillVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAnimated(false);
        chart.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0;");
        root = new BorderPane(chart);
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0;");
        getContentWrapper().setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background-radius: 0;");
        getContentWrapper().getChildren().add(root);
    }

    private void updateChart() {
        if (shipmentRepository == null) return;
        try {
            Method getAllShipments = shipmentRepository.getClass().getMethod("getAll");
            List<?> shipments = (List<?>) getAllShipments.invoke(shipmentRepository);

            Map<String, Integer> domesticMap = new HashMap<>();
            Map<String, Integer> internationalMap = new HashMap<>();

            for (Object shipment : shipments) {
                Method getCourier = shipment.getClass().getMethod("getCourier");
                Object courier = getCourier.invoke(shipment);
                if (courier == null) continue;
                Method getFullName = courier.getClass().getMethod("getFullName");
                String courierName = (String) getFullName.invoke(courier);
                if (shipment.getClass().getSimpleName().equals("DomesticShipment")) {
                    domesticMap.put(courierName, domesticMap.getOrDefault(courierName, 0) + 1);
                } else if (shipment.getClass().getSimpleName().equals("InternationalShipment")) {
                    internationalMap.put(courierName, internationalMap.getOrDefault(courierName, 0) + 1);
                }
            }

            chart.getData().clear();
            XYChart.Series<String, Number> domesticSeries = new XYChart.Series<>();
            domesticSeries.setName("Domestic");
            XYChart.Series<String, Number> internationalSeries = new XYChart.Series<>();
            internationalSeries.setName("International");

            for (String courierName : domesticMap.keySet()) {
                domesticSeries.getData().add(new XYChart.Data<>(courierName, domesticMap.getOrDefault(courierName, 0)));
                internationalSeries.getData().add(new XYChart.Data<>(courierName, internationalMap.getOrDefault(courierName, 0)));
            }

            chart.getData().addAll(domesticSeries, internationalSeries);

            // Set bar colors: green for domestic, orange for international
            for (XYChart.Series<String, Number> series : chart.getData()) {
                String color = series.getName().equals("Domestic") ? "#43a047" : "#ff9800";
                for (XYChart.Data<String, Number> data : series.getData()) {
                    data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                        if (newNode != null) {
                            newNode.setStyle("-fx-bar-fill: " + color + ";");
                        }
                    });
                    if (data.getNode() != null) {
                        data.getNode().setStyle("-fx-bar-fill: " + color + ";");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(Object data) {
        try {
            Method getShipmentRepository = data.getClass().getMethod("getShipmentRepository");
            Object shipmentRepo = getShipmentRepository.invoke(data);
            this.shipmentRepository = shipmentRepo;
            updateChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parent getUI() {
        return this;
    }

    @Override
    public void setSize(double width, double height) {
        setPrefSize(width, height);
    }
}
