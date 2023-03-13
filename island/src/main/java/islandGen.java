import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;


public class islandGen {
    
    public void assignBiome(Mesh aMesh){

        List<Polygon> polyList = aMesh.getPolygonsList();
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

        //Iterate through each polygon, and assign biome
        















    //     Vertex centroid = p.centroid();
    //     float x = centroid.x();
    //     float y = centroid.y();

    //     int width = aMesh.width;
    //     int height = aMesh.height;

    //     int r1 = (2*height)/5;
    //     int r2 = (height)/4;
    //     //fix radius for all widths and heights, current radius is ideal for 1080x1920

    //     //put formula and check stuff
    //     double circle = Math.pow((x - (width / 2.0)), 2) + Math.pow((y - (height / 2.0)), 2);
    //     if (circle <= Math.pow(r2, 2)){
    //         return Structs.Property.newBuilder().setKey("Biome").setValue("Lagoon").build();
    //     }
    //     else if ((circle <= Math.pow(r1,2)) && (circle > Math.pow(r2,2))){
    //         return Structs.Property.newBuilder().setKey("Biome").setValue("Land").build();
    //     }
    //     else if (circle > Math.pow(r1,2)){
    //         return Structs.Property.newBuilder().setKey("Biome").setValue("Ocean").build();
    //     }
    //     else {
    //         return null;
    //     }
    }

}
