package shapes;

import java.awt.geom.Path2D;

public class Rectangle implements Shape<Path2D> {
    @Override
    public Path2D drawShape(int width, int height) {
        Path2D rect = new Path2D.Float();
        float x1 = (float) width/4;
        float x2 = (float) 2*width/3;
        float y1 = (float) height/7;
        float y2 = (float) 2*height/3;

        rect.moveTo(x1, y1);
        rect.lineTo(x2, y1);
        rect.lineTo(x2, y2);
        rect.lineTo(x1, y2);
        rect.lineTo(x1, y1);
        rect.closePath();
        return rect;
    }
}
