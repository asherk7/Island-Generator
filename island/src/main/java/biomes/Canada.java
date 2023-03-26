package biomes;

import ElevationProfiles.getElevation;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import water.humidity;

import java.util.List;

public class Canada implements Biome{
    water.humidity humidity = new humidity();
    getElevation elevation = new getElevation();
    @Override
    public void assignBiome(List<Structs.Polygon.Builder> polygonList) {
        for (Structs.Polygon.Builder polygon: polygonList){
            for (int i=0; i<polygon.getPropertiesList().size(); i++){
                Structs.Property property = polygon.getProperties(i);
                if (property.getKey().equals("Biome") && property.getValue().equals("land")){
                    int humid = humidity.getHumidity(polygon);
                    int elev = elevation.getElevation(polygon);
                    if (elev >= 300){
                        if (humid >= 300){
                            replaceProperty(polygon, "rainforest");
                        }
                        else{
                            replaceProperty(polygon, "snowpeak");
                        }
                    }
                    else if (elev >= 100){
                        if (humid >= 300){
                            replaceProperty(polygon, "wetland");
                        }
                        else{
                            replaceProperty(polygon, "dryland");
                        }
                    }
                    else{
                        if (humid < 230){
                            replaceProperty(polygon, "deadland");
                        }
                    }
                }
            }
        }
    }
    public void replaceProperty(Structs.Polygon.Builder polygon, String property){
        for (int i=0; i<polygon.getPropertiesList().size(); i++){
            Structs.Property prop = polygon.getProperties(i);
            if (prop.getKey().equals("Biome")){
                polygon.removeProperties(i);
                Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue(property).build();
                polygon.addProperties(biome);
                break;
            }
        }
    }
}
