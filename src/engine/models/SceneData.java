package engine.models;

import engine.models.components.Camera;
import engine.models.components.geometry.Geometry;
import engine.models.components.lights.Light;
import engine.util.RGBColor;

import java.util.ArrayList;
import java.util.List;

public class SceneData {
    public Camera camera;
    public List<Light> lights;
    public List<Geometry> geometries;
    public SceneOptions sceneOptions;

    public SceneData() {
        lights = new ArrayList<>();
        geometries = new ArrayList<>();
        sceneOptions = new SceneOptions();
    }

    public class SceneOptions {

        // Initialize Options with standard values
        public int IMAGE_HEIGHT = 500;
        public int IMAGE_WIDTH = 500;
        public RGBColor BACKGROUND_COLOR = RGBColor.grey();
    }

}
