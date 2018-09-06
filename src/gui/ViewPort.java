package gui;

import engine.models.SceneData;
import engine.models.components.geometry.Geometry;
import engine.models.components.geometry.Sphere;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ViewPort {
    private PerspectiveCamera camera;
    private Scene scene;


    public Scene getViewPortScene(SceneData sceneData) {
        Parent content = createContent(sceneData);
        scene = new Scene(content);
        scene.setCamera(camera);
        return scene;
    }

    private Parent createContent(SceneData sceneData) {
        Group interactiveView = new Group();

        camera = new PerspectiveCamera(true);

        camera.getTransforms().addAll(
                new Rotate(sceneData.camera.rotation.y, Rotate.Y_AXIS),
                new Rotate(sceneData.camera.rotation.x, Rotate.X_AXIS),
                new Rotate(sceneData.camera.rotation.z, Rotate.Z_AXIS)
        );
        camera.setFieldOfView(sceneData.camera.fov);
        interactiveView.getChildren().add(camera);

        for (Geometry geometry : sceneData.geometries) {
            if (geometry instanceof Sphere) {
                Sphere renderSphere = (Sphere) geometry;
                javafx.scene.shape.Sphere viewPortSphere = new javafx.scene.shape.Sphere(renderSphere.radius);
                viewPortSphere.getTransforms().add(new Translate(renderSphere.position.x, renderSphere.position.y, renderSphere.position.z));
                interactiveView.getChildren().add(viewPortSphere);
            }
        }

        return interactiveView;
    }

}
