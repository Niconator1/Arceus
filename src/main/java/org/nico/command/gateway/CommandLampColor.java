package org.nico.command.gateway;

import org.nico.data.HSVColor;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLampColor extends CommandLamp {

    public CommandLampColor(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, String lamp, Gateway gateway) {
        Pattern pattern = Pattern.compile("farbe .+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        final int hue;
        final int sat;
        if (matcher.find()) {
            String colorString = matcher.group().substring(6);
            HSVColor hsvColor = HSVColor.getMapNameToHSVColor().get(colorString);
            if (Objects.nonNull(hsvColor)) {
                hue = hsvColor.getHue();
                sat = hsvColor.getSat();
                gateway.sendMessage("lights/" + lamp + "/state", "{ \"on\": true, \"hue\": " + hue + " , \"sat\": " + sat + " }", RequestType.PUT);
            }
        }
    }
}
