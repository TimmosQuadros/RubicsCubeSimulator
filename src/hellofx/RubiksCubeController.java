package hellofx;

public class RubiksCubeController {
    private RubiksCube cube;

    public RubiksCubeController(RubiksCube cube) {
        this.cube = cube;
    }

    public void rotateFace(String face, boolean clockwise) {
        // Stub method for rotating a face
        System.out.println("Rotate " + face + " face " + (clockwise ? "clockwise" : "counterclockwise"));
    }
}
