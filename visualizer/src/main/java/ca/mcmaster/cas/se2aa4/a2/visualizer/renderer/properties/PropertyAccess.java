package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties;

import java.util.List;
import java.util.Optional;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface PropertyAccess<T> {

    Optional<T> extract(List<Structs.Property> props);

}

