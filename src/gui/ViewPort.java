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
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ViewPort {
    private PerspectiveCamera viewPortCamera;
    private static final double AXIS_LENGTH = 10;
    private static final double AXIS_STRENGTH = 0.1;

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

        viewPortCamera = getViewPortCamera(sceneData);

        interactiveView.getChildren().add(viewPortCamera);

        addGeometry(sceneData, interactiveView);
        addLights(sceneData, interactiveView);

        addAxes(interactiveView);
        return interactiveView;
    }


    private PerspectiveCamera getViewPortCamera(SceneData sceneData) {
        PerspectiveCamera viewPortCamera = new PerspectiveCamera(true);
        Camera renderCamera = sceneData.camera;
        viewPortCamera.getTransforms().addAll(
                new Rotate(renderCamera.rotation.y, Rotate.Y_AXIS),
                new Rotate(renderCamera.rotation.x, Rotate.X_AXIS),
                new Rotate(renderCamera.rotation.z, Rotate.Z_AXIS),
                new Translate(renderCamera.position.x, renderCamera.position.y, renderCamera.position.z)
        );

        viewPortCamera.setFieldOfView(renderCamera.fov);
        return viewPortCamera;
    }


    private void addGeometry(SceneData sceneData, Group interactiveView) {
        for (Geometry geometry : sceneData.geometries) {
            if (geometry instanceof Sphere) {
                Sphere renderSphere = (Sphere) geometry;
                javafx.scene.shape.Sphere viewPortSphere = new javafx.scene.shape.Sphere(renderSphere.radius);
                viewPortSphere.getTransforms().add(new Translate(renderSphere.position.x, renderSphere.position.y, renderSphere.position.z));
                interactiveView.getChildren().add(viewPortSphere);
            }
        }
    }


    private void addLights(SceneData sceneData, Group interactiveView) {
        for (Light light : sceneData.lights) {
            if (light instanceof PointLight) {
                PointLight renderLight = (PointLight) light;
                javafx.scene.PointLight viewPortLight = new javafx.scene.PointLight(light.toJavaFXLightColor());
                viewPortLight.getTransforms().add(new Translate(renderLight.position.x, renderLight.position.y, renderLight.position.z));
                interactiveView.getChildren().add(viewPortLight);
            }

            // simulate directional light with point light very far away
            if (light instanceof DirectionalLight) {
                DirectionalLight renderLight = (DirectionalLight) light;
                javafx.scene.PointLight viewPortLight = new javafx.scene.PointLight(light.toJavaFXLightColor());
                viewPortLight.getTransforms().add(new Translate(-renderLight.direction.x * 10000, -renderLight.direction.y * 10000, -renderLight.direction.z * 10000));
                interactiveView.getChildren().add(viewPortLight);
            }
        }
    }

    private void addAxes(Group interactiveView) {
        Group axesGroup = new Group();
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, AXIS_STRENGTH, AXIS_STRENGTH);
        xAxis.getTransforms().add(new Translate(AXIS_LENGTH / 2, 0, 0));
        final Box yAxis = new Box(AXIS_STRENGTH, AXIS_LENGTH, AXIS_STRENGTH);
        yAxis.getTransforms().add(new Translate(0, AXIS_LENGTH / 2, 0));
        final Box zAxis = new Box(AXIS_STRENGTH, AXIS_STRENGTH, AXIS_LENGTH);
        zAxis.getTransforms().add(new Translate(0, 0, AXIS_LENGTH / 2));

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axesGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axesGroup.setVisible(true);
        interactiveView.getChildren().addAll(axesGroup);
    }

}
