package gui;

import engine.models.SceneData;
import engine.models.components.Camera;
import engine.models.components.geometry.Geometry;
import engine.models.components.geometry.Sphere;
import engine.models.components.lights.DirectionalLight;
import engine.models.components.lights.Light;
import engine.models.components.lights.PointLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ViewPort {
    private PerspectiveCamera viewPortCamera;


    public Scene getViewPortScene(SceneData sceneData) {
        Parent content = createContent(sceneData);
        Scene scene = new Scene(content);
        scene.setCamera(viewPortCamera);
        return scene;
    }

    /**
     * remember to invert y axis when adding objects, as the y axis of the viewport points downwards
     */

    private Parent createContent(SceneData sceneData) {
        Group interactiveView = new Group();

        viewPortCamera = new PerspectiveCamera(true);


        Camera renderCamera = sceneData.camera;

        viewPortCamera.getTransforms().addAll(
                new Rotate(renderCamera.rotation.y, Rotate.Y_AXIS),
                new Rotate(renderCamera.rotation.x, Rotate.X_AXIS),
                new Rotate(renderCamera.rotation.z, Rotate.Z_AXIS),
                new Translate(renderCamera.position.x, -renderCamera.position.y, renderCamera.position.z)
        );

        viewPortCamera.setFieldOfView(renderCamera.fov);
        interactiveView.getChildren().add(viewPortCamera);

        for (Geometry geometry : sceneData.geometries) {
            if (geometry instanceof Sphere) {
                Sphere renderSphere = (Sphere) geometry;
                javafx.scene.shape.Sphere viewPortSphere = new javafx.scene.shape.Sphere(renderSphere.radius);
                viewPortSphere.getTransforms().add(new Translate(renderSphere.position.x, -renderSphere.position.y, renderSphere.position.z));
                interactiveView.getChildren().add(viewPortSphere);
            }
        }

        for (Light light : sceneData.lights) {
            if (light instanceof PointLight) {
                PointLight renderLight = (PointLight) light;
                javafx.scene.PointLight viewPortLight = new javafx.scene.PointLight(light.toJavaFXLightColor());
                viewPortLight.getTransforms().add(new Translate(renderLight.position.x, -renderLight.position.y, renderLight.position.z));
                interactiveView.getChildren().add(viewPortLight);
            }

            // simulate directional light with point light very far away
            if (light instanceof DirectionalLight) {
                DirectionalLight renderLight = (DirectionalLight) light;
                javafx.scene.PointLight viewPortLight = new javafx.scene.PointLight(light.toJavaFXLightColor());
                viewPortLight.getTransforms().add(new Translate(-renderLight.direction.x * 10000, renderLight.direction.y * 10000, -renderLight.direction.z * 10000));
                interactiveView.getChildren().add(viewPortLight);
            }
        }

        return interactiveView;
    }

}
