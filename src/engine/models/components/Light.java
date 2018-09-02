package engine.models.components;

import engine.util.RGBColor;
import engine.util.Vector3D;

public abstract class Light {
    public Vector3D position;
    public double intensity;
    public RGBColor color;

    public Light(Vector3D position, double intensity, RGBColor color) {
        this.position = position;
        this.intensity = intensity;
        this.color = color;
    }

    public abstract Vector3D getDirection(Vector3D point);

    public abstract RGBColor getIntensity(Vector3D point);
}
