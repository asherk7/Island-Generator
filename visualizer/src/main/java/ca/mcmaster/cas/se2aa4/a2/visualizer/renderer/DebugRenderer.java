package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Set;

public class DebugRenderer implements Renderer {

    private Renderer foundation;

    public DebugRenderer() {
        this(new GraphicRenderer());
    }

    public DebugRenderer(Renderer foundation) {
        this.foundation = foundation;
    }

    @Override
    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        // Delegating to the foundation
        this.foundation.render(aMesh, canvas);
        // Setting
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawNeighbourhood(aMesh, canvas); // Superimpose neigbourhood relation
        drawCentroids(aMesh, canvas); // Superimpose centroids
    }

    private void drawCentroids(Structs.Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.RED);
        for(Structs.Polygon p: aMesh.getPolygonsList()) {
            Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());
            Ellipse2D circle = new Ellipse2D.Float((float) centroid.getX()-1.5f, (float) centroid.getY()-1.5f,
                                                3, 3);
            canvas.fill(circle);
        }
    }

    private void drawNeighbourhood(Structs.Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.LIGHT_GRAY);
        Set<Set<Structs.Polygon>> drawn = new HashSet<>();
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());
            for(Integer neigbourIdx: p.getNeighborIdxsList()){
                Structs.Polygon neighbour = aMesh.getPolygons(neigbourIdx);
                Structs.Vertex neighbourCentroid = aMesh.getVertices(neighbour.getCentroidIdx());
                if(!drawn.contains(Set.of(p, neighbour))){
                    drawLink(centroid, neighbourCentroid, canvas);
                    drawn.add(Set.of(p, neighbour));
                }
            }
        }
    }

    private void drawLink(Structs.Vertex centroid, Structs.Vertex neighbourCentroid, Graphics2D canvas) {
        Line2D line = new Line2D.Float((float) centroid.getX(), (float) centroid.getY(),
                                        (float) neighbourCentroid.getX(),(float) neighbourCentroid.getY());
        canvas.draw(line);
    }

}
