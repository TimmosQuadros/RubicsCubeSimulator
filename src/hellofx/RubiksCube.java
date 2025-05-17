package hellofx;

import java.util.Arrays;
import java.util.Random;

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
    	//System.out.println("face: " + face + " row: "+row+" col: "+col);
        return cube[face][row][col];
    }

    public void setFacelet(int face, int row, int col, String color) {
        cube[face][row][col] = color;
    }

    public void rotateFace(char face) {
        String[] temp;
        switch (face) {
            case 'U':
                rotateFaceMatrix(0);
                /*temp = cube[3][0].clone(); // F top
                cube[3][0] = cube[5][0].clone(); // F <- R
                cube[5][0] = cube[2][0].clone(); // R <- B
                cube[2][0] = cube[4][0].clone(); // B <- L
                cube[4][0] = temp; // L <- old F*/
            	System.out.println("F top: " + Arrays.toString(cube[2][0]));
            	System.out.println("R top: " + Arrays.toString(cube[5][0]));
            	System.out.println("B top: " + Arrays.toString(cube[3][0]));
            	System.out.println("L top: " + Arrays.toString(cube[4][0]));
                break;

            case 'D':
                rotateFaceMatrix(1);
                temp = cube[2][2].clone(); // F bottom
                cube[2][2] = cube[4][2].clone(); // F <- L
                cube[4][2] = cube[3][2].clone(); // L <- B
                cube[3][2] = cube[5][2].clone(); // B <- R
                cube[5][2] = temp; // R <- old F
                break;

            case 'F':
                rotateFaceMatrix(2);
                temp = new String[]{cube[0][2][0], cube[0][2][1], cube[0][2][2]};
                for (int i = 0; i < 3; i++) {
                    cube[0][2][i] = cube[4][2 - i][2];
                    cube[4][2 - i][2] = cube[1][0][2 - i];
                    cube[1][0][2 - i] = cube[5][i][0];
                    cube[5][i][0] = temp[i];
                }
                break;

            case 'B':
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
            rotateFace(faces[rand.nextInt(faces.length)]);
        }
    }
}
