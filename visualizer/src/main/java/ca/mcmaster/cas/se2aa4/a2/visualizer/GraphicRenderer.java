package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.net.SocketTimeoutException;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas, Boolean debug_mode) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);

        //Draw Vertices, EXCLUDING centroids
        List<Vertex> vertices = aMesh.getVerticesList();
        for (int i = 0; i < 625; i++) {
            Vertex v = vertices.get(i);
            
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            if (debug_mode){
                Color c = new Color(0, 0, 0);
                canvas.setColor(c);
            } else {
                canvas.setColor(extractColor(v.getPropertiesList()));
            }
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
        //Draw polygons
        for (Polygon p: aMesh.getPolygonsList()){
            //Draw each segment for Polygon, p
            for (int i: p.getSegmentIdxsList()){
                Segment s_p = aMesh.getSegments(i);

                int index_v1_p = s_p.getV1Idx();
                int index_v2_p = s_p.getV2Idx();

                Vertex v1_p = aMesh.getVertices(index_v1_p);
                Vertex v2_p = aMesh.getVertices(index_v2_p);
                
                Color old = canvas.getColor();
                if (debug_mode){
                    Color c = new Color(0, 0, 0);
                    canvas.setColor(c);
                } else {
                    canvas.setColor(extractColor(p.getPropertiesList()));
                }
                canvas.drawLine((int)v1_p.getX(), (int)v1_p.getY(), (int)v2_p.getX(), (int)v2_p.getY());
                canvas.setColor(old);
            }
        }
        
        //Draw centroid and neighbors, only in debug mode
        if (debug_mode){
            List<Polygon> polygons = aMesh.getPolygonsList();
            //Draw neighbor segments
            for (Polygon p: aMesh.getPolygonsList()){
                Vertex centroid = vertices.get(p.getCentroidIdx()+625);
                for (int i: p.getNeighborIdxsList()){
                    Polygon neighbor = polygons.get(i);
                    Vertex neighbor_centroid = vertices.get(neighbor.getCentroidIdx()+625);

                    //Draw a line between both points
                    Color old = canvas.getColor();
                    Color c = new Color(211, 211, 211);
                    canvas.setColor(c);
                    canvas.drawLine((int)centroid.getX(), (int)centroid.getY(), (int)neighbor_centroid.getX(), (int)neighbor_centroid.getY());
                    canvas.setColor(old);


                }
            }
            //Draw centroids
            for (int i = 625; i < aMesh.getVerticesCount(); i++){
                Vertex v_cent = vertices.get(i);
                double centre_x_cent = v_cent.getX() - (THICKNESS/2.0d);
                double centre_y_cent = v_cent.getY() - (THICKNESS/2.0d);
                Color old = canvas.getColor();
                canvas.setColor(extractColor(v_cent.getPropertiesList()));
                Ellipse2D point = new Ellipse2D.Double(centre_x_cent, centre_y_cent, THICKNESS, THICKNESS);
                canvas.fill(point);
                canvas.setColor(old);
            }
        }
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

}
