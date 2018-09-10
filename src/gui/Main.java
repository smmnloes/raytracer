package gui;


import engine.RayTracer;
import engine.models.SceneData;
import engine.models.components.Camera;
import engine.models.components.Material;
import engine.models.components.geometry.Sphere;
import engine.models.components.lights.DirectionalLight;
import engine.models.components.lights.Light;
import engine.models.components.lights.PointLight;
import engine.util.RGBColor;
import engine.util.Vector3D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneData sceneData = getSampleSceneData();

        ViewPort viewPort = new ViewPort();
        primaryStage.setTitle("Viewport");
        Scene viewPortScene = viewPort.getViewPortScene(sceneData);
        primaryStage.setScene(viewPortScene);
        primaryStage.setHeight(sceneData.sceneOptions.IMAGE_HEIGHT);
        primaryStage.setWidth(sceneData.sceneOptions.IMAGE_WIDTH);
        primaryStage.setResizable(false);

        primaryStage.show();

        Stage renderStage = new Stage();
        RenderView renderView = new RenderView();
        renderStage.setTitle("Rendered Image");
        Scene renderViewScene = renderView.getRenderViewScene();
        renderStage.setScene(renderViewScene);
        renderStage.setHeight(sceneData.sceneOptions.IMAGE_HEIGHT);
        renderStage.setWidth(sceneData.sceneOptions.IMAGE_WIDTH);
        renderStage.setResizable(false);

        viewPortScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case R:
                    renderTestScene(renderView, sceneData);
                    renderStage.show();
                    break;
                case UP:
                    sceneData.camera.position.y += 1;
                    viewPortScene.getCamera().getTransforms().add(new Translate(0, -1, 0));
                    break;
                case DOWN:
                    sceneData.camera.position.y -= 1;

                    viewPortScene.getCamera().getTransforms().add(new Translate(0, 1, 0));
                    break;

                case LEFT:
                    sceneData.camera.position.x += -1;

                    viewPortScene.getCamera().getTransforms().add(new Translate(-1, 0, 0));
                    break;

                case RIGHT:
                    sceneData.camera.position.x += 1;

                    viewPortScene.getCamera().getTransforms().add(new Translate(1, 0, 0));
                    break;
            }
        });
    }

    private void renderTestScene(RenderView renderView, SceneData sceneData) {
        RayTracer rayTracer = new RayTracer(sceneData);
        renderView.drawImage(rayTracer.render());
    }

    private static SceneData getSampleSceneData() {
        SceneData sceneData = new SceneData();

        sceneData.camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 90);
        Sphere sphere = new Sphere(new Vector3D(-10, 2, 12), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 4, new Material(RGBColor.white()));
        Sphere sphere2 = new Sphere(new Vector3D(2, 1, 10), new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), 4, new Material(RGBColor.white()));

        Light light = new PointLight(new Vector3D(-1, 7, 10), 0.5, RGBColor.red());
        Light light2 = new DirectionalLight(null, 0.5, RGBColor.white(), new Vector3D(0.5, -0.5, 0.5));
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
