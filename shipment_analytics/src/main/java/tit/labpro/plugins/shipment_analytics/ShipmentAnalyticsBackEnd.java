package tit.labpro.plugins.shipment_analytics;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipmentAnalyticsBackEnd {

    public Map<String, Integer> analyzeShipments(List<?> shipments) {
        Map<String, Integer> statusCount = new HashMap<>();

        if (shipments == null || shipments.isEmpty()) return statusCount;

        try {
            Class<?> shipmentClass = shipments.get(0).getClass();
            Method getCurrentStatus = shipmentClass.getMethod("getCurrentStatus");

            Method getName = null;
            Class<?> statusClass = null;

            for (Object shipment : shipments) {
                Object status = getCurrentStatus.invoke(shipment);

                if (status != null) {
                    if (getName == null) {
                        statusClass = status.getClass();
                        getName = statusClass.getMethod("getName");
                    }
                    String name = (String) getName.invoke(status);
                    statusCount.put(name, statusCount.getOrDefault(name, 0) + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusCount;
    }
}
