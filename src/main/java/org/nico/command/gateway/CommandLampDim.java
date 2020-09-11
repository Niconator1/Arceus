package org.nico.command.gateway;

import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLampDim extends CommandLamp {

    public CommandLampDim(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, String lamp, Gateway gateway) {
        Pattern pattern = Pattern.compile("dimmen \\b\\d+\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        final int value;
        if (matcher.find()) {
            String percent = matcher.group().substring(7);
            value = (int) (Math.max(0.0, Math.min(100.0, Double.parseDouble(percent))) / 100.0 * 255.0 + 0.5);
        } else {
            value = 255;
        }
        gateway.sendMessage("lights/" + lamp + "/state", "{ \"on\": true, \"bri\": " + value + " }", RequestType.PUT);
    }

}