package ca.mcmaster.cas.se2aa4.a2.generator.specification;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.Mesh;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.adt.Vertex;
import ca.mcmaster.cas.se2aa4.a2.generator.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.generator.neighborhoud.DelaunayNeighbourhood;
import ca.mcmaster.cas.se2aa4.a2.generator.neighborhoud.GridNeighborhood;

import java.util.Map;

public class GridSpecification implements Buildable {

    private final int width, height, squareSize;

    public GridSpecification(int width, int height, int squareSize) {
        checkSizeConstraint(width, height, squareSize);
        this.width = width;
        this.height = height;
        this.squareSize = squareSize;
    }

    GridSpecification(Map<String, String> params) {
        this(Integer.parseInt(params.get(Configuration.WIDTH)),
                Integer.parseInt(params.get(Configuration.HEIGHT)),
                Integer.parseInt(params.get(Configuration.SIZE_SQUARES)));
    }

    private void checkSizeConstraint(int w, int h, int s) {
        if (w % s != 0){
            throw new IllegalArgumentException("Cannot fit " + s + "squares in "+ w + "width");
        }
        if (h % s != 0){
            throw new IllegalArgumentException("Cannot fit " + s + "squares in "+ h + "height");
        }
    }

    @Override
    public Mesh build() {
        Mesh result = new Mesh(this.width, this.height);
        int nbSquareWidth = this.width / this.squareSize;
        int nbSquareHeight = this.height / this.squareSize;
        for(int i = 0; i < nbSquareWidth; i++){
            for(int j = 0; j < nbSquareHeight; j++){
                result.register(buildConvexSquare(i, j));
            }
        }
        result.populateNeighbours(new GridNeighborhood(squareSize));
        return result;
    }

    private Polygon buildConvexSquare(int i, int j) {
        Polygon aSquare = new Polygon();
        float xOrigin = i * squareSize;
        float yOrigin = j * squareSize;
        aSquare.add(new Vertex(xOrigin, yOrigin));
        aSquare.add(new Vertex(xOrigin+squareSize, yOrigin));
        aSquare.add(new Vertex(xOrigin+squareSize, yOrigin+squareSize));
        aSquare.add(new Vertex(xOrigin, yOrigin+squareSize));
        return aSquare;
    }

}
