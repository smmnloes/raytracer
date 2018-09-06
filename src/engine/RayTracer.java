package engine;

import engine.models.SceneData;
import engine.models.components.geometry.Geometry;
import engine.models.components.lights.Light;
import engine.util.*;

public class RayTracer {
    private static final int IMAGE_HEIGHT = Options.IMAGE_HEIGHT;
    private static final int IMAGE_WIDTH = Options.IMAGE_WIDTH;

    private final SceneData sceneData;

    private final RGBColor BACKGROUND_COLOR = Options.BACKGROUND_COLOR;

    public RayTracer(SceneData sceneData) {
        this.sceneData = sceneData;
    }


    public int[][] render() {
        int[][] imageBuffer = new int[IMAGE_HEIGHT][IMAGE_WIDTH];

        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                double fov = sceneData.camera.fov;
                double scale = Math.tan(fov / 2d * Math.PI / 180d);


                double Px, Py;

                if (IMAGE_WIDTH >= IMAGE_HEIGHT) {
                    double imageAspectRatio =  (double)IMAGE_WIDTH / (double)IMAGE_HEIGHT; // assuming width > height
                    Px = (2d * (x + 0.5d) / (double)IMAGE_WIDTH - 1d) * scale * imageAspectRatio;
                    Py = (1d - 2d * (y + 0.5d) / (double)IMAGE_HEIGHT) * scale;
                } else {
                    double imageAspectRatio = (double)IMAGE_WIDTH / (double)IMAGE_HEIGHT; // assuming width < height
                    Px = (2d * ((x + 0.5d) / (double)IMAGE_WIDTH) - 1d) * scale * imageAspectRatio;
                    Py = (1d - 2d * (y + 0.5d) / (double)IMAGE_HEIGHT) * scale;
                }

                // TODO: add support for camera movement
                Vector3D rayOrigin = new Vector3D(0, 0, 0);
                Vector3D rayDirection = new Vector3D(Px, Py, 1).minus(rayOrigin); // note that this just equal to Vec3f(Px, Py, -1);
                rayDirection.normalize(); // it's a direction so don't forget to normalize

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
                double distance = intersection.t;
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestIntersection = intersection;
                }
            }

        }
        return shade(closestIntersection, ray);
    }


    private RGBColor shade(Intersection intersection, Ray ray) {
        if (intersection == null) {
            return BACKGROUND_COLOR;
        } else {
            Vector3D hitPoint = sceneData.camera.position.plus(ray.direction.times(intersection.t));
            Vector3D normal = intersection.geometry.getNormal(hitPoint);

            RGBColor finalColor = RGBColor.black();

            for (Light light : sceneData.lights) {
                Vector3D L = light.getDirection(hitPoint).negate();
                finalColor = finalColor.add(intersection.geometry.material.color
                        .divideBy(Math.PI)
                        .times(light.getIntensity(hitPoint))
                        .times(Math.max(0.d, normal.dotProduct(L))));
            }

            return finalColor;
        }
    }


}
