package engine.models.components.lights;


import engine.util.RGBColor;
import engine.util.Vector3D;

public class PointLight extends Light {
    private static final int INTENSITY_MUTLIPLIER = 1500;

    public PointLight(Vector3D position, double intensity, RGBColor color) {
        super(position, intensity, color);
    }

    public Vector3D getDirection(Vector3D point) {
        return (point.minus(position).normalize());
    }

    public RGBColor getIntensity(Vector3D point) {
        Vector3D direction = point.minus(position);
        double distance = direction.length();
        double sqFalloff = 1.0 / (4.0 * Math.PI * (distance * distance));
        return this.color.times(intensity * INTENSITY_MUTLIPLIER).times(sqFalloff);
    }

}
