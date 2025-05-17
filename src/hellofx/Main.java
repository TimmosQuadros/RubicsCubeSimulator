package hellofx;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    	RubiksCube model = new RubiksCube();
    	Cube3D cube3D = new Cube3D();
    	Group cubeGroup = cube3D.createCube(model);

        // Add lighting
        cubeGroup.getChildren().add(new AmbientLight(Color.WHITE));

        // Rotation transforms
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        cubeGroup.getTransforms().addAll(rotateX, rotateY);

        // Camera setup
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-500);

        // 3D subscene
        SubScene subScene = new SubScene(cubeGroup, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGRAY);
        subScene.setCamera(camera);

        // Layout and scene
        StackPane root = new StackPane(subScene);
        Scene scene = new Scene(root, 800, 600);

        // Mouse drag rotation
        final double[] mouseOldX = new double[1];
        final double[] mouseOldY = new double[1];

        root.setOnMousePressed((MouseEvent event) -> {
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            double dx = event.getSceneX() - mouseOldX[0];
            double dy = event.getSceneY() - mouseOldY[0];

            rotateY.setAngle(rotateY.getAngle() + dx * 0.5);
            rotateX.setAngle(rotateX.getAngle() - dy * 0.5);

            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();
        });
        
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case R -> {
                    model.rotateFace('R'); // or scramble()
                }
                case L -> {
                    model.rotateFace('L'); // or scramble()
                }
                case U -> {
                    model.rotateFace('U'); // or scramble()
                }
                case D -> {
                    model.rotateFace('D'); // or scramble()
                }
                case F -> {
                    model.rotateFace('F'); // or scramble()
                }
                case B -> {
                    model.rotateFace('B'); // or scramble()
                }
                default -> {}
            }
            cubeGroup.getChildren().setAll(cube3D.createCube(model).getChildren()); // refresh
        });

        primaryStage.setTitle("Rubik's Cube via Cube3D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
