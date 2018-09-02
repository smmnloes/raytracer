package engine.util;

import engine.models.components.geometry.Geometry;

public class Intersection {
    public Geometry geometry;
    public double t;

    public Intersection(Geometry geometry, double t) {
        this.geometry = geometry;
        this.t = t;
    }
}
