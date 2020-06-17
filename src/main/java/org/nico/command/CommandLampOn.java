package org.nico.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.data.Light;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLampOn extends CommandGateway {

    public CommandLampOn(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, Gateway gateway) {
        ObjectMapper mapper = new ObjectMapper();
        Pattern pattern = Pattern.compile("an \\b\\d+\\b");
        Matcher matcher = pattern.matcher(text);
        final int value;
        if (matcher.find()) {
            String percent = matcher.group().substring(3);
            value = (int) (Math.max(0.0, Math.min(100.0, Double.parseDouble(percent))) / 100.0 * 254.0 + 0.5);
        } else {
            value = 254;
        }
        Pattern patternName = Pattern.compile("Lampe (.+) an");
        Matcher matcherName = patternName.matcher(text);
        final String name;
        if (matcherName.find()) {
            name = matcherName.group().substring(6, matcherName.group().length() - 3);
        } else {
            name = "";
        }
        try {
            Map<String, Light> mapIndexToLight = mapper.readValue(gateway.sendMessage("lights", null, RequestType.GET), new TypeReference<Map<String, Light>>() {
            });
            mapIndexToLight.entrySet().stream().filter(stringLightEntry -> name.equalsIgnoreCase(stringLightEntry.getValue().name)).findAny().ifPresent(stringLightEntry -> {
                gateway.sendMessage("lights/" + stringLightEntry.getKey() + "/state", "{ \"on\": true, \"bri\": " + value + " }", RequestType.PUT);
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
