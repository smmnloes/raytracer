package engine.util;

import engine.models.components.Geometry;

public class Intersection {
    public Geometry geometry;
    public Vector3D hitPoint;

    public Intersection(Geometry geometry, Vector3D hitPoint) {
        this.geometry = geometry;
        this.hitPoint = hitPoint;
    }
}
