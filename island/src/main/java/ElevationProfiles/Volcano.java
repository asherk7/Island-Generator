package ElevationProfiles;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class Volcano implements AltProfile {
    @Override
    public void markHeight(List<Structs.Polygon.Builder> islandPolygonList, List<Structs.Polygon.Builder> polygonList){
        double xCenter = 0;
        double yCenter = 0;
        //Find center polygon for volcano
        for (int i=0; i<islandPolygonList.size(); i++){
            Structs.Polygon.Builder p = islandPolygonList.get(i);
            for (int j=0; j<p.getPropertiesList().size(); j++){
                Structs.Property property = p.getPropertiesList().get(j);
                if (property.getKey().equals("Centroid")){
                    String[] coord = property.getValue().split(",");
                    double x = Double.parseDouble(coord[0]);
                    double y = Double.parseDouble(coord[1]);
                    xCenter+=x;
                    yCenter+=y;
                }
            }
        }
        xCenter = xCenter/islandPolygonList.size();
        yCenter = yCenter/islandPolygonList.size();

        Structs.Polygon.Builder pCenter = islandPolygonList.get(0);   //Random polygon, does not matter
        double min_distance = 100000000.0;  //Random large value
        for (int i=0; i<islandPolygonList.size(); i++){
            Structs.Polygon.Builder p = islandPolygonList.get(i);
            for (int j=0; j<p.getPropertiesList().size(); j++){
                Structs.Property property = p.getPropertiesList().get(j);
                if (property.getKey().equals("Centroid")){
                    String[] coord = property.getValue().split(",");
                    double x = Double.parseDouble(coord[0]);
                    double y = Double.parseDouble(coord[1]);
                    
                    double distance = Math.sqrt(Math.pow(xCenter - x,2) + Math.pow(yCenter - y,2));
                    if (distance < min_distance){
                        min_distance = distance;
                        pCenter = p;
                    }
                }
            }
        }

        //Set elevation of peak
        Structs.Property peak = Structs.Property.newBuilder().setKey("Elevation").setValue("800").build();
        pCenter.addProperties(peak);
        List<Integer> neighbourIdx = pCenter.getNeighborIdxsList();

        volcanoCreation(neighbourIdx, polygonList, 700);

        /*
        for (int i = 0; i < neighbourIdx.size(); i++){
            Polygon.Builder neighbour_Poly = polygonList.get(neighbourIdx.get(i));
            //Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue(String.valueOf(700)).build();
            //neighbour_Poly.addProperties(height);
            mountainPropogation(neighbour_Poly, polygonList, 700);
            // if neighbour poly is in polygonList && is not assigned an elevation:
                // Add elevation of -50
            //Use recursion for propogation?

        }
         */
        //test
        assignColor(polygonList);

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

    //Method of going to the neighbors and adding elevation property
    public void mountainPropogation(Polygon.Builder polygon, List<Structs.Polygon.Builder> polygonList, Integer altitude_value){
        //If the current polygon exists in polygonList, and the elevation property has not been assigned
        if ((contains(polygon, polygonList))&&(elevationDNE(polygon))){
            Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue(String.valueOf(altitude_value)).build();
            polygon.addProperties(height);
        } else {return;}

    }

    public void volcanoCreation(List<Integer> neighbourList, List<Structs.Polygon.Builder> polygonList, Integer altitudeValue){
        List<Integer> nextIterationNeighbour = new ArrayList<>();
        for (int i : neighbourList){
            Polygon.Builder neighbour_Poly = polygonList.get(i);
            for (int j : neighbour_Poly.getNeighborIdxsList()){
                nextIterationNeighbour.add(j);
            }
            //Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue(String.valueOf(700)).build();
            //neighbour_Poly.addProperties(height);
            mountainPropogation(neighbour_Poly, polygonList, altitudeValue);
        }
        if (altitudeValue == 0){
            return;
        } else {
            volcanoCreation(nextIterationNeighbour, polygonList, altitudeValue-100);
        }
    }

    //Testing Colours
    public void assignColor(List<Structs.Polygon.Builder> polygonList){
        for (int k = 0; k < polygonList.size(); k++){
            Polygon.Builder polygon = polygonList.get(k);
            for (int i=0; i<polygon.getPropertiesList().size(); i++){
                Structs.Property property = polygon.getPropertiesList().get(i);
                if (property.getKey().equals("Elevation")) {
                    if(property.getValue().equals("800")){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("255,0,0").build();
                        polygon.addProperties(color);
                    }
                    else if ((property.getValue().equals("700") || (property.getValue().equals("600")))){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("255,106,0").build();
                        polygon.addProperties(color);
                    }
                    else if ((property.getValue().equals("500") || (property.getValue().equals("400")))){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("157,255,0").build();
                        polygon.addProperties(color);
                    }
                    else if ((property.getValue().equals("300") || (property.getValue().equals("200")))){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("43,255,0").build();
                        polygon.addProperties(color);
                    }
                    else if ((property.getValue().equals("200") || (property.getValue().equals("100")))){
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("0,255,166").build();
                        polygon.addProperties(color);
                    }
                    break;
                }
            }
        }
    }
}

