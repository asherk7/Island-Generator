package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DotGenTest {

    @Test
    public void meshIsNotNull() {
        DotGen generator = new DotGen();
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }

}
