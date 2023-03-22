package ElevationProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class Plains implements AltProfile {
    Random rand = new Random();
    public void markHeight(List<Structs.Polygon.Builder> islandPolygonList, List<Structs.Polygon.Builder> polygonList){
        //create 3-5 high value points, and have them propogate. Peaks will be 200-400m in altitude(not accurate IRL)
        List<Polygon.Builder> hillPeaks = new ArrayList<>();
        int numOfPeaks = 5;

        for (int i = 0; i < numOfPeaks; i++){ hillPeaks.add(islandPolygonList.get(rand.nextInt(islandPolygonList.size()))); }

        for (Polygon.Builder p : hillPeaks){
            Structs.Property peak = Structs.Property.newBuilder().setKey("Elevation").setValue("100").build();
            p.addProperties(peak);
            List<Integer> neighbourIdx = p.getNeighborIdxsList();

            hillCreation(neighbourIdx, polygonList, 100, 5);
        }
        missedElevation(polygonList);
    }

    //This method checks if the polygon can be marked as a neighbour elevation
    public void hillPropogation(Polygon.Builder polygon, List<Structs.Polygon.Builder> polygonList, Integer altitude_value){
        //If the current polygon exists in polygonList, and the elevation property has not been assigned
        if ((contains(polygon, polygonList))&&(elevationDNE(polygon))){
            Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue(String.valueOf(altitude_value)).build();
            polygon.addProperties(height);
        } else {return;}

    }

    //Main method for hill propogation
    public void hillCreation(List<Integer> neighbourList, List<Structs.Polygon.Builder> polygonList, Integer altitudeValue, Integer numOfTimes){
        List<Integer> nextIterationNeighbour = new ArrayList<>();
        for (int i : neighbourList){
            Polygon.Builder neighbour_Poly = polygonList.get(i);
            for (int j : neighbour_Poly.getNeighborIdxsList()){
                nextIterationNeighbour.add(j);
            }
            hillPropogation(neighbour_Poly, polygonList, altitudeValue);
        }
        if (numOfTimes == 0){
            return;
        } else {
            hillCreation(nextIterationNeighbour, polygonList, altitudeValue, numOfTimes - 1);
        }
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

    public void missedElevation(List<Structs.Polygon.Builder> polygonList){
        for (int i=0; i<polygonList.size(); i++){
            Polygon.Builder polygon = polygonList.get(i);
            if (elevationDNE(polygon)){
                Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue("50").build();
                polygon.addProperties(height);
            }
            polygonList.set(i, polygon);
        }
    }
}
