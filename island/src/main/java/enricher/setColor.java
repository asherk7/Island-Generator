package enricher;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class setColor {
    public void assignColor(List<Structs.Polygon.Builder> polygonList){
        for (int j=0; j< polygonList.size(); j++) {
            Structs.Polygon.Builder polygon = polygonList.get(j);
            for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
                Structs.Property property = polygon.getPropertiesList().get(i);
                if (property.getKey().equals("Biome")) {
                    if (property.getValue().equals("land")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("1,50,32").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("ocean")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("43,101,236").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("lake")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("43,101,236").build();
                        polygon.addProperties(color);
                        break;
                    }
                }
            }
        }
    }
}
