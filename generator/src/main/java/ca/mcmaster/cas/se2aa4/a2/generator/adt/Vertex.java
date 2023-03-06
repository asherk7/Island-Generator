package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import java.util.Objects;

public class Vertex implements Cropable<Vertex> {

    private static final int PRECISION = 2;
    private final int x, y;

    public Vertex(float x, float y) {
        this.x = convert(x);
        this.y = convert(y);
    }

    public float x() {
        return this.x / (float) Math.pow(10, PRECISION);
    }

    public float y() {
        return this.y / (float) Math.pow(10, PRECISION);
    }

    @Override
    public Vertex crop(float maxX, float maxY) {
        float newX = Math.max(0.0f, Math.min(this.x(), maxX));
        float newY = Math.max(0.0f, Math.min(this.y(), maxY));
        return new Vertex(newX, newY);
    }

    private int convert(float x) {
        return (int) Math.round(x*Math.pow(10, PRECISION));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x && y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x() +
                ", " + y() +
                ')';
    }
}
