package org.nico.data;

import java.util.HashMap;
import java.util.Map;

public class HSVColor {

    private static final Map<String, HSVColor> mapNameToHSVColor;

    static {
        mapNameToHSVColor = new HashMap<>();
        mapNameToHSVColor.put("zurücksetzen", new HSVColor(8256, 140, 255));
        mapNameToHSVColor.put("rot", new HSVColor(0, 255, 255));
        mapNameToHSVColor.put("grün", new HSVColor(21845, 255, 255));
        mapNameToHSVColor.put("blau", new HSVColor(43690, 255, 255));
        mapNameToHSVColor.put("orange", new HSVColor(5461, 255, 255));
        mapNameToHSVColor.put("gelb", new HSVColor(10922, 255, 255));
    }

    private final int hue;
    private final int sat;
    private final int value;

    public HSVColor(int hue, int sat, int value) {
        this.hue = hue;
        this.sat = sat;
        this.value = value;
    }

    public static Map<String, HSVColor> getMapNameToHSVColor() {
        return mapNameToHSVColor;
    }

    public int getHue() {
        return hue;
    }

    public int getSat() {
        return sat;
    }

    public int getValue() {
        return value;
    }

}