package gui;


import engine.RayTracer;
import engine.models.SceneData;
import engine.models.components.*;
import engine.models.components.geometry.Sphere;
import engine.models.components.lights.DirectionalLight;
import engine.models.components.lights.Light;
import engine.models.components.lights.PointLight;
import engine.util.Options;
import engine.util.RGBColor;
import engine.util.Vector3D;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneData sceneData = getSampleSceneData();

        ViewPort viewPort = new ViewPort();
        primaryStage.setTitle("raytracer");
        primaryStage.setScene(viewPort.createViewPortScene(sceneData));
        primaryStage.show();

        renderTestScene(viewPort);

        primaryStage.setHeight(Options.IMAGE_HEIGHT);
        primaryStage.setWidth(Options.IMAGE_WIDTH);
    }

    private void renderTestScene(ViewPort viewPort) {
        SceneData sceneData = getSampleSceneData();
        RayTracer rayTracer = new RayTracer(sceneData);
        viewPort.render(rayTracer.render());}

    private static SceneData getSampleSceneData() {
        SceneData sceneData = new SceneData();
        sceneData.camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 90);
        Sphere sphere = new Sphere(new Vector3D(-10, 0, 12), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 4, new Material(RGBColor.white()));
        Sphere sphere2 = new Sphere(new Vector3D(2, 0, 10), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 4, new Material(RGBColor.white()));

        Light light = new PointLight(new Vector3D(-1, 7, 10), 500, RGBColor.red());
        Light light2 = new DirectionalLight(null, 2, RGBColor.white(), new Vector3D(0.5, -0.5, 0.5));
        sceneData.lights.add(light);
        sceneData.lights.add(light2);
        sceneData.geometries.add(sphere);
        sceneData.geometries.add(sphere2);

        return sceneData;

    }


    public static void main(String[] args) {
        launch(args);
    }
}
