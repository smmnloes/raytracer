package gui;

import engine.Raytracer;
import engine.models.SceneData;
import engine.models.components.*;
import engine.util.RGBColor;
import engine.util.Vector3D;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Sphere sphere = new Sphere(new Vector3D(0, 0, -10), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 5, new Material(new RGBColor(1,1,1)));
        Light light = new PointLight(new Vector3D(-3, 5, -2), 500, new RGBColor(0.5,1,1));
        sceneData.lights.add(light);
        sceneData.geometries.add(sphere);
        Raytracer raytracer = new Raytracer(sceneData);

        viewPort.drawImage(raytracer.render());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
