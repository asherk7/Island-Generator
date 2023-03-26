package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class aquiferGen {
    public int aquiferSize;
    humidity humidity = new humidity();
    public void drawAquifers(int aquifers, List<Structs.Polygon.Builder> newPolygons) {
        this.aquiferSize = 1;
        for (int j = 0; j < aquifers; j++) {
            try {
                makeAquifer(newPolygons, findAquiferPolygon(newPolygons));
            } catch (Exception e) {
                //a new aquifer couldn't be formed due to biome issues
            }
        }
        humidity.assignAquiferHumidity(newPolygons);
    }

    public int findAquiferPolygon(List<Structs.Polygon.Builder> newPolygons) {
        List<Structs.Polygon.Builder> polyon_copy = new ArrayList<>(newPolygons) ;
        Collections.shuffle(polyon_copy);
        //creating a shuffled list, so we can iterate through random polygons
        for (Structs.Polygon.Builder polygon: polyon_copy){
            for (Structs.Property property1 : polygon.getPropertiesList()) {
                //make sure polygon isn't an ocean or lake
                if (property1.getKey().equals("Biome") && !(property1.getValue().equals("ocean") || property1.getValue().equals("lake"))) {
                    boolean neighbour_isntWater = checkWaterNeighbours(polygon, newPolygons);
                    if(neighbour_isntWater){ return newPolygons.indexOf(polygon); }
                }
            }
        }
        return -1;
    }

    public boolean checkWaterNeighbours(Structs.Polygon.Builder polygon, List<Structs.Polygon.Builder> newPolygons){
        for (int n : polygon.getNeighborIdxsList()) {
            Structs.Polygon.Builder neighbour_p = newPolygons.get(n);
            //make sure none of the neighbours are oceans and lakes
            for (Structs.Property property2 : neighbour_p.getPropertiesList()) {
                if (property2.getKey().equals("Biome") && (property2.getValue().equals("ocean") || property2.getValue().equals("lake"))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeAquifer(List<Structs.Polygon.Builder> newPolygons, int polygon_position) {
        Structs.Polygon.Builder polygon = newPolygons.get(polygon_position);
        Structs.Property aquifer = Structs.Property.newBuilder().setKey("Aquifer").setValue("150").build();
        //value is amount of moisture given to neighbours
        polygon.addProperties(aquifer);
        for (int i=0; i< polygon.getPropertiesList().size(); i++) {
            if (polygon.getProperties(i).getKey().equals("Humidity")){
                int oldHumidity = Integer.parseInt(polygon.getProperties(i).getValue());
                polygon.removeProperties(i);
                Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue(String.valueOf(150 + oldHumidity)).build();
                polygon.addProperties(humidity);
                break;
            }
        }
    }


}
