package shapes;

import java.awt.geom.Path2D;

public class Rectangle implements Shape<Path2D> {
    @Override
    public Path2D drawShape(int width, int height) {
        Path2D rect = new Path2D.Float();
        //rect.moveTo();
        //rect.lineTo();
        //rect.lineTo();
        //rect.lineTo();
        //rect.lineTo();
        //rect.closePath();
        return rect;
    }
}
