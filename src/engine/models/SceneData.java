package engine.models;

import engine.models.components.Camera;
import engine.models.components.Light;
import engine.models.components.Object;

import java.util.List;

public class SceneData {
    public Camera camera;
    public List<Light> lights;
    public List<Object> objects;
}
