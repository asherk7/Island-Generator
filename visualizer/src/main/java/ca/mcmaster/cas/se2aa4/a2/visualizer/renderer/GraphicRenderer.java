package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas);
        drawSegments(aMesh, canvas);
        drawVertices(aMesh, canvas);
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
    }

    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) {
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);

        Color c = null;
        String[] code = null;
        List<Structs.Property> property_list = p.getPropertiesList();
        for (Structs.Property property: property_list){
            if (property.getKey().equals("Color")){

                code = property.getValue().split(",");
                int red = Integer.parseInt(code[0]);
                int green = Integer.parseInt(code[1]);
                int blue = Integer.parseInt(code[2]);

                c = new Color(red, green, blue);
            }
        }
        Color old = canvas.getColor();
        canvas.setColor(c);
        canvas.fill(path);
        canvas.setColor(old);
    }

    private void drawSegments(Mesh aMesh, Graphics2D canvas){
        for(Structs.Segment s: aMesh.getSegmentsList()){
            drawASegment(s, aMesh, canvas);
        }
    }

    private void drawASegment(Structs.Segment segment, Mesh aMesh, Graphics2D canvas){
        Color c = null;
        String[] code = null;
        int thickness;
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        for(Structs.Property p : segment.getPropertiesList()){
            if (p.getKey().equals("River")){
                thickness = Integer.parseInt(p.getValue());
                stroke = new BasicStroke(thickness);
                canvas.setStroke(stroke);
            }
            else if(p.getKey().equals("Path")){
                stroke = new BasicStroke(5);
                canvas.setStroke(stroke);
            }
        }
        for (Structs.Property property: segment.getPropertiesList()){
            if (property.getKey().equals("Color")){

                code = property.getValue().split(",");
                int red = Integer.parseInt(code[0]);
                int green = Integer.parseInt(code[1]);
                int blue = Integer.parseInt(code[2]);

                c = new Color(red, green, blue);
                Color old = canvas.getColor();
                canvas.setColor(c);
                Vertex v1_p = aMesh.getVerticesList().get(segment.getV1Idx());
                Vertex v2_p = aMesh.getVerticesList().get(segment.getV2Idx());

                canvas.drawLine((int)v1_p.getX(), (int)v1_p.getY(), (int)v2_p.getX(), (int)v2_p.getY());
                canvas.setColor(old);
            }
        }
    }

    private void drawVertices(Mesh aMesh, Graphics2D canvas){
        for(Structs.Vertex v: aMesh.getVerticesList()){
            drawAVertex(v, canvas);
        }
    }

    private void drawAVertex(Vertex v, Graphics2D canvas){
        Color c = null;
        String[] code = null;
        int size = 3;
        Boolean important = false;
        List<Structs.Property> property_list = v.getPropertiesList();
        for (Structs.Property property: property_list){
            if (property.getKey().equals("Color")){
                important = true;

                code = property.getValue().split(",");
                int red = Integer.parseInt(code[0]);
                int green = Integer.parseInt(code[1]);
                int blue = Integer.parseInt(code[2]);

                c = new Color(red, green, blue);
            }
            if (property.getKey().equals("City")){
                size = Integer.parseInt(property.getValue());
            }
        }
        if (important) {
            canvas.setColor(c);
            Ellipse2D circle = new Ellipse2D.Float((float) (v.getX() - (size/2)), (float) (v.getY() - (size/2)), size, size);
            canvas.fill(circle);
        }
    }
}
