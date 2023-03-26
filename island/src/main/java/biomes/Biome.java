package biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface Biome {
    public void assignBiome(List<Structs.Polygon.Builder> polygonList);
    public void replaceProperty(Structs.Polygon.Builder polygon, String property);
}
