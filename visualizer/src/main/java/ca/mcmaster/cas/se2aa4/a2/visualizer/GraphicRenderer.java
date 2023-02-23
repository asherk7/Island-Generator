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
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
        /* 
        //Draw Segment
        for (Segment s: aMesh.getSegmentsList()){
            int index_v1 = s.getV1Idx();
            int index_v2 = s.getV2Idx();

            Vertex v1 = aMesh.getVertices(index_v1);
            Vertex v2 = aMesh.getVertices(index_v2);

            Color old = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList()));
            canvas.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
            canvas.setColor(old);
        }
        */
        //Draw polygons
        for (Polygon p: aMesh.getPolygonsList()){

            //Segments
            for (int i: p.getSegmentIdxsList()){
                Segment s_p = aMesh.getSegments(i);

                int index_v1_p = s_p.getV1Idx();
                int index_v2_p = s_p.getV2Idx();

                Vertex v1_p = aMesh.getVertices(index_v1_p);
                Vertex v2_p = aMesh.getVertices(index_v2_p);

                Color old = canvas.getColor();
                canvas.setColor(extractColor(p.getPropertiesList()));
                canvas.drawLine((int)v1_p.getX(), (int)v1_p.getY(), (int)v2_p.getX(), (int)v2_p.getY());
                canvas.setColor(old);

            }
            
        }
        System.out.println("Polygon construction completed");
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
