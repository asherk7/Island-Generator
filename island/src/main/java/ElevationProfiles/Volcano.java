package ElevationProfiles;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class Volcano implements AltProfile {
    @Override
    public void markHeight(List<Structs.Polygon.Builder> polygonList){
        double xCenter = 0;
        double yCenter = 0;
        //Find center polygon for volcano
        for (int i=0; i<polygonList.size(); i++){
            Structs.Polygon.Builder p = polygonList.get(i);
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
        xCenter = xCenter/polygonList.size();
        yCenter = yCenter/polygonList.size();

        Structs.Polygon.Builder pCenter = polygonList.get(0);   //Random polygon, does not matter
        double min_distance = 100000000.0;  //Random large value
        for (int i=0; i<polygonList.size(); i++){
            Structs.Polygon.Builder p = polygonList.get(i);
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
        Structs.Property peak = Structs.Property.newBuilder().setKey("Elevation").setValue("750").build();
        pCenter.addProperties(peak);
        
        List<Integer> neighbourIdx = pCenter.getNeighborIdxsList();
        for (int i = 0; i < neighbourIdx.size(); i++){
            Polygon.Builder neighbour_Poly = polygonList.get(neighbourIdx.get(i));
            List<Structs.Property> propertyList = neighbour_Poly.getPropertiesList();
            if ((contains(neighbour_Poly, polygonList))&&(elevationDNE(neighbour_Poly))){
                Structs.Property height = Structs.Property.newBuilder().setKey("Elevation").setValue("700").build();
                neighbour_Poly.addProperties(height);
            }
            // if neighbour poly is in polygonList && is not assigned an elevation:
                // Add elevation of -50
            //Use recursion for propogation?
            
        }

    }

    public Boolean contains(Polygon.Builder polygon, List<Structs.Polygon.Builder> polygonList){
        for (Polygon.Builder p : polygonList){
            if (p.equals(polygon)){
                return true;
            }
        }
        return false;
    }

    public Boolean elevationDNE(Polygon.Builder polygon){
        for (int i=0; i<polygon.getPropertiesList().size(); i++){
            Structs.Property property = polygon.getPropertiesList().get(i);
            if (property.getKey().equals("Elevation")){
                return false;
            }
        }
        return true;
    }
}   
