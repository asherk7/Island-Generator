package adt;

import java.util.List;
import ElevationProfiles.AltProfile;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class setElevation{

    //Goes to interface to create altitude
    public void setElevProfile(AltProfile altitudeType, List<Structs.Polygon.Builder> polygonList){
        altitudeType.markHeight();
    }
    
}