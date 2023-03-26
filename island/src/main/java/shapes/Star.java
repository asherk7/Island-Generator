package shapes;

import java.awt.geom.Path2D;

public class Star implements Shape<Path2D> {
    @Override
    public Path2D drawShape(int width, int height) {
        Path2D star = new Path2D.Float();
        star.moveTo(width/2, height/8);
        star.lineTo(7*width/12, 2*height/5);
        star.lineTo(3*width/4, height/2);
        star.lineTo(7*width/12, 3*height/5);
        star.lineTo(7*width/11, 6*height/7);
        star.lineTo(width/2, 3*height/4);
        star.lineTo(4*width/11, 6*height/7);
        star.lineTo(5*width/12, 3*height/5);
        star.lineTo(width/4, height/2);
        star.lineTo(5*width/12, 2*height/5);
        star.lineTo(width/2, height/8);
        return star;
    }
}
