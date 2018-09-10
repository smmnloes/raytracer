package engine.models.components;

import engine.util.Vector3D;

public class Camera {
    public Vector3D position, rotation;
    public float fov;

    public Camera(Vector3D origin, Vector3D rotation, float fov) {
        this.position = origin;
        this.rotation = rotation;
        this.fov = fov;
    }

    public double[][] calculateToWorld() {
        return Vector3D.createToWorldMatrix(position, rotation, new Vector3D(1, 1, 1));
    }
}
