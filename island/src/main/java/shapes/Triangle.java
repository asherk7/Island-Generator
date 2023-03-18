package shapes;

import java.awt.geom.Path2D;

public class Triangle implements Shape<Path2D> {
    @Override
    public Path2D drawShape(int width, int height) {
        Path2D triangle = new Path2D.Float();
        float x1 = (float) width/2;
        float x2 = (float) width/3;
        float x3 = (float) 3*width/4;
        float y1 = (float) height/7;
        float y2 = (float) 3*height/4;

        triangle.moveTo(x1, y1);
        triangle.lineTo(x3, y2);
        triangle.lineTo(x2, y2);
        triangle.lineTo(x1, y1);
        return triangle;
    }
}
