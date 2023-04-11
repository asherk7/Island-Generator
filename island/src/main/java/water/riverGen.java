package water;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.*;

public class riverGen {
    Random rand = new Random();
    humidity humidity = new humidity();
    private List<Structs.Segment.Builder> riverList = new ArrayList<>();

    private HashMap<Integer, Boolean> watersegment = new HashMap<>();
    private HashMap<Integer, Boolean> alreadyRiver = new HashMap<>();
    private HashMap<Integer, List<Integer>> vertexSegments = new HashMap<>();
    private HashMap<Integer, List<Structs.Polygon.Builder>> segmentPolygons = new HashMap<>();

    public void drawRivers(int rivers, List<Structs.Polygon.Builder> polygonList, Structs.Mesh.Builder mesh, int repeat){
        createMaps(mesh, polygonList);
        eliminateWaterSegments(polygonList);
        for (int i = 0; i<rivers; i++){
            try {
                int riverPos = findRiver(polygonList);
                Set<Integer> visitedVertices = new HashSet<>();
                List<Integer> currentIterationRivers = new ArrayList<>();
                int thickness = rand.nextInt(2, 4);
                makeRiver(mesh, polygonList, riverPos, repeat, visitedVertices, currentIterationRivers, thickness);
                assignRivers(mesh);
            } catch(Exception e){
                //new river couldn't be formed due to biome issues
            }
        }
        humidity.assignRiverHumidity(mesh, alreadyRiver, segmentPolygons);
    }

    public void eliminateWaterSegments(List<Structs.Polygon.Builder> polygonList){
        for (Structs.Polygon.Builder p:polygonList){
            for (Structs.Property property : p.getPropertiesList()){
                if(property.getKey().equals("Biome")) {
                    if (property.getValue().equals("lake") || property.getValue().equals("ocean")) {
                        for (Integer index : p.getSegmentIdxsList()) {
                            watersegment.put(index, true);
                        }
                    }
                }
            }
        }
    }

    public int findRiver(List<Structs.Polygon.Builder> polygonList){
        List<Structs.Polygon.Builder> polygon_copy = new ArrayList<>(polygonList);
        Collections.shuffle(polygon_copy);
        //creating a shuffled list, so we can iterate through random polygons
        for (Structs.Polygon.Builder polygon : polygon_copy) {
            for(Integer index: polygon.getSegmentIdxsList()){
                if (!(watersegment.get(index) || alreadyRiver.get(index))){
                    return index;
                }
            }
        }
        return -1;
    }

    public void makeRiver(Structs.Mesh.Builder mesh, List<Structs.Polygon.Builder> polygonList, int riverPos, int repeat, Set<Integer> visitedVertices, List<Integer> currentIterationRivers, int thickness){
        List<Structs.Polygon.Builder> p_list = segmentPolygons.get(riverPos);
        Structs.Segment s = mesh.getSegments(riverPos);
        visitedVertices.add(s.getV1Idx());
        currentIterationRivers.add(riverPos);
        if (repeat==0){
            createLake(p_list, polygonList);
            return;
        }
        for (Structs.Polygon.Builder p : p_list){
            for (Structs.Property property : p.getPropertiesList()){
                if (property.getKey().equals("Biome") && (property.getValue().equals("lake") || property.getValue().equals("ocean"))){
                    return;
                }
            }
        }

        if (visitedVertices.contains(s.getV2Idx())){ //makes sure we don't loop in a circle back to a river around a polygon
            List<Structs.Polygon.Builder> wantedPolygons = new ArrayList<>(p_list);
            int possiblyRiver = 0;
            for (Integer i :vertexSegments.get(s.getV2Idx())){
                for (Structs.Property prop : mesh.getSegments(i).getPropertiesList()){
                    if (prop.getKey().equals("River")){
                        possiblyRiver = i;
                    }
                }
            }
            List<Structs.Polygon.Builder> unwantedPolygons = segmentPolygons.get(possiblyRiver);
            for (Structs.Polygon.Builder p: unwantedPolygons){
                wantedPolygons.remove(p);
            }
            createLake(wantedPolygons, polygonList);
            return;
        }

        List<Integer> connectedsegments = vertexSegments.get(s.getV2Idx());
        for (Integer i : connectedsegments){
            if (alreadyRiver.get(i)){
                visitedVertices.add(s.getV2Idx());
                Structs.Segment.Builder temp_s = s.toBuilder();
                Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(thickness)).build();
                temp_s.addProperties(river);
                s = temp_s.build();
                mesh.setSegments(riverPos, s);
                mergeRiver(i, thickness, mesh, currentIterationRivers);
                return;
            }
        }

        Structs.Polygon.Builder p = null;
        int elev = getElevation(p_list.get(0));
        p = getLowElevPolygon(elev, connectedsegments, currentIterationRivers, false);
        if (p == null){
            p = getLowElevPolygon(elev, connectedsegments, currentIterationRivers, true);
            repeat -= 1;
        }
        if (p==null){
            createLake(p_list, polygonList);
        }
        else{
            int newPosition = 0;
            for (Integer i :p.getSegmentIdxsList()){
                if (connectedsegments.contains(i) && !currentIterationRivers.contains(i)){
                    newPosition = i;
                }
            }
            visitedVertices.add(s.getV2Idx());
            Structs.Segment.Builder temp_s = s.toBuilder();
            Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(thickness)).build();
            temp_s.addProperties(river);
            s = temp_s.build();
            mesh.setSegments(riverPos, s);
            makeRiver(mesh, polygonList, newPosition, repeat, visitedVertices, currentIterationRivers, thickness);
        }
    }

    public void createMaps(Structs.Mesh.Builder mesh, List<Structs.Polygon.Builder> polygonList){
        for (int i = 0; i < mesh.getSegmentsList().size(); i++){
            List<Structs.Polygon.Builder> empty = new ArrayList<>();
            segmentPolygons.put(i,empty);
        }
        for (Structs.Polygon.Builder p : polygonList){
            for (Integer index : p.getSegmentIdxsList()){
                List<Structs.Polygon.Builder> poly_list = segmentPolygons.get(index);
                poly_list.add(p);
                segmentPolygons.put(index, poly_list);
            }
        }

        for (int j = 0; j < mesh.getVerticesList().size(); j++){
            List<Integer> segments = new ArrayList<>();
            vertexSegments.put(j, segments);
        }
        for (int k = 0; k < mesh.getSegmentsList().size(); k++){
            Structs.Segment s = mesh.getSegments(k);
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            List<Integer> segments = vertexSegments.get(v1);
            segments.add(k);
            vertexSegments.put(v1, segments);
            segments = vertexSegments.get(v2);
            segments.add(k);
            vertexSegments.put(v2, segments);
        }

        for (Structs.Polygon.Builder p:polygonList){
            for (Integer index : p.getSegmentIdxsList()) {
                watersegment.put(index, false);
                alreadyRiver.put(index, false);
            }
        }
    }

    public int getElevation(Structs.Polygon.Builder polygon) {
        for (Structs.Property property : polygon.getPropertiesList()) {
            if (property.getKey().equals("Elevation")) {
                return Integer.parseInt(property.getValue());
            }
        }
        return -1;
    }

    public int getBiomeProperty(Structs.Polygon.Builder polygon) {
        for (int i = 0; i < polygon.getPropertiesList().size(); i++) {
            if (polygon.getProperties(i).getKey().equals("Biome")) {
                return i;
            }
        }
        return -1;
    }

    public Structs.Polygon.Builder getLowElevPolygon(int elev, List<Integer> connectedsegments, List<Integer> currentIterationRivers, Boolean lower){
        for (Integer i : connectedsegments){
            if (!currentIterationRivers.contains(i)){
                List<Structs.Polygon.Builder> temp_list = segmentPolygons.get(i);
                for (Structs.Polygon.Builder p1 : temp_list){
                    if (!lower){
                        if (elev > getElevation(p1)){
                            return p1;
                        }
                    }
                    else{
                        if (elev == getElevation(p1)){
                            return p1;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void createLake(List<Structs.Polygon.Builder> p_list, List<Structs.Polygon.Builder> polygonList){
        for (Structs.Polygon.Builder p : p_list) {
            p.removeProperties(getBiomeProperty(p));
            Structs.Property biome = Structs.Property.newBuilder().setKey("Biome").setValue("lake").build();
            p.addProperties(biome);
            humidity.assignLakeHumidity(polygonList);
            return;
        }
    }

    public void assignRivers(Structs.Mesh.Builder mesh){
        for (int i =0; i< mesh.getSegmentsList().size(); i++){
            Structs.Segment s = mesh.getSegments(i);
            for (Structs.Property property : s.getPropertiesList()){
                if (property.getKey().equals("River")){
                    alreadyRiver.put(i, true);
                }
            }
        }
    }

    public void mergeRiver(int position, int thickness, Structs.Mesh.Builder mesh, List<Integer> currentIterationRivers){
        List<Structs.Polygon.Builder> p_list = segmentPolygons.get(position);
        Structs.Segment s = mesh.getSegments(position);
        for (int i = 0; i < s.getPropertiesList().size(); i++) {
            if (s.getProperties(i).getKey().equals("River")) {
                int oldthickness = Integer.parseInt(s.getProperties(i).getValue());
                Structs.Segment.Builder s1 = s.toBuilder();
                s1.removeProperties(i);
                Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(thickness+oldthickness)).build();
                s1.addProperties(river);
                s = s1.build();
                mesh.setSegments(position, s);
                break;
            }
        }
        List<Integer> connectedsegments = vertexSegments.get(s.getV2Idx());
        Structs.Polygon.Builder p = null;
        int elev = getElevation(p_list.get(0));
        p = getLowElevPolygon(elev, connectedsegments, currentIterationRivers, false);
        if (p == null){
            p = getLowElevPolygon(elev, connectedsegments, currentIterationRivers, true);
        }
        if (p==null){
            return;
        }
        else{
            int newPosition = 0;
            for (Integer i :p.getSegmentIdxsList()){
                if (connectedsegments.contains(i) && !currentIterationRivers.contains(i)){
                    newPosition = i;
                }
            }
            //implement visited vertices
            visitedVertices.add(s.getV2Idx());
            Structs.Segment.Builder temp_s = s.toBuilder();
            Structs.Property river = Structs.Property.newBuilder().setKey("River").setValue(String.valueOf(thickness)).build();
            temp_s.addProperties(river);
            s = temp_s.build();
            mesh.setSegments(riverPos, s);
            makeRiver(mesh, polygonList, newPosition, repeat, visitedVertices, currentIterationRivers, thickness);
    }
}