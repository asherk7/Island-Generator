package configuration;

import ElevationProfiles.Hills;
import ElevationProfiles.Plains;
import ElevationProfiles.Volcano;
import SoilProfiles.Sand;
import shapes.Circle;
import shapes.Rectangle;
import shapes.Triangle;

public class SeedSetMethods {
    public void setMode(Configuration config, int mode) {
        switch (mode) {
            case 0 -> config.islandMode = "lagoon";
            case 1 -> config.islandMode = "island";
        }
    }

    public void setShape(Configuration config, int mode) {
        switch (mode) {
            case 0 -> config.shape = new Circle();
            case 1 -> config.shape = new Rectangle();
            case 2 -> config.shape = new Triangle();
        }
    }

    public void setAltitude(Configuration config, int mode) {
        switch (mode) {
            case 0 -> config.elevationType = new Volcano();
            case 1 -> config.elevationType = new Hills();
            case 3 -> config.elevationType = new Plains();
        }
    }

    public void setSoil(Configuration config, int mode) {
        switch(mode) {
            case 0 -> config.soilType = new Sand();
        }
    }

    public void setLakes(Configuration config, int mode) {
        config.lakes = mode;
    }

    public void setRivers(Configuration config, int mode) {
        config.rivers = mode;
    }

    public void setAquifers(Configuration config, int mode) {
        config.aquifers = mode;
    }
}
