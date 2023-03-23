package water;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class humidityTest {
    humidity humidGen = new humidity();
    @Test
    public void setHumidityTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();
        polygonList.add(Structs.Polygon.newBuilder());
        humidGen.setHumidity(polygonList);
        assertEquals(polygonList.get(0).getProperties(0).getKey(), "Humidity");
    }
}
