package gui;


import engine.RayTracer;
import engine.models.SceneData;
import engine.models.components.Camera;
import engine.models.components.Material;
import engine.models.components.geometry.Sphere;
import engine.models.components.lights.DirectionalLight;
import engine.models.components.lights.Light;
import engine.models.components.lights.PointLight;
import engine.util.Options;
import engine.util.RGBColor;
import engine.util.Vector3D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneData sceneData = getSampleSceneData();

        ViewPort viewPort = new ViewPort();
        RenderView renderView = new RenderView();

        primaryStage.setTitle("raytracer");
        Scene viewPortScene = viewPort.getViewPortScene(sceneData);
        Scene renderViewScene = renderView.getRenderViewScene();
        primaryStage.setScene(viewPortScene);

        primaryStage.setHeight(Options.IMAGE_HEIGHT);
        primaryStage.setWidth(Options.IMAGE_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();

        // TODO: create controller to switch scenes
        viewPortScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case R:
                    primaryStage.setScene(renderViewScene);
                    renderTestScene(renderView);break;
            }
        });

        renderViewScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case I:
                    primaryStage.setScene(viewPortScene);
            }
        });

    }

    private void renderTestScene(RenderView renderView) {
        SceneData sceneData = getSampleSceneData();
        RayTracer rayTracer = new RayTracer(sceneData);
        renderView.drawImage(rayTracer.render());}

    private static SceneData getSampleSceneData() {
        SceneData sceneData = new SceneData();
        sceneData.camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 120);
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
