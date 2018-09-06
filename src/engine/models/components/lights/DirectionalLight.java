package engine.models.components.lights;

import engine.util.RGBColor;
import engine.util.Vector3D;

public class DirectionalLight extends Light {
    public Vector3D direction;
    private static final int INTENSITY_MUTLIPLIER = 5;

    public DirectionalLight(Vector3D position, double intensity, RGBColor color, Vector3D direction) {
        super(position, intensity, color);
        this.direction = direction.normalize();
    }

    public Vector3D getDirection(Vector3D point) {
        return direction;
    }

    public RGBColor getIntensity(Vector3D point) {
        return this.color.times(intensity * INTENSITY_MUTLIPLIER);
    }

}
