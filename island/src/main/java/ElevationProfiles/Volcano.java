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
        
    }
}   
