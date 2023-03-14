import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class islandGen {
    
    public Structs.Property assignBiome(Mesh aMesh, Polygon p, List<Vertex> vertices){
        Vertex centroid = vertices.get(p.getCentroidIdx());
        double x = centroid.getX();
        double y = centroid.getY();

        int width = 0;
        int height = 0;  
        
        //Get width and height values
        for (Structs.Property property: aMesh.getPropertiesList()){
            if (property.getKey().equals("Width")){
                width = Integer.parseInt(property.getValue());
            }
            if (property.getKey().equals("Height")){
                height = Integer.parseInt(property.getValue());
            }
        }    

        int r1 = (2*height)/5;
        int r2 = (height)/4;

        Structs.Property.Builder biome = Structs.Property.newBuilder().setKey("Biome");
        double circle = Math.pow((x - (width / 2.0)), 2) + Math.pow((y - (height / 2.0)), 2);
        if (circle <= Math.pow(r2, 2)){
            biome.setValue("Lagoon").build();
        }
        else if ((circle <= Math.pow(r1,2)) && (circle > Math.pow(r2,2))){
            biome.setValue("Land").build();
        }
        else if (circle > Math.pow(r1,2)){
            biome.setValue("Ocean").build();
        }

        List<Integer> neighbours = p.getNeighborIdxsList();
        if(biome.getValue().equals("Land")) {
            for (int i = 0; i < neighbours.size(); i++) {
                Polygon p1 = aMesh.getPolygons(neighbours.get(i)); //double check where to find the neighbours, might need to take buildpolygonregistry from exporter
                Vertex centroid1 = vertices.get(p1.getCentroidIdx());
                double x1 = centroid1.getX();
                double y1 = centroid1.getY();
                double circle1 = Math.pow((x1 - (width / 2.0)), 2) + Math.pow((y1 - (height / 2.0)), 2);
                if (circle1 <= Math.pow(r2, 2) || circle1 > Math.pow(r1, 2)) {
                    biome.setValue("Beach").build();
                }
            }
        }
        return biome.build();
    }

    public Structs.Property assignColour(Polygon.Builder p){
        List<Structs.Property> property_list = p.getPropertiesList();
        for (Structs.Property property: property_list) {
            if (property.getKey().equals("Biome")) {
                if (property.getValue().equals("Lagoon")){
                    return Structs.Property.newBuilder().setKey("Color").setValue("173,216,255").build();
                }
                else if (property.getValue().equals("Land")){
                    return Structs.Property.newBuilder().setKey("Color").setValue("7,255,12").build();
                }
                else if (property.getValue().equals("Ocean")){
                    return Structs.Property.newBuilder().setKey("Color").setValue("0,0,255").build();
                }
                else if (property.getValue().equals("Beach")){
                    return Structs.Property.newBuilder().setKey("Color").setValue("194,178,128").build();
                }
            }
        }
        return null;
    }

}
