package SoilProfiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import water.humidity;

import java.util.List;

public class WetSoil implements AbsProfile{
    private final double WETSOIL_ADJUSTMENT = 0.04;

    water.humidity humidity = new humidity();
    public int lakeSize = 0;


    @Override
    public void absorption(List<Structs.Polygon.Builder> polygons) {
        for (int i = 0; i < polygons.size(); i++) {
            Structs.Polygon.Builder p = polygons.get(i);
            double newHumidity = humidity.calcHumidityScore(WETSOIL_ADJUSTMENT, p, polygons);
            humidity.replaceHumidity(newHumidity, polygons, i);
        }
    }
    @Override
    public int lakeSize(){ return lakeSize; }
}
