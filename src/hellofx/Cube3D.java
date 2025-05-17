package hellofx;

import hellofx.utils.ColorUtils;
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

    public Group createCube(RubiksCube model) {
        Group group = new Group();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {

                    if (x == 0 && y == 0 && z == 0) continue;

                    Box cubelet = new Box(SIZE, SIZE, SIZE);
                    Group cubeletGroup = new Group(cubelet);

                    // Safely mapped indices
                    int iX = toIndex(x);
                    int iY = toIndex(y);
                    int iZ = toIndex(z);
                    int iXInv = toIndex(-x);
                    int iYInv = toIndex(-y);
                    int iZInv = toIndex(-z);

                    // Add stickers based on logical position
                    if (x == 1) cubeletGroup.getChildren().add(createSticker(
                        ColorUtils.getJavaFXColor(model.getFacelet(5, iY, 2 - iZ)), Rotate.Y_AXIS, 90)); // R
                    if (x == -1) cubeletGroup.getChildren().add(createSticker(
                        ColorUtils.getJavaFXColor(model.getFacelet(4, iY, iZ)), Rotate.Y_AXIS, -90)); // L
                    if (y == 1) cubeletGroup.getChildren().add(createSticker(
                        ColorUtils.getJavaFXColor(model.getFacelet(0, 2 - iZ, iX)), Rotate.X_AXIS, -90)); // U
                    if (y == -1) cubeletGroup.getChildren().add(createSticker(
                        ColorUtils.getJavaFXColor(model.getFacelet(1, iZ, iX)), Rotate.X_AXIS, 90)); // D
                    if (z == 1) cubeletGroup.getChildren().add(createSticker(
                        ColorUtils.getJavaFXColor(model.getFacelet(2, 2 - iY, iX)), null, 0)); // F
                    if (z == -1) cubeletGroup.getChildren().add(createSticker(
                        ColorUtils.getJavaFXColor(model.getFacelet(3, 2 - iY, 2 - iX)), Rotate.Y_AXIS, 180)); // B

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
        face.setTranslateX(-stickerSize / 2);
        face.setTranslateY(-stickerSize / 2);
        face.setTranslateZ(faceOffset);

        Group faceGroup = new Group(face);

        if (axis != null && angle != 0) {
            faceGroup.getTransforms().add(new Rotate(angle, axis));
        }

        return faceGroup;
    }

    private int toIndex(int n) {
        return Math.max(0, Math.min(2, n + 1)); // maps -1→0, 0→1, 1→2
    }
} 
