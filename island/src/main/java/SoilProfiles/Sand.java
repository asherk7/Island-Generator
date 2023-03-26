package SoilProfiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class Sand implements AbsProfile{
    public final double SAND_ADJUSTMENT = -0.1;
    @Override
    public void absorption(List<Structs.Polygon.Builder> polygons) {
        for (int i = 0; i < polygons.size() -1; i++) {
            Structs.Polygon.Builder p = polygons.get(i);
            double newHumidity = calcHumidityScore(p, polygons);
            replaceHumidity(newHumidity, polygons, i);
        }
    }
    private double calcHumidityScore(Structs.Polygon.Builder polygon, List<Structs.Polygon.Builder> polygons) {
        double humidityScore = 0;
        double x1 = 0, y1 = 0, x2, y2;
        int oldHumidity = 0;
        for (Structs.Property p: polygon.getPropertiesList()) {
            if (p.getKey().equals("Centroid")) {
                String[] coord = p.getValue().split(",");
                x1 = Double.parseDouble(coord[0]);
                y1 = Double.parseDouble(coord[1]);
            }
        }

        for (Structs.Property p: polygon.getPropertiesList()) {
            if (p.getKey().equals("Humidity")) {
                oldHumidity = Integer.parseInt(p.getValue());
            }
        }

        for (Structs.Polygon.Builder poly: polygons) {
            for (Structs.Property p : poly.getPropertiesList()) {
                if ((p.getKey().equals("Biome") && (p.getValue().equals("lake") || p.getValue().equals("river")))
                        || p.getKey().equals("Aquifer")) {
                    for (Structs.Property h: poly.getPropertiesList()) {
                        if (h.getKey().equals("Centroid")) {
                            String[] coord = p.getValue().split(",");
                            x2 = Double.parseDouble(coord[0]);
                            y2 = Double.parseDouble(coord[1]);
                            double dy = Math.abs(y2 - y1);
                            double dx = Math.abs(x2 - x1);
                            humidityScore += Math.hypot(dx, dy);
                        }
                    }
                }
            }
        }
        return (humidityScore * SAND_ADJUSTMENT + oldHumidity);
    }

    private void replaceHumidity(double newHumidity, List<Structs.Polygon.Builder> polygons, int index) {
        Structs.Polygon.Builder polygon = polygons.get(index);
        for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
            Structs.Property property = polygon.getPropertiesList().get(i);
            if (property.getKey().equals("Humidity")) {
                //int oldHumidity = Integer.parseInt(property.getValue());
                polygons.get(index).removeProperties(i);
                Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity")
                        .setValue(String.valueOf((int) Math.round(newHumidity))).build();
                polygons.get(index).addProperties(humidity);
                break;
            }
        }
    }
}
