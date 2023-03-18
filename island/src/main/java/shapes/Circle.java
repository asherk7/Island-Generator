package shapes;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class Circle implements Shape<Path2D> {
    @Override
    public Path2D drawShape(int width, int height) {
        float r1 = (2*height)/5;
        float r2 = (height)/4;
        Path2D circle = new Path2D.Float();
        circle.append(new Ellipse2D.Float((float) width/2, (float) height/2, r1, r2), true);
        return circle;
    }
}
