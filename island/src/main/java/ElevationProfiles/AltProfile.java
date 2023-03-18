package ElevationProfiles;

import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface AltProfile {
    List<Structs.Polygon.Builder> markHeight();
}
