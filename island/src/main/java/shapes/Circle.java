package shapes;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class Circle implements Shape<Path2D> {
    @Override
    public Path2D drawShape(int width, int height) {
        int r1 = (2*height)/3;
        Path2D circle = new Path2D.Float();
        circle.append(new Ellipse2D.Float((float) width/4, (float) height/7, (float) r1, (float) r1), true);
        return circle;
    }
}
