package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.List;

public class humidity {
    public void setHumidity(List<Structs.Polygon.Builder> polygonList){
        for (int i=0; i< polygonList.size(); i++){
            Structs.Polygon.Builder polygon = polygonList.get(i);
            Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();
            polygon.addProperties(humidity);
        }
    }

    public void assignAquiferHumidity(List<Structs.Polygon.Builder> newPolygons){
        for(int i=0; i < newPolygons.size(); i++){
            Structs.Polygon.Builder polygon = newPolygons.get(i);
            for (int j = 0; j < polygon.getPropertiesList().size(); j++) {
                Structs.Property property = polygon.getPropertiesList().get(j);
                if (property.getKey().equals("Aquifer")) {
                    for (int n : polygon.getNeighborIdxsList()) {
                        Structs.Polygon.Builder neighbour = newPolygons.get(n);
                        for (int k = 0; k < neighbour.getPropertiesList().size(); k++) {
                            Structs.Property property1 = neighbour.getPropertiesList().get(k);
                            if (property1.getKey().equals("Biome") && property1.getValue().equals("land")) {
                                for (int z = 0; z < neighbour.getPropertiesList().size(); z++) {
                                    Structs.Property property2 = neighbour.getPropertiesList().get(z);
                                    if (property2.getKey().equals("Humidity")) {
                                        int oldHumidity = Integer.parseInt(property2.getValue());
                                        neighbour.removeProperties(z);
                                        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(Integer.parseInt(property.getValue()) + oldHumidity)).build();
                                        neighbour.addProperties(humidity);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void assignRiverHumidity(List<Structs.Polygon.Builder> polygonList) {
        for (int j = 0; j < polygonList.size(); j++) {
            Structs.Polygon.Builder polygon = polygonList.get(j);
            for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
                if (polygon.getProperties(i).getKey().equals("River")) {
                    for (int w = 0; w < polygon.getPropertiesList().size(); w++) {
                        Structs.Property prop = polygon.getPropertiesList().get(w);
                        if (prop.getKey().equals("Humidity")) {
                            int oldHumidity = Integer.parseInt(prop.getValue());
                            polygon.removeProperties(w);
                            Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(75 + oldHumidity)).build();
                            polygon.addProperties(humidity);
                            break;
                        }
                    }
                    for (int n : polygon.getNeighborIdxsList()) {
                        Structs.Polygon.Builder neighbour = polygonList.get(n);
                        for (int k = 0; k < neighbour.getPropertiesList().size(); k++) {
                            Structs.Property property1 = neighbour.getPropertiesList().get(k);
                            if (property1.getKey().equals("Biome") && property1.getValue().equals("land")) {
                                for (int z = 0; z < neighbour.getPropertiesList().size(); z++) {
                                    Structs.Property property2 = neighbour.getPropertiesList().get(z);
                                    if (property2.getKey().equals("Humidity")) {
                                        int oldHumidity = Integer.parseInt(property2.getValue());
                                        neighbour.removeProperties(z);
                                        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(75 + oldHumidity)).build();
                                        neighbour.addProperties(humidity);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void assignLakeHumidity(List<Structs.Polygon.Builder> newPolygons){
        for(int i=0; i < newPolygons.size(); i++){
            Structs.Polygon.Builder polygon = newPolygons.get(i);
            for (int j = 0; j < polygon.getPropertiesList().size(); j++) {
                Structs.Property property = polygon.getPropertiesList().get(j);
                if (property.getKey().equals("Biome") && property.getValue().equals("lake")) {
                    for (int n : polygon.getNeighborIdxsList())     {
                        Structs.Polygon.Builder neighbour = newPolygons.get(n);
                        for (int k = 0; k < neighbour.getPropertiesList().size(); k++) {
                            Structs.Property property1 = neighbour.getPropertiesList().get(k);
                            if (property1.getKey().equals("Biome") && property1.getValue().equals("land")) {
                                for (int z = 0; z < neighbour.getPropertiesList().size(); z++) {
                                    Structs.Property property2 = neighbour.getPropertiesList().get(z);
                                    if (property2.getKey().equals("Humidity")) {
                                        neighbour.removeProperties(z);
                                        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("150").build();
                                        neighbour.addProperties(humidity);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void replaceHumidity(double newHumidity, List<Structs.Polygon.Builder> polygons, int index) {
        Structs.Polygon.Builder polygon = polygons.get(index);
        for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
            Structs.Property property = polygon.getPropertiesList().get(i);
            if (property.getKey().equals("Humidity")) {
                polygon.removeProperties(i);
                Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity")
                        .setValue(String.valueOf((int) Math.round(newHumidity))).build();
                polygon.addProperties(humidity);
                break;
            }
        }
    }

    public double calcHumidityScore(double ADJUSTMENT, Structs.Polygon.Builder polygon, List<Structs.Polygon.Builder> polygons) {
        double humidityScore = 0;
        double x1 = 0, y1 = 0, x2, y2;
        int oldHumidity = 0;
        for (Structs.Property p : polygon.getPropertiesList()) {
            if (p.getKey().equals("Centroid")) {
                String[] coord = p.getValue().split(",");
                x1 = Double.parseDouble(coord[0]);
                y1 = Double.parseDouble(coord[1]);
            }
        }

        for (Structs.Property p : polygon.getPropertiesList()) {
            if (p.getKey().equals("Humidity")) {
                oldHumidity = Integer.parseInt(p.getValue());
            }
        }

        for (Structs.Polygon.Builder poly : polygons) {
            for (Structs.Property p : poly.getPropertiesList()) {
                if ((p.getKey().equals("Biome") && (p.getValue().equals("lake") || p.getValue().equals("river")))
                        || p.getKey().equals("Aquifer")) {
                    for (Structs.Property h : poly.getPropertiesList()) {
                        if (h.getKey().equals("Centroid")) {
                            String[] coord = h.getValue().split(",");
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
        return (humidityScore * ADJUSTMENT + oldHumidity);
    }
}
