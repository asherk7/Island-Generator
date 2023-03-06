import ca.mcmaster.cas.se2aa4.a2.generator.adt.Mesh;
import ca.mcmaster.cas.se2aa4.a2.generator.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.generator.export.enricher.RandomEnricher;
import ca.mcmaster.cas.se2aa4.a2.generator.export.Exporter;
import ca.mcmaster.cas.se2aa4.a2.generator.specification.Buildable;
import ca.mcmaster.cas.se2aa4.a2.generator.specification.SpecificationFactory;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        Buildable specification = SpecificationFactory.create(config);
        Mesh theMesh = specification.build();
        Structs.Mesh exported = new Exporter().run(theMesh);
        if(config.export().containsKey(Configuration.DEMO)) {
            exported = new RandomEnricher(0.2f).process(exported);
        }
        new MeshFactory().write(exported, config.export(Configuration.FILENAME));
    }
}