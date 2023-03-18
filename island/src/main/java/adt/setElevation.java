package adt;

import java.util.ArrayList;
import java.util.List;
import ElevationProfiles.AltProfile;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class setElevation{

    //Goes to interface to create altitude
    public void setElevProfile(AltProfile altitudeType, List<Structs.Polygon.Builder> polygonList){
        List<Structs.Polygon.Builder> islandPolygonList = new ArrayList<>();
        //Set all points outside of the island to have sea level elevation
        for (int i = 0; i < polygonList.size(); i++){
            Polygon.Builder p = polygonList.get(i);
            for (int j=0; j<p.getPropertiesList().size(); j++){
                Structs.Property property = p.getPropertiesList().get(j);
                if (property.getKey().equals("Biome")){
                    if (property.getValue().equals("ocean")){
                        Structs.Property oceanLevel = Structs.Property.newBuilder().setKey("Elevation").setValue("0").build();
                        p.addProperties(oceanLevel);
                    } else {
                        islandPolygonList.add(p);
                    }

                }
            }
        }
        altitudeType.markHeight(islandPolygonList);
    }
    
}