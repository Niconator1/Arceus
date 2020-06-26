package org.nico.command;

import org.nico.data.HSVColor;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLightColor extends CommandLight {

    public CommandLightColor(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, String group, Gateway gateway) {
        Pattern pattern = Pattern.compile("Farbe .+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        final int hue;
        final int sat;
        if (matcher.find()) {
            String colorString = matcher.group().substring(6);
            HSVColor hsvColor = HSVColor.getMapNameToHSVColor().get(colorString);
            if (Objects.nonNull(hsvColor)) {
                hue = hsvColor.getHue();
                sat = hsvColor.getSat();
                gateway.sendMessage("groups/" + group + "/action", "{ \"on\": true, \"hue\": " + hue + " , \"sat\": " + sat + " }", RequestType.PUT);
            }
        }
    }
}
