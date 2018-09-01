package engine.models.components;

import engine.util.Intersection;
import engine.util.Vector3D;

public abstract class Geometry {
    public Vector3D position, rotation, scale;
    private Material material;

    public abstract Intersection intersects(Vector3D ray);
}
