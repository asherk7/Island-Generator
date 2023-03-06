package ca.mcmaster.cas.se2aa4.a2.generator.export.enricher;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.util.Random;

public class RandomEnricher implements Enricher {

    private final Random bag = new Random();
    private final float threshold;

    public RandomEnricher(float threshold) {
        this.threshold = threshold;
    }

    @Override
    public Structs.Mesh process(Structs.Mesh aMesh) {
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(aMesh.getVerticesList());
        clone.addAllSegments(aMesh.getSegmentsList());
        for(Structs.Polygon poly: aMesh.getPolygonsList()) {
            float level = bag.nextFloat();
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(poly);
            if (level < threshold){
                String color = bag.nextInt(255)+","+bag.nextInt(255)+","+bag.nextInt(255);
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(color)
                        .build();
                pc.addProperties(p);
            }
            clone.addPolygons(pc);
        }
        return clone.build();
    }
}
