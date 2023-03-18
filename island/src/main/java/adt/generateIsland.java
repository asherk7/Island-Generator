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
        for (int i=0; i<polygonList.size(); i++){
            Structs.Polygon.Builder p = polygonList.get(i);
            for (int j=0; j<p.getPropertiesList().size(); j++){
                Structs.Property property = p.getPropertiesList().get(j);
                if (property.getKey().equals("Centroid")){
                    String[] coord = property.getValue().split(",");
                    double x = Double.parseDouble(coord[0]);
                    double y = Double.parseDouble(coord[1]);
                    if(path.contains(x, y)){
                        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
                        p.addProperties(biome);
                    }
                    else{
                        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("ocean").build();
                        p.addProperties(biome);
                    }
                    break;
                }
            }
            polygonList.set(i, p);
        }
    }

}
