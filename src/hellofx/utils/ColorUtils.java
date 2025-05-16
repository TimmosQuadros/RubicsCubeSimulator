package hellofx.utils;

import javafx.scene.paint.Color;

public class ColorUtils {
    public static Color getJavaFXColor(String code) {
        return switch (code) {
            case "W" -> Color.WHITE;
            case "Y" -> Color.YELLOW;
            case "R" -> Color.RED;
            case "O" -> Color.ORANGE;
            case "G" -> Color.GREEN;
            case "B" -> Color.BLUE;
            default -> Color.GRAY;
        };
    }
}
