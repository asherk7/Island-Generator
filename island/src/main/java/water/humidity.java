package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.List;

public class humidity {
    public void setHumidity(List<Structs.Polygon.Builder> polygonList){
        for (int i=0; i< polygonList.size(); i++){
            Structs.Polygon.Builder polygon = polygonList.get(i);
            Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("0").build();
            polygon.addProperties(humidity);
        }
    }
}
