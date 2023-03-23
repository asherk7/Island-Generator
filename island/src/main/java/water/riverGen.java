package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class riverGen {
    Random rand = new Random();
    private List<Structs.Segment.Builder> riverList = new ArrayList<>();

    public List<Structs.Segment.Builder> drawRivers(int rivers, List<Structs.Polygon.Builder> polygonList, int repeat) {
        for (int j = 0; j < rivers; j++) {
            try {
                makeRiver(riverList, polygonList, findRiver(polygonList), repeat, -1);
            } catch (Exception e) {
                //a new river couldn't be formed due to biome issues
            }
        }
        assignRiverHumidity(polygonList);
        return riverList;
    }

    public int findRiver(List<Structs.Polygon.Builder> polygonList) {
        List<Structs.Polygon.Builder> polyon_copy = new ArrayList<>(polygonList);
        Collections.shuffle(polyon_copy);
        //creating a shuffled list, so we can iterate through random polygons
        for (Structs.Polygon.Builder polygon : polyon_copy) {
            for (Structs.Property property : polygon.getPropertiesList()) {
                if (property.getKey().equals("River")) {
                    break;
                }
            }
            for (Structs.Property property : polygon.getPropertiesList()) {
                if (property.getKey().equals("Biome") && !(property.getValue().equals("ocean") || property.getValue().equals("lake"))) {
                    return polygonList.indexOf(polygon);
                }
            }
        }
        return -1;
    }

    public void makeRiver(List<Structs.Segment.Builder> riverList, List<Structs.Polygon.Builder> polygonList, int polygon_position, int repeat, int prev) {
        if (repeat == 0) {
            Structs.Polygon.Builder polygon = polygonList.get(polygon_position);
            polygon.removeProperties(getBiomeProperty(polygon));
            Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
            polygon.addProperties(biome);
            assignLakeHumidity(polygon, polygonList);
            return;
        }
        Structs.Polygon.Builder polygon = polygonList.get(polygon_position);
        int elevation = getElevation(polygon);
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = polygonList.get(n);
            for (Structs.Property property : neighbour_p.getPropertiesList()) {
                if (property.getKey().equals("Biome") && (property.getValue().equals("ocean") || property.getValue().equals("lake"))) {
                    Structs.Segment.Builder segment = Structs.Segment.newBuilder();
                    segment.setV1Idx(polygon.getCentroidIdx()).setV2Idx(neighbour_p.getCentroidIdx());
                    Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(rand.nextInt(1, 4))).build();
                    riverList.add(segment.addProperties(river));
                    Structs.Property river_p = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(riverList.indexOf(segment))).build();
                    polygon.addProperties(river_p);
                    return;
                }
            }
        }
        Structs.Polygon.Builder neighbour = null;
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = polygonList.get(n);
            if (elevation > getElevation(neighbour_p)) {
                neighbour = neighbour_p;
                break;
            }
        }
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = polygonList.get(n);
            if (elevation == getElevation(neighbour_p)) {
                if (polygonList.indexOf(neighbour_p) != prev) {
                    neighbour = neighbour_p;
                    repeat -= 1;
                    break;
                }
            }
        }
        if (neighbour == null) {
            polygon.removeProperties(getBiomeProperty(polygon));
            Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
            polygon.addProperties(biome);
            assignLakeHumidity(polygon, polygonList);
        } else {
            Structs.Segment.Builder segment = Structs.Segment.newBuilder();
            segment.setV1Idx(polygon.getCentroidIdx()).setV2Idx(neighbour.getCentroidIdx());
            Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(rand.nextInt(1, 4))).build();
            riverList.add(segment.addProperties(river));
            Structs.Property river_p = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(riverList.indexOf(segment))).build();
            polygon.addProperties(river_p);
            for (int i = 0; i<neighbour.getPropertiesList().size(); i++){
                Structs.Property p = neighbour.getProperties(i);
                if(p.getKey().equals("River")){
                    mergeRiver(polygon, neighbour, riverList, polygonList, 0);
                    return;
                }
            }
            makeRiver(riverList, polygonList, polygonList.indexOf(neighbour), repeat, polygon_position);
        }
    }

    public int getElevation(Structs.Polygon.Builder polygon) {
        for (Structs.Property property : polygon.getPropertiesList()) {
            if (property.getKey().equals("Elevation")) {
                return Integer.parseInt(property.getValue());
            }
        }
        return -1;
    }

    public int getBiomeProperty(Structs.Polygon.Builder polygon) {
        for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
            if (polygon.getProperties(i).getKey().equals("Biome")) {
                return i;
            }
        }
        return -1;
    }

    public void assignLakeHumidity(Structs.Polygon.Builder polygon, List<Structs.Polygon.Builder> polygonList) {
        for (int j = 0; j < polygon.getPropertiesList().size(); j++) {
            Structs.Property property = polygon.getPropertiesList().get(j);
            if (property.getKey().equals("Biome") && property.getValue().equals("lake")) {
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
                                    Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(50 + oldHumidity)).build();
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
                            Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(25 + oldHumidity)).build();
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
                                        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(25 + oldHumidity)).build();
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

    public void mergeRiver(Structs.Polygon.Builder polygon, Structs.Polygon.Builder neighbour, List<Structs.Segment.Builder> riverList, List<Structs.Polygon.Builder> polygonList, int iteration){
        if (iteration == 0) {
            Structs.Segment.Builder river = null;
            Structs.Segment.Builder river1 = null;
            int river_index = 0;

            for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
                if (polygon.getProperties(i).getKey().equals("River")) {
                    river = riverList.get(Integer.parseInt(polygon.getProperties(i).getValue()));
                }
            }
            for (int i = 0; i < neighbour.getPropertiesList().size(); i++) {
                if (neighbour.getProperties(i).getKey().equals("River")) {
                    river_index = Integer.parseInt(neighbour.getProperties(i).getValue());
                    river1 = riverList.get(river_index);
                }
            }
            int thickness = Integer.parseInt(river.getProperties(0).getValue()) + Integer.parseInt(river1.getProperties(0).getValue());
            Structs.Property river_property = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(thickness)).build();
            river1.setProperties(0, river_property);

            Structs.Polygon.Builder next_neighbour = null;
            int river_index1 = 0;
            for (int n : neighbour.getNeighborIdxsList()) {
                next_neighbour = polygonList.get(n);
                for (int i = 0; i < next_neighbour.getPropertiesList().size(); i++) {
                    if (next_neighbour.getProperties(i).getKey().equals("River")) {
                        river_index1 = Integer.parseInt(next_neighbour.getProperties(i).getValue());
                        if (river_index1 == river_index+1){
                            iteration += 1;
                            mergeRiver(neighbour, next_neighbour, riverList, polygonList, iteration);
                            return;
                        }
                    }
                }
            }
        }
        else{
            Structs.Segment.Builder river = null;
            Structs.Segment.Builder river1 = null;
            int river_index = 0;

            for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
                if (polygon.getProperties(i).getKey().equals("River")) {
                    river = riverList.get(Integer.parseInt(polygon.getProperties(i).getValue()));
                }
            }
            for (int i = 0; i < neighbour.getPropertiesList().size(); i++) {
                if (neighbour.getProperties(i).getKey().equals("River")) {
                    river_index = Integer.parseInt(neighbour.getProperties(i).getValue());
                    river1 = riverList.get(river_index);
                }
            }
            int thickness = Integer.parseInt(river.getProperties(0).getValue());
            Structs.Property river_property = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(thickness)).build();
            river1.setProperties(0, river_property);

            Structs.Polygon.Builder next_neighbour = null;
            int river_index1 = 0;
            for (int n : neighbour.getNeighborIdxsList()) {
                next_neighbour = polygonList.get(n);
                for (int i = 0; i < next_neighbour.getPropertiesList().size(); i++) {
                    if (next_neighbour.getProperties(i).getKey().equals("River")) {
                        river_index1 = Integer.parseInt(next_neighbour.getProperties(i).getValue());
                        if (river_index1 == river_index+1){
                            mergeRiver(neighbour, next_neighbour, riverList, polygonList, iteration);
                            return;
                        }
                    }
                }
            }
        }
    }
}