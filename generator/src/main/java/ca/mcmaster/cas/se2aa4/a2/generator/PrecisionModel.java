package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.GeometryFactory;

public class PrecisionModel {

    public static final int PRECISION = 10;

    public static final GeometryFactory FACTORY  =
            new GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(PRECISION));;

}
