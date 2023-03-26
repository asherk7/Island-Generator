package SoilProfiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface AbsProfile {
    public void absorption(List<Structs.Polygon.Builder> polygons);
    public int lakeSize();
}
