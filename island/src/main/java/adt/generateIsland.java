package adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import shapes.Shape;
import java.awt.geom.Path2D;
import java.util.List;

public class generateIsland {
    int width, height;

    public generateIsland(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void drawIsland(Shape<Path2D> shape, List<Structs.Polygon.Builder> polygonList){
        Path2D path = shape.drawShape(this.width, this.height);
        for (Structs.Polygon.Builder p: polygonList){
            for(Structs.Property property: p.getPropertiesList()){
                if (property.getKey().equals("Centroid")){
                    String[] coord = property.getValue().split(",");
                    double x = Integer.parseInt(coord[0]);
                    double y = Integer.parseInt(coord[1]);
                    if(path.contains(x, y)){
                        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
                        p.addProperties(biome);
                    }
                    else{
                        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
                        p.addProperties(biome);
                    }
                }
            }
            assignColor(p);
        }
    }

    public void assignColor(Structs.Polygon.Builder polygon){
        for(Structs.Property property: polygon.getPropertiesList()) {
            if (property.getKey().equals("Biome")) {
                if(property.getValue().equals("land")){
                    Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("1,50,32").build();
                    polygon.addProperties(color);
                }
                else if (property.getValue().equals("ocean")){
                    Structs.Property color = Structs.Property.newBuilder().setKey("Color").setValue("43, 101, 236").build();
                    polygon.addProperties(color);
                }
            }
        }
    }
}
