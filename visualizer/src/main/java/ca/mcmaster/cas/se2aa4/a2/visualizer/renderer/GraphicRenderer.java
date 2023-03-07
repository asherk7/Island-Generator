package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
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
        List<Structs.Property> property_list = p.getPropertiesList();
        for (Structs.Property property: property_list){
            if (property.getKey().equals("Biome")){
                if (property.getValue().equals("Lagoon")){
                    c = new Color(173, 216, 230);
                }
                else if (property.getValue().equals("Land")){
                    c = new Color(0, 255, 0);
                }
                //else if (property.getValue().equals("Beach")){
                //
                //}
                else if (property.getValue().equals("Ocean")){
                    c = new Color(0, 0, 255);
                }
            }
        }
        Color old = canvas.getColor();
        canvas.setColor(c);
        canvas.fill(path);
        canvas.setColor(old);
    }

}
