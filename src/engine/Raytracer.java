package engine;

import engine.models.SceneData;
import engine.models.components.Geometry;
import engine.models.components.Light;
import engine.util.Intersection;
import engine.util.RGBColor;
import engine.util.Ray;
import engine.util.Vector3D;

public class Raytracer {
    private static final int IMAGE_HEIGHT = 1000;
    private static final int IMAGE_WIDTH = 1000;

    private final SceneData sceneData;

    private final RGBColor BACKGROUND_COLOR = new RGBColor(0, 0, 0);

    public Raytracer(SceneData sceneData) {
        this.sceneData = sceneData;
    }


    public int[][] render() {
        int[][] imageBuffer = new int[IMAGE_HEIGHT][IMAGE_WIDTH];

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                double fov = sceneData.camera.fov;
                double scale = Math.tan(fov / 2 * Math.PI / 180);


                double Px, Py;

                if (IMAGE_WIDTH > IMAGE_HEIGHT) {
                    double imageAspectRatio = (double) IMAGE_WIDTH / (double) IMAGE_HEIGHT; // assuming width > height
                    Px = (2 * ((x + 0.5) / IMAGE_WIDTH) - 1) * scale * imageAspectRatio;
                    Py = (1 - 2 * ((y + 0.5) / IMAGE_HEIGHT) * scale);
                } else {
                    double imageAspectRatio = (double) IMAGE_HEIGHT / (double) IMAGE_WIDTH; // assuming width < height
                    Px = (2 * ((x + 0.5) / IMAGE_WIDTH) - 1) * scale;
                    Py = (1 - 2 * ((y + 0.5) / IMAGE_HEIGHT) * scale * imageAspectRatio);
                }

                // TODO: add support for camera movement
                Vector3D rayOrigin = new Vector3D(0, 0, 0);
                Vector3D rayDirection = new Vector3D(Px, Py, -1).minus(rayOrigin); // note that this just equal to Vec3f(Px, Py, -1);
                rayDirection.normalize(); // it's a rotation so don't forget to normalize

                imageBuffer[y][x] = trace(new Ray(rayOrigin, rayDirection)).toInt();
            }
        }
        return imageBuffer;
    }

    private RGBColor trace(Ray ray) {
        double closestDistance = Double.MAX_VALUE;
        Intersection closestIntersection = null;

        for (Geometry geometry : sceneData.geometries) {
            Intersection intersection = geometry.intersects(ray);

            if (intersection != null) {
                // TODO: use t directly
                double distance = intersection.hitPoint.distance(sceneData.camera.position);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestIntersection = intersection;
                }
            }

        }
        return shade(closestIntersection);
    }


    private RGBColor shade(Intersection intersection) {
        if (intersection == null) {
            return BACKGROUND_COLOR;
        } else {
            Light light = sceneData.lights.get(0);
            Vector3D hitPoint = intersection.hitPoint;
            Vector3D normal = intersection.geometry.getNormal(intersection.hitPoint);
            Vector3D L = light.getDirection(hitPoint).negate();
            return intersection.geometry.material.color
                    .divideBy(light.getIntensity(hitPoint))
                            .times(Math.PI)
                            .times(Math.max(0.d, normal.dotProduct(L)));
        }
    }


}
