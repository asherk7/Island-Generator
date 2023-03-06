package ca.mcmaster.cas.se2aa4.a2.visualizer;

import java.awt.*;
import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class SVGCanvasProvider {

    public static Graphics2D build(Structs.Mesh aMesh) {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: aMesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        // Creating the Canvas to draw the mesh
        Graphics2D canvas = SVGCanvasProvider.build((int) Math.ceil(max_x), (int) Math.ceil(max_y));
        return canvas;
    }

    public static Graphics2D build(int width, int height) {
        DOMImplementation dom = GenericDOMImplementation.getDOMImplementation();
        String svgNS = "http://www.w3.org/2000/svg";
        Document doc = dom.createDocument(svgNS, "svg", null);
        SVGGraphics2D g = new SVGGraphics2D(doc);
        g.setSVGCanvasSize(new Dimension(width, height));
        return g;
    }

    public static void write(Graphics2D g, String fileName) throws IOException {
        if (! (g instanceof SVGGraphics2D))
            throw new IllegalArgumentException("Not an SVG canvas!");
        SVGGraphics2D svg = (SVGGraphics2D) g;
        svg.stream(fileName, true);
    }

}
