package engine.models.components;

import engine.util.RGBColor;
import engine.util.Vector3D;

public class PointLight extends Light {
    public PointLight(Vector3D position, double intensity, RGBColor color) {
        super(position, intensity, color);
    }

    public Vector3D getDirection(Vector3D point) {
        return (position.minus(point).normalize());
    }

    public RGBColor getIntensity(Vector3D point) {
        Vector3D direction = position.minus(point);
        double distance = direction.length();
        double sqFalloff = 1.0 / (4.0 * Math.PI * Math.pow(distance, 2));
        return this.color.times(intensity).times(sqFalloff);
    }

}
