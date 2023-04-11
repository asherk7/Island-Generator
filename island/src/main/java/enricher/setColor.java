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
                    } else if (property.getValue().equals("rainforest")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("3,190,23").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("dryforest")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("76,117,46").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("wetland")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("5,66,4").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("dryland")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("158,162,107").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("snowpeak")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("255,255,255").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("permafrost")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("18,126,148").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("ice")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("165,242,243").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("deadland")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("128,91,0").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("ocean")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("32,4,145").build();
                        polygon.addProperties(color);
                    } else if (property.getValue().equals("lake")) {
                        Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("32,4,145").build();
                        polygon.addProperties(color);
                    }
                }
            }
        }
    }

    public void assignColor(Structs.Segment.Builder segment) {
        for (int i = 0; i < segment.getPropertiesList().size(); i++) {
            Structs.Property property = segment.getPropertiesList().get(i);
            if(property.getKey().equals("River")){
                Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("32,4,145").build();
                segment.addProperties(color);
            }
            else if(property.getKey().equals("Path")){
                Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("40,40,40").build();
                segment.addProperties(color);
            }
        }
    }

    public void assignColor(Structs.Vertex.Builder vertex) {
        for (int i = 0; i < vertex.getPropertiesList().size(); i++) {
            Structs.Property property = vertex.getPropertiesList().get(i);
            if(property.getKey().equals("City")){
                Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("95,95,95").build();
                vertex.addProperties(color);
            }
        }
    }
}
