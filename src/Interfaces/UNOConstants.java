package Interfaces;

import java.awt.Color;
/*
Code created by Josh Braza
*/
public class UNOConstants {
    //Colors
    public Color RED;
    public Color BLUE;
    public Color GREEN;
    public Color YELLOW;
    public UNOConstants() {

    }

    // Getters para as cores
    public Color getRED(String palette) {
        RED = switch (palette) {
            case "Acromatopsia" -> new Color(148, 148, 148);
            case "Tritanomalia" -> new Color(255, 25, 94);
            case "Deuteromalia" -> new Color(165, 90, 47);
            case "Protanomalia" -> new Color(159, 87, 64);
            default -> new Color(219, 60, 40);
        };
        return RED;
    }

    public Color getBLUE(String palette) {
        BLUE = switch (palette) {
            case "Acromatopsia" -> new Color(105, 105, 103);
            case "Tritanomalia" -> new Color(0, 101, 146);
            case "Deuteromalia" -> new Color(28, 95, 184);
            case "Protanomalia" -> new Color(26, 110, 191);
            default -> new Color(18, 117, 186);
        };
        return BLUE;
    }

    public Color getGREEN(String palette) {
        GREEN = switch (palette) {
            case "Acromatopsia" -> new Color(180, 180, 180);
            case "Tritanomalia" -> new Color(44, 204, 235);
            case "Deuteromalia" -> new Color(217, 227, 123);
            case "Protanomalia" -> new Color(151, 211, 68);
            default -> new Color(0, 153, 0);
        };
        return GREEN;
    }

    public Color getYELLOW(String palette) {
        YELLOW = switch (palette) {
            case "Acromatopsia" -> new Color(210, 189, 189);
            case "Tritanomalia" -> new Color(124, 110, 115);
            case "Deuteromalia" -> new Color(242, 222, 10);
            case "Protanomalia" -> new Color(245, 216, 12);
            default -> new Color(255, 204, 0);
        };
        return YELLOW;
    }
}