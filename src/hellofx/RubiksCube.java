package hellofx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RubiksCube {
    // Faces: 0=U, 1=D, 2=F, 3=B, 4=L, 5=R
    private final String[][][] cube = new String[6][3][3];
    private final List<String> moveHistory = new ArrayList<>();

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
    	//System.out.println("face: " + face + " row: "+row+" col: "+col);
        return cube[face][row][col];
    }

    public void setFacelet(int face, int row, int col, String color) {
        cube[face][row][col] = color;
    }
    

public void rotateFace(char face, boolean clockwise) {
    if (clockwise) {
        rotateFaceOnce(face);
    } else {
        // Rotate 3 times to simulate counterclockwise
        for (int i = 0; i < 3; i++) {
            rotateFaceOnce(face);
        }
    }
    
    moveHistory.add("" + face + (clockwise ? "" : "'"));
}

    public void rotateFaceOnce(char face) {
        String[] temp;
        switch (face) {
            case 'D':
                rotateFaceMatrix(0);
                temp = cube[2][0].clone(); // F top
                cube[2][0] = cube[5][0].clone(); // F <- R
                cube[5][0] = cube[3][0].clone(); // R <- B
                cube[3][0] = cube[4][0].clone(); // B <- L
                cube[4][0] = temp; // L <- old F
                break;

            case 'U':
                rotateFaceMatrix(1);
                temp = cube[2][2].clone(); // F bottom
                cube[2][2] = cube[4][2].clone(); // F <- L
                cube[4][2] = cube[3][2].clone(); // L <- B
                cube[3][2] = cube[5][2].clone(); // B <- R
                cube[5][2] = temp; // R <- old F
                break;

            case 'B':
                rotateFaceMatrix(2);
                temp = new String[]{cube[0][2][0], cube[0][2][1], cube[0][2][2]};
                for (int i = 0; i < 3; i++) {
                    cube[0][2][i] = cube[4][2 - i][2];
                    cube[4][2 - i][2] = cube[1][0][2 - i];
                    cube[1][0][2 - i] = cube[5][i][0];
                    cube[5][i][0] = temp[i];
                }
                break;

            case 'F':
                rotateFaceMatrix(3);
                temp = new String[]{cube[0][0][0], cube[0][0][1], cube[0][0][2]};
                for (int i = 0; i < 3; i++) {
                    cube[0][0][i] = cube[5][i][2];
                    cube[5][i][2] = cube[1][2][2 - i];
                    cube[1][2][2 - i] = cube[4][2 - i][0];
                    cube[4][2 - i][0] = temp[i];
                }
                break;

            case 'L':
                rotateFaceMatrix(4);
                temp = new String[]{cube[0][0][0], cube[0][1][0], cube[0][2][0]};
                for (int i = 0; i < 3; i++) {
                    cube[0][i][0] = cube[3][2 - i][2];
                    cube[3][2 - i][2] = cube[1][i][0];
                    cube[1][i][0] = cube[2][i][0];
                    cube[2][i][0] = temp[i];
                }
                break;

            case 'R':
                rotateFaceMatrix(5);
                temp = new String[]{cube[0][0][2], cube[0][1][2], cube[0][2][2]};
                for (int i = 0; i < 3; i++) {
                    cube[0][i][2] = cube[2][i][2];
                    cube[2][i][2] = cube[1][i][2];
                    cube[1][i][2] = cube[3][2 - i][0];
                    cube[3][2 - i][0] = temp[i];
                }
                break;
        }
    }

    private void rotateFaceMatrix(int face) {
        String[][] temp = new String[3][3];
        // Copy face
        for (int i = 0; i < 3; i++) {
            System.arraycopy(cube[face][i], 0, temp[i], 0, 3);
        }
        // Rotate clockwise
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cube[face][j][2 - i] = temp[i][j];
            }
        }
    }

    public void scramble(int moves) {
        Random rand = new Random();
        char[] faces = {'U', 'D', 'F', 'B', 'L', 'R'};
        for (int i = 0; i < moves; i++) {
            rotateFace(faces[rand.nextInt(faces.length)], false);
        }
    }
    
    public void applyMoves(String moves) {
        String[] tokens = moves.trim().split("\\s+");
        for (String move : tokens) {
            boolean clockwise = !move.endsWith("'");
            char face = move.charAt(0);
            rotateFace(face, clockwise);
        }
    }
    
    public void findWhiteEdges() {
        System.out.println("Locating all white edge pieces:");
        for (int face = 0; face < 6; face++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (isEdgePosition(row, col) && getFacelet(face, row, col).equals("W")) {
                        System.out.printf("White edge on face %d at position [%d][%d]%n", face, row, col);
                    }
                }
            }
        }
    }
    
    private boolean isEdgePosition(int row, int col) {
        return (row == 1 && col != 1) || (col == 1 && row != 1);
    }

    public List<String> getMoveHistory() {
        return new ArrayList<>(moveHistory);
    }

    public void clearHistory() {
        moveHistory.clear();
    }
}
