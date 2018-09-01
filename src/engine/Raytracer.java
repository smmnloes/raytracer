package engine;

import engine.models.SceneData;
import engine.models.components.Geometry;
import engine.util.Intersection;
import engine.util.Ray;
import engine.util.Vector3D;

public class Raytracer {
    private static final int IMAGE_HEIGHT = 500;
    private static final int IMAGE_WIDTH = 500;

    private final SceneData sceneData;

    private final int BACKGROUND_COLOR = 0;

    public Raytracer(SceneData sceneData) {
        this.sceneData = sceneData;
    }


    public int[][] render() {
        int[][] imageBuffer = new int[IMAGE_HEIGHT][IMAGE_WIDTH];

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_HEIGHT; x++) {
                double fov = sceneData.camera.fov;
                double imageAspectRatio = IMAGE_WIDTH / IMAGE_HEIGHT; // assuming width > height
                double scale = Math.tan(fov / 2 * Math.PI / 180);
                double Px = (2 * ((x + 0.5) / IMAGE_WIDTH) - 1) * scale * imageAspectRatio;
                double Py = (1 - 2 * ((y + 0.5) / IMAGE_HEIGHT) * scale);
                // TODO: add support for camera movement
                Vector3D rayOrigin = new Vector3D(0, 0, 0);
                Vector3D rayDirection = Vector3D.minus(new Vector3D(Px, Py, -1), rayOrigin); // note that this just equal to Vec3f(Px, Py, -1);
                rayDirection.normalize(); // it's a rotation so don't forget to normalize

                imageBuffer[y][x] = trace(new Ray(rayOrigin, rayDirection));
            }
        }
        return imageBuffer;
    }

    private int trace(Ray ray) {
        double closestDistance = Double.MAX_VALUE;
        Intersection closestIntersection = null;

        for (Geometry geometry : sceneData.geometries) {
            Intersection intersection = geometry.intersects(ray);

            if (intersection != null) {
                double distance = Vector3D.distance(intersection.hitPoint, sceneData.camera.position);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestIntersection = intersection;
                }
            }

        }
        return closestIntersection == null ? BACKGROUND_COLOR : shade(closestIntersection);
    }


    private int shade(Intersection intersection) {
        return 0xFFFFFFFF;
    }


}
