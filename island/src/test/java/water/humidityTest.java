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

    @Test
    public void getHumidityTest(){
        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("50").build();
        polygon.addProperties(humidity);
        int h = humidGen.getHumidity(polygon);
        assertEquals(h, 50);
    }

    @Test
    public void assignAquiferHumidityTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Property aquifer = Structs.Property.newBuilder().setKey("Aquifer").setValue("50").build();
        polygon.addProperties(aquifer).addNeighborIdxs(1);

        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder();
        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("50").build();
        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon1.addProperties(humidity).addProperties(biome).addNeighborIdxs(0);

        polygonList.add(polygon);
        polygonList.add(polygon1);

        humidGen.assignAquiferHumidity(polygonList);
        assertEquals(polygon1.getProperties(1).getValue(), "100");
    }

    @Test
    public void assignLakeHumidityTest(){
        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();

        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Property lake = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
        polygon.addProperties(lake).addNeighborIdxs(1);

        Structs.Polygon.Builder polygon1 = Structs.Polygon.newBuilder();
        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("50").build();
        Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("land").build();
        polygon1.addProperties(humidity).addProperties(biome).addNeighborIdxs(0);

        polygonList.add(polygon);
        polygonList.add(polygon1);

        humidGen.assignLakeHumidity(polygonList);
        assertEquals(polygon1.getProperties(1).getValue(), "200");
    }

    @Test
    public void replaceHumidityTest(){
        Structs.Polygon.Builder polygon = Structs.Polygon.newBuilder();
        Structs.Property humidity = Structs.Property.newBuilder().setKey("Humidity").setValue("50").build();
        polygon.addProperties(humidity);

        List<Structs.Polygon.Builder> polygonList = new ArrayList<>();
        polygonList.add(polygon);

        humidGen.replaceHumidity(100, polygonList, 0);
        assertEquals(polygon.getProperties(0).getValue(), "100");
    }
}
