package engine.models.components.lights;

import engine.util.RGBColor;
import engine.util.Vector3D;
import javafx.scene.paint.Color;

public abstract class Light {
    public Vector3D position;
    public double intensity;
    // Viewport only allows intensity up to 1.0, not enough for rendered image
    public RGBColor color;

    public Light(Vector3D position, double intensity, RGBColor color) {
        this.position = position;
        this.intensity = intensity;
        this.color = color;
    }

    public abstract Vector3D getDirection(Vector3D point);

    public abstract RGBColor getIntensity(Vector3D point);

    public Color toJavaFXLightColor() {
        return new Color(color.red * intensity, color.green * intensity, color.blue * intensity, 1);
    }
}
