package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class riverGen {
    Random rand = new Random();
    private List<Structs.Segment.Builder> riverList = new ArrayList<>();
    public List<Structs.Segment.Builder> drawRivers(int rivers, List<Structs.Polygon.Builder> polygonList){
        for (int j=0; j < rivers; j++) {
            try {
                makeRiver(riverList, polygonList, findRiver(polygonList), 3);
            }
            catch(Exception e){
                //a new river couldn't be formed due to biome issues
            }
        }
        return riverList;
    }

    public int findRiver(List<Structs.Polygon.Builder> polygonList){
        List<Structs.Polygon.Builder> polyon_copy = new ArrayList<>(polygonList) ;
        Collections.shuffle(polyon_copy);
        //creating a shuffled list, so we can iterate through random polygons
        for (Structs.Polygon.Builder polygon: polyon_copy){
            for (Structs.Property property : polygon.getPropertiesList()) {
                //make sure elevation is low
                if (property.getKey().equals("Biome") && !(property.getValue().equals("ocean") || property.getValue().equals("lake"))) {
                    return polygonList.indexOf(polygon);
                }
            }
        }
        return -1;
    }

    public void makeRiver(List<Structs.Segment.Builder> riverList, List<Structs.Polygon.Builder> polygonList, int polygon_position, int repeat){
        if(repeat==0){
            Structs.Polygon.Builder polygon = polygonList.get(polygon_position);
            polygon.removeProperties(getBiomeProperty(polygon));
            Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
            polygon.addProperties(biome);
            return;
        }
        Structs.Polygon.Builder polygon = polygonList.get(polygon_position);
        int elevation = getElevation(polygon);
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = polygonList.get(n);
            for (Structs.Property property : neighbour_p.getPropertiesList()) {
                if (property.getKey().equals("Biome") && (property.getValue().equals("ocean") || property.getValue().equals("lake"))) {
                    Structs.Segment.Builder segment = Structs.Segment.newBuilder();
                    segment.setV1Idx(polygon.getCentroidIdx()).setV2Idx(neighbour_p.getCentroidIdx());
                    Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(rand.nextInt(1, 4))).build();
                    riverList.add(segment.addProperties(river));
                    return;
                }
            }
        }
        Structs.Polygon.Builder neighbour = null;
        for (int n : polygon.getNeighborIdxsList()){
            Structs.Polygon.Builder neighbour_p = polygonList.get(n);
            if (elevation > getElevation(neighbour_p)){
                neighbour = neighbour_p;
                break;
            }
        }
        for (int n : polygon.getNeighborIdxsList()){
            Structs.Polygon.Builder neighbour_p = polygonList.get(n);
            if (elevation == getElevation(neighbour_p)){
                neighbour = neighbour_p;
                repeat -= 1;
                break;
            }
        }
        if(neighbour == null){
            polygon.removeProperties(getBiomeProperty(polygon));
            Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
            polygon.addProperties(biome);
        }
        else{
            Structs.Segment.Builder segment = Structs.Segment.newBuilder();
            segment.setV1Idx(polygon.getCentroidIdx()).setV2Idx(neighbour.getCentroidIdx());
            Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(rand.nextInt(1, 4))).build();
            riverList.add(segment.addProperties(river));
            makeRiver(riverList, polygonList, polygonList.indexOf(neighbour), repeat);
        }
    }

    public int getElevation(Structs.Polygon.Builder polygon){
        for (Structs.Property property: polygon.getPropertiesList()){
            if (property.getKey().equals("Elevation")){
                return Integer.parseInt(property.getValue());
            }
        }
        return -1;
    }

    public int getBiomeProperty(Structs.Polygon.Builder polygon){
        for (int i=0; i<polygon.getPropertiesList().size(); i++){
            if (polygon.getProperties(i).getKey().equals("Biome")){
                return i;
            }
        }
        return -1;
    }
}
