package SoilProfiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import water.humidity;

import java.util.List;

public class Sand implements AbsProfile{
    private final double SAND_ADJUSTMENT = 0.01;
    humidity humidity = new humidity();
    @Override
    public void absorption(List<Structs.Polygon.Builder> polygons) {
        for (int i = 0; i < polygons.size(); i++) {
            Structs.Polygon.Builder p = polygons.get(i);
            double newHumidity = humidity.calcHumidityScore(SAND_ADJUSTMENT, p, polygons);
            humidity.replaceHumidity(newHumidity, polygons, i);
        }
    }
}
