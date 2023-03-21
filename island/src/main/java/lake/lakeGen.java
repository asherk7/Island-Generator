package lake;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class lakeGen {
    public int lakeSize;
    Random rand = new Random();

    public void drawLakes(int lakes, List<Structs.Polygon.Builder> newPolygons) {
        this.lakeSize = 1;
        for (int j=0; j < lakes; j++) {
            try {
                makeLake(newPolygons, findLakePolygon(newPolygons));
            }
            catch(Exception e){
                //a new lake couldn't be formed due to biome issues
            }
        }
        assignHumidity(newPolygons);
    }
    public int findLakePolygon(List<Structs.Polygon.Builder> newPolygons) {
        List<Structs.Polygon.Builder> polyon_copy = new ArrayList<>(newPolygons) ;
        Collections.shuffle(polyon_copy);
        //creating a shuffled list, so we can iterate through random polygons
        for (Structs.Polygon.Builder polygon: polyon_copy){
            for (Structs.Property property : polygon.getPropertiesList()) {
                //make sure elevation is low
                if (property.getKey().equals("Elevation") && (property.getValue().equals("50") || property.getValue().equals("100"))) {
                    for (Structs.Property property1 : polygon.getPropertiesList()) {
                        //make sure polygon isn't an ocean or lake
                        if (property1.getKey().equals("Biome") && !(property1.getValue().equals("ocean") || property1.getValue().equals("lake"))) {
                            boolean neighbour_isntWater = checkWaterNeighbours(polygon, newPolygons);
                            if(neighbour_isntWater){ return newPolygons.indexOf(polygon); }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public boolean checkWaterNeighbours(Structs.Polygon.Builder polygon, List<Structs.Polygon.Builder> newPolygons){
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = newPolygons.get(n);
            //make sure none of the neighbours are oceans and lakes
            for (Structs.Property property2 : neighbour_p.getPropertiesList()) {
                if (property2.getKey().equals("Biome") && (property2.getValue().equals("ocean") || property2.getValue().equals("lake"))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLake(Structs.Polygon.Builder polygon){
        for (Structs.Property property : polygon.getPropertiesList()) {
            if (property.getKey().equals("Biome") && (property.getValue().equals("lake"))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkOceanNeighbours(Structs.Polygon.Builder polygon, List<Structs.Polygon.Builder> newPolygons){
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = newPolygons.get(n);
            //make sure none of the neighbours are oceans and lakes
            for (Structs.Property property2 : neighbour_p.getPropertiesList()) {
                if (property2.getKey().equals("Biome") && (property2.getValue().equals("ocean"))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeLake(List<Structs.Polygon.Builder> newPolygons, int polygon_position) {
        Structs.Polygon.Builder polygon = newPolygons.get(polygon_position);
        for (int i=0; i < polygon.getPropertiesList().size(); i++){
            Structs.Property property = polygon.getPropertiesList().get(i);
            if (property.getKey().equals("Biome")){
                polygon.removeProperties(i);
                Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
                polygon.addProperties(biome);
            }
        }
        for (int n: polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = newPolygons.get(n);
            boolean neighbour_isntOcean = checkOceanNeighbours(neighbour_p, newPolygons);
            if (neighbour_isntOcean) {
                for (int i = 0; i < neighbour_p.getPropertiesList().size(); i++) {
                    Structs.Property property = neighbour_p.getPropertiesList().get(i);
                    if (property.getKey().equals("Elevation")) {
                        if (property.getValue().equals("50") || property.getValue().equals("100")) {
                            for (int j = 0; j < neighbour_p.getPropertiesList().size(); j++) {
                                Structs.Property property1 = neighbour_p.getPropertiesList().get(j);
                                if (property1.getKey().equals("Biome")) {
                                    neighbour_p.removeProperties(j);
                                    Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
                                    neighbour_p.addProperties(biome);
                                }
                            }
                        }
                    }
                }
            }
        }
        Structs.Polygon.Builder lakeNeighbor;
        for (int j = 0; j < lakeSize; j++) {
            lakeNeighbor = newPolygons.get(polygon.getNeighborIdxsList().get(j));
            if (isLake(lakeNeighbor)) {
                for (int n : lakeNeighbor.getNeighborIdxsList()) {
                    Structs.Polygon.Builder neighbour = newPolygons.get(n);
                    boolean neighbour_isntOcean = checkOceanNeighbours(neighbour, newPolygons);
                    if (neighbour_isntOcean) {
                        for (int i = 0; i < neighbour.getPropertiesList().size(); i++) {
                            Structs.Property property = neighbour.getPropertiesList().get(i);
                            if (property.getKey().equals("Elevation")) {
                                if (property.getValue().equals("50") || property.getValue().equals("100")) {
                                    for (int k = 0; k < neighbour.getPropertiesList().size(); k++) {
                                        Structs.Property property1 = neighbour.getPropertiesList().get(k);
                                        if (property1.getKey().equals("Biome")) {
                                            neighbour.removeProperties(k);
                                            Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
                                            neighbour.addProperties(biome);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void assignHumidity(List<Structs.Polygon.Builder> newPolygons){
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
                                Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("50").build();
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
