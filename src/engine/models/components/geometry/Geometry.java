package engine.models.components.geometry;

import engine.models.components.Material;
import engine.util.Intersection;
import engine.util.Ray;
import engine.util.Vector3D;

public abstract class Geometry {
    public Vector3D position, rotation, scale;
    public Material material;

    public Geometry(Vector3D position, Vector3D rotation, Vector3D scale, Material material) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;
    }

    public abstract Intersection intersects(Ray ray);

    public abstract Vector3D getNormal(Vector3D hitPoint);
}
