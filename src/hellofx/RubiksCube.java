package hellofx;

public class RubiksCube {
    // Faces: 0=U, 1=D, 2=F, 3=B, 4=L, 5=R
    private final String[][][] cube = new String[6][3][3];

    private static final String[] COLORS = {
        "W", // U: White
        "Y", // D: Yellow
        "G", // F: Green
        "B", // B: Blue
        "O", // L: Orange
        "R"  // R: Red
    };

    public RubiksCube() {
        reset();
    }

    public void reset() {
        for (int face = 0; face < 6; face++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    cube[face][row][col] = COLORS[face];
                }
            }
        }
    }

    public String[][][] getState() {
        return cube;
    }

    public String getFacelet(int face, int row, int col) {
        return cube[face][row][col];
    }

    public void setFacelet(int face, int row, int col, String color) {
        cube[face][row][col] = color;
    }

    // Placeholder for future: rotateFace('U'), etc.
}
