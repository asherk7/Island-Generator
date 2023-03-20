package lake;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class lakeGen {
    private List<Point> lakecoords = new ArrayList<>();
    public int lakeSize;
    public void drawLakes(int lakes, List<Structs.Polygon.Builder> newPolygons) {
        this.lakeSize = 1;
        for (int j=0; j < lakes; j++) {
            Structs.Polygon.Builder pLake = getClosestPoly(lakecoords.get(j), newPolygons);
            makeLake(pLake, newPolygons);
        }
    }

    public void makeLake(Structs.Polygon.Builder pLake, List<Structs.Polygon.Builder> newPolygons) {
        Structs.Property.Builder lake = Structs.Property.newBuilder().setKey("Lake");
        pLake.addProperties(lake);
        Structs.Polygon.Builder lakeNeighbor;
        for (int n: pLake.getNeighborIdxsList()) {
            newPolygons.get(n).addProperties(lake);
        }
        for (int j = 0; j < lakeSize; j++) {
            lakeNeighbor = newPolygons.get(pLake.getNeighborIdxsList().get(j));
            for (int n: lakeNeighbor.getNeighborIdxsList()) {
                newPolygons.get(n).addProperties(lake);
            }
        }
    }

    private Structs.Polygon.Builder getClosestPoly(Point point, List<Structs.Polygon.Builder> newPolygons) {
        double min_distance = 100000; //arbitrary
        Structs.Polygon.Builder pLakeCenter = newPolygons.get(0);
        int xLake = point.x;
        int yLake = point.y;
        for (int i = 0; i < newPolygons.size(); i++) {
            Structs.Polygon.Builder p = newPolygons.get(i);
            for (int j = 0; j < p.getPropertiesList().size(); j++) {
                Structs.Property property = p.getPropertiesList().get(j);
                if (property.getKey().equals("Centroid")) {
                    String[] coord = property.getValue().split(",");
                    double x = Double.parseDouble(coord[0]);
                    double y = Double.parseDouble(coord[1]);
                    double distance = Math.sqrt(Math.pow(xLake - x, 2) + Math.pow(yLake - y, 2));
                    if (distance < min_distance) {
                        min_distance = distance;
                        pLakeCenter = p;
                    }
                }
            }
        }
        return pLakeCenter;
    }
}
