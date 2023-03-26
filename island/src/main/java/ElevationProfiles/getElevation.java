package ElevationProfiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class getElevation {
    public int getElevation(Structs.Polygon.Builder polygon){
        int elevation = 0;
        for (int i=0; i<polygon.getPropertiesList().size(); i++){
            Structs.Property property = polygon.getProperties(i);
            if (property.getKey().equals("Elevation")){
                elevation = Integer.parseInt(property.getValue());
            }
        }
        return elevation;
    }
}
