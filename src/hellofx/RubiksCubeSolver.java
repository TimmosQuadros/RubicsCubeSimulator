package hellofx;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class RubiksCubeSolver {

    private final RubiksCube cube;
    private final List<String> moves = new ArrayList<>();
    private final Cube3D cube3D;
    private final javafx.scene.Group cubeGroup;

    public RubiksCubeSolver(RubiksCube cube, Cube3D cube3D, javafx.scene.Group cubeGroup) {
        this.cube = cube;
        this.cube3D = cube3D;
        this.cubeGroup = cubeGroup;
    }

    public void solveYellowCrossAsync() {
        new Thread(() -> {
            int attempts = 0;
            while (!isYellowCrossFormed() && attempts < 4) {
                applyYellowCrossAlgorithm();
                //rotate("F", true); // change orientation to try other L shapes or lines
                attempts++;
                //updateView();
                try {
                    Thread.sleep(600);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private boolean isYellowCrossFormed() {
        return "Y".equals(cube.getFacelet(0, 0, 1)) &&
               "Y".equals(cube.getFacelet(0, 1, 0)) &&
               "Y".equals(cube.getFacelet(0, 1, 2)) &&
               "Y".equals(cube.getFacelet(0, 2, 1));
    }

    private void applyYellowCrossAlgorithm() {
        rotate("F", true);
        rotate("R", true);
        rotate("U", true);
        rotate("R", false);
        rotate("U", false);
        rotate("F", false);
        updateView();
        sleep(600);
    }

    private void rotate(String face, boolean clockwise) {
        moves.add(clockwise ? face : face + "'");
        cube.rotateFace(face.charAt(0), clockwise);
    }

    private void updateView() {
        Platform.runLater(() -> {
            cubeGroup.getChildren().setAll(cube3D.createCube(cube).getChildren());
        });
    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public List<String> getMoves() {
        return moves;
    }
}
