package SoilProfiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import water.humidity;

import java.util.List;

public class DrySoil implements AbsProfile{
    private final double DRYSOIL_ADJUSTMENT = 0.02;
    public int lakeSize = 1;
    humidity humidity = new humidity();
    @Override
    public void absorption(List<Structs.Polygon.Builder> polygons) {
        for (int i = 0; i < polygons.size(); i++) {
            Structs.Polygon.Builder p = polygons.get(i);
            double newHumidity = humidity.calcHumidityScore(DRYSOIL_ADJUSTMENT, p, polygons);
            humidity.replaceHumidity(newHumidity, polygons, i);
        }
    }
    @Override
    public int lakeSize(){ return lakeSize; }

}
