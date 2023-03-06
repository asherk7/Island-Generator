import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.DebugRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvasProvider;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.Renderer;

import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        Graphics2D canvas = SVGCanvasProvider.build(aMesh);
        Renderer renderer = (config.debug()? new DebugRenderer(): new GraphicRenderer());
        renderer.render(aMesh, canvas);
        SVGCanvasProvider.write(canvas, config.output());
    }

}
