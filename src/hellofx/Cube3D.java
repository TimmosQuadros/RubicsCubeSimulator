package hellofx;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Cube3D {

    private static final int SIZE = 50;
    private static final int GAP = 2;
    private static final int OFFSET = SIZE + GAP;

    public Group createCube() {
        Group group = new Group();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    // Main cubelet
                    Box cubelet = new Box(SIZE, SIZE, SIZE);

                    // Create a group for the cubelet and its stickers
                    Group cubeletGroup = new Group();
                    cubeletGroup.getChildren().add(cubelet);

                    // Add face stickers (correct positions and rotations)
                    if (x == 1) cubeletGroup.getChildren().add(createSticker(Color.RED, Rotate.Y_AXIS, 90));      // Right
                    if (x == -1) cubeletGroup.getChildren().add(createSticker(Color.ORANGE, Rotate.Y_AXIS, -90)); // Left
                    if (y == 1) cubeletGroup.getChildren().add(createSticker(Color.WHITE, Rotate.X_AXIS, -90));   // Up
                    if (y == -1) cubeletGroup.getChildren().add(createSticker(Color.YELLOW, Rotate.X_AXIS, 90));  // Down
                    if (z == 1) cubeletGroup.getChildren().add(createSticker(Color.GREEN, null, 0));              // Front
                    if (z == -1) cubeletGroup.getChildren().add(createSticker(Color.BLUE, Rotate.Y_AXIS, 180));   // Back

                    // Position the entire cubelet group in the cube
                    cubeletGroup.setTranslateX(x * OFFSET);
                    cubeletGroup.setTranslateY(y * OFFSET);
                    cubeletGroup.setTranslateZ(z * OFFSET);

                    group.getChildren().add(cubeletGroup);
                }
            }
        }

        return group;
    }

    private Node createSticker(Color color, Point3D axis, double angle) {
        double stickerSize = SIZE * 0.9;
        double faceOffset = SIZE / 2.0 + 0.1;

        Rectangle face = new Rectangle(stickerSize, stickerSize);
        face.setFill(color);

        // Center the rectangle on its face
        face.setTranslateX(-stickerSize / 2);
        face.setTranslateY(-stickerSize / 2);
        face.setTranslateZ(faceOffset);

        Group faceGroup = new Group(face);

        if (axis != null && angle != 0) {
            faceGroup.getTransforms().add(new Rotate(angle, axis));
        }

        return faceGroup;
    }
}
