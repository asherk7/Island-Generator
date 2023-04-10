package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class CheckNeighbours {
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

    public boolean checkWaterNeighbours(Structs.Polygon polygon, List<Structs.Polygon> newPolygons){
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon neighbour_p = newPolygons.get(n);
            //make sure none of the neighbours are oceans and lakes
            for (Structs.Property property2 : neighbour_p.getPropertiesList()) {
                if (property2.getKey().equals("Biome") && (property2.getValue().equals("ocean") || property2.getValue().equals("lake"))) {
                    return false;
                }
            }
        }
        return true;
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
}
