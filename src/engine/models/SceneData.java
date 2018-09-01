package engine.models;

import engine.models.components.Camera;
import engine.models.components.Light;
import engine.models.components.Geometry;

import java.util.List;

public class SceneData {
    public Camera camera;
    public List<Light> lights;
    public List<Geometry> geometries;
}
