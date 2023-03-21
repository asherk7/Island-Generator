package lake;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class lakeGen {
    private List<Point> lakeCoords = new ArrayList<>();
    public int lakeSize;
    public void drawLakes(int lakes, List<Structs.Polygon.Builder> newPolygons) {
        this.lakeSize = 1;
        setLakeCoords(newPolygons);
        for (int j=0; j < lakes; j++) {
            Structs.Polygon.Builder pLake = getClosestPoly(lakeCoords.get(j), newPolygons);
            makeLake(pLake, newPolygons);
        }
    }
    public void setLakeCoords(List<Structs.Polygon.Builder> newPolygons) {
        Structs.Polygon.Builder pFirst = newPolygons.get(1);
        Structs.Polygon.Builder pLast = newPolygons.get(newPolygons.size()-1);
        int xDistance = getCentroidPoint(pLast).x/6;
        int yDistance = getCentroidPoint(pLast).y/4;
        for (int i = getCentroidPoint(pFirst).x + xDistance; i <= getCentroidPoint(pLast).x - xDistance; i += xDistance) {
            for (int j = getCentroidPoint(pFirst).y + yDistance; j <= getCentroidPoint(pLast).x - yDistance; j += yDistance) {
                Point lakeCoord = new Point();
                lakeCoord.x = i;
                lakeCoord.y = j;
                lakeCoords.add(lakeCoord);
            }
        }
    }

    private Point getCentroidPoint(Structs.Polygon.Builder pLast) {
        Point h = new Point();
        for (Structs.Property p: pLast.getPropertiesList()) {
            if (p.getKey().equals("Centroid")) {
                String[] coord = p.getValue().split(",");
                int x = Integer.parseInt(coord[0]);
                int y = Integer.parseInt(coord[1]);
                h.x = x;
                h.y = y;
            }
        }
        return h;
    }

    public void makeLake(Structs.Polygon.Builder pLake, List<Structs.Polygon.Builder> newPolygons) {
        List<Structs.Property> c = pLake.getPropertiesList();
        for (Structs.Property p: c) {
            if (p.getKey().equals("Biome")) {
                p.toBuilder().setValue("lake").build();
            }
        }
        Structs.Polygon.Builder lakeNeighbor;
        for (int n: pLake.getNeighborIdxsList()) {
            c = newPolygons.get(n).getPropertiesList();
            for (Structs.Property p: c) {
                if (p.getKey().equals("Biome")) {
                    p.toBuilder().setValue("lake").build();
                }
            }
        }
        for (int j = 0; j < lakeSize; j++) {
            lakeNeighbor = newPolygons.get(pLake.getNeighborIdxsList().get(j));
            for (int n: lakeNeighbor.getNeighborIdxsList()) {
                c = newPolygons.get(n).getPropertiesList();
                for (Structs.Property p: c) {
                    if (p.getKey().equals("Biome")) {
                        if (!(p.getValue().equals("ocean"))) {
                            p.toBuilder().setValue("lake").build();
                        }
                    }
                }
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
