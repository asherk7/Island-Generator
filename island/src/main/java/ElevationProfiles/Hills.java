package ElevationProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class Hills implements AltProfile{
    Random rand = new Random();
    public void markHeight(List<Structs.Polygon.Builder> islandPolygonList, List<Structs.Polygon.Builder> polygonList){
        //create 3-5 high value points, and have them propogate. Peaks will be 200-400m in altitude(not accurate IRL)
        List<Polygon.Builder> hillPeaks = new ArrayList<>();
        int numOfPeaks = rand.nextInt(3) + 5;

        for (int i = 0; i < numOfPeaks; i++){ hillPeaks.add(islandPolygonList.get(rand.nextInt(islandPolygonList.size()))); }

        for (Polygon.Builder p : hillPeaks){
            Structs.Property peak = Structs.Property.newBuilder().setKey("Elevation").setValue("300").build();
            p.addProperties(peak);
            List<Integer> neighbourIdx = p.getNeighborIdxsList();

            hillCreation(neighbourIdx, polygonList, 200);
        }
        missedElevation(polygonList);
        assignColor(polygonList);
    }

    //This method checks if the polygon can be marked as a neighbour elevation
    public void hillPropogation(Polygon.Builder polygon, List<Structs.Polygon.Builder> polygonList, Integer altitude_value){
        //If the current polygon exists in polygonList, and the elevation property has not been assigned
        if ((contains(polygon, polygonList))){
            if (!elevationDNE(polygon)){
                if (!isOcean(polygon)){
                    for (int i=0; i<polygon.getPropertiesList().size(); i++){
                        Structs.Property property = polygon.getPropertiesList().get(i);
                        if (property.getKey().equals("Elevation")){
                            int old_altitude = Integer.parseInt(property.getValue());
                            if (old_altitude < altitude_value){
                                polygon.removeProperties(i);
                                Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue(String.valueOf(altitude_value)).build();
                                polygon.addProperties(height);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue(String.valueOf(altitude_value)).build();
                polygon.addProperties(height);
            }
        }
    }

    //Main method for hill propogation
    public void hillCreation(List<Integer> neighbourList, List<Structs.Polygon.Builder> polygonList, Integer altitudeValue){
        if (altitudeValue == 0){ return; }
        List<Integer> nextIterationNeighbour = new ArrayList<>();
        for (int i : neighbourList){
            Polygon.Builder neighbour_Poly = polygonList.get(i);
            for (int j : neighbour_Poly.getNeighborIdxsList()){
                nextIterationNeighbour.add(j);
            }
            hillPropogation(neighbour_Poly, polygonList, altitudeValue);
        }
        hillCreation(nextIterationNeighbour, polygonList, altitudeValue - 100);
    }

    //Is the polygon within polygon list
    public Boolean contains(Polygon.Builder polygon, List<Structs.Polygon.Builder> polygonList){
        for (Polygon.Builder p : polygonList){
            if (p.equals(polygon)){
                return true;
            }
        }
        return false;
    }
    //Does the elevation property exist for polygon
    public Boolean elevationDNE(Polygon.Builder polygon){
        for (int i=0; i<polygon.getPropertiesList().size(); i++){
            Structs.Property property = polygon.getPropertiesList().get(i);
            if (property.getKey().equals("Elevation")){
                return false;
            }
        }
        return true;
    }

    public boolean isOcean(Polygon.Builder polygon){
        for (int i=0; i<polygon.getPropertiesList().size(); i++){
            Structs.Property property = polygon.getPropertiesList().get(i);
            if (property.getKey().equals("Biome")){
                if (property.getValue().equals("ocean")){
                    return true;
                }
            }
        }
        return false;
    }

    public void missedElevation(List<Structs.Polygon.Builder> polygonList){
        for (int i=0; i<polygonList.size(); i++){
            Polygon.Builder polygon = polygonList.get(i);
            if (elevationDNE(polygon)){
                Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue("0").build();
                polygon.addProperties(height);
            }
            polygonList.set(i, polygon);
        }
    }

    //Testing Colours
    public void assignColor(List<Structs.Polygon.Builder> polygonList){
        for (int k = 0; k < polygonList.size(); k++){
            Polygon.Builder polygon = polygonList.get(k);
            for (int i=0; i<polygon.getPropertiesList().size(); i++){
                Structs.Property property = polygon.getPropertiesList().get(i);
                if (property.getKey().equals("Elevation")) {
                    if(property.getValue().equals("300")){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("45,235,42").build();
                        polygon.addProperties(color);
                    }
                    else if (property.getValue().equals("200")){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("33,184,86").build();
                        polygon.addProperties(color);
                    }
                    else if (property.getValue().equals("100")){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("20,110,77").build();
                        polygon.addProperties(color);
                    }
                }
            }
        }
    }
}
