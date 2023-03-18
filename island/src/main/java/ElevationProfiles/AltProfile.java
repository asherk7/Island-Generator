package ElevationProfiles;

import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface AltProfile {
    public void markHeight(List<Structs.Polygon.Builder> polygonList);
}
