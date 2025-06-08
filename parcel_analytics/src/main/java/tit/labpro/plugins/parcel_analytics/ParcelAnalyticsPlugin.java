package tit.labpro.plugins.parcel_analytics;

import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.shape.Rectangle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import tit.labpro.core.api.Plugin;
import tit.labpro.core.api.PluginTemplate;
import tit.labpro.core.api.ResizablePlugin;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelAnalyticsPlugin extends PluginTemplate implements Plugin, ResizablePlugin {

    private Object repository;
    private final PieChart chart;

    public ParcelAnalyticsPlugin() {
        chart = new PieChart();
        chart.setTitle("Parcel Status Analytics");
        chart.setLegendSide(Side.RIGHT);
        chart.setPrefSize(200, 150);
        chart.setMinSize(200, 150);
        chart.setMaxSize(600, 600);

        Rectangle clip = new Rectangle();
        chart.setClip(clip);
        chart.layoutBoundsProperty().addListener((obs, old, bounds) -> {
            clip.setWidth(bounds.getWidth());
            clip.setHeight(bounds.getHeight());
        });

        getContentWrapper().getChildren().add(chart);
    }

    private void updateChart() {
        if (repository == null) return;
        try {
            Method getAll = repository.getClass().getMethod("getAll");
            List<?> parcels = (List<?>) getAll.invoke(repository);

            Map<String, Integer> statusCount = new HashMap<>();
            for (Object parcel : parcels) {
                Method getStatus = parcel.getClass().getMethod("getStatus");
                Object status = getStatus.invoke(parcel);
                Method getName = status.getClass().getMethod("getName");
                String name = (String) getName.invoke(status);
                statusCount.put(name, statusCount.getOrDefault(name, 0) + 1);
            }

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : statusCount.entrySet()) {
                pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            chart.setData(pieData);

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

    @Override
    public void setData(Object data) {
        try {
            Method getParcelRepository = data.getClass().getMethod("getParcelRepository");
            Object parcelRepo = getParcelRepository.invoke(data);
            this.repository = parcelRepo;
            updateChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
