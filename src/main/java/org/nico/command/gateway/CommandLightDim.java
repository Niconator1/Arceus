package org.nico.command.gateway;

import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLightDim extends CommandLight {

    public CommandLightDim(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, String group, Gateway gateway) {
        Pattern pattern = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        final int value;
        if (matcher.find()) {
            String percent = matcher.group();
            value = (int) (Math.max(0.0, Math.min(100.0, Double.parseDouble(percent))) / 100.0 * 255.0 + 0.5);
        } else {
            value = 255;
        }
        gateway.sendMessage("groups/" + group + "/action", "{ \"bri\": " + value + " }", RequestType.PUT);
    }
}
