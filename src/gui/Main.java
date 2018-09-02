package gui;

import engine.Raytracer;
import engine.models.SceneData;
import engine.models.components.*;
import engine.models.components.lights.DirectionalLight;
import engine.models.components.lights.Light;
import engine.models.components.lights.PointLight;
import engine.util.RGBColor;
import engine.util.Vector3D;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("raytracer");

        ViewPort viewPort = new ViewPort();

        root.getChildren().add(viewPort);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        renderTestScene(viewPort);
        primaryStage.setHeight(1000);
        primaryStage.setWidth(1000);
    }

    private void renderTestScene(ViewPort viewPort) {
        SceneData sceneData = new SceneData();
        sceneData.camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 90);
        Sphere sphere = new Sphere(new Vector3D(-5, 0, -15), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 5, new Material(new RGBColor(1, 1, 1)));
        Sphere sphere2 = new Sphere(new Vector3D(2, 0, -10), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 4, new Material(new RGBColor(1, 1, 1)));

        Light light = new PointLight(new Vector3D(3, 5, -2), 1000, new RGBColor(1,0,0));
        Light light2 = new DirectionalLight(null, 2, new RGBColor(1, 1, 1), new Vector3D(0.5, -0.5, -0.5));
        sceneData.lights.add(light);
        sceneData.lights.add(light2);
        sceneData.geometries.add(sphere);
        sceneData.geometries.add(sphere2);
        Raytracer raytracer = new Raytracer(sceneData);

        viewPort.drawImage(raytracer.render());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
