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

public class CommandLampOff extends CommandGateway {

    public CommandLampOff(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, Gateway gateway) {
        ObjectMapper mapper = new ObjectMapper();
        Pattern patternName = Pattern.compile("Lampe (.+) aus", Pattern.CASE_INSENSITIVE);
        Matcher matcherName = patternName.matcher(text);
        final String name;
        if (matcherName.find()) {
            name = matcherName.group().substring(6, matcherName.group().length() - 4);
        } else {
            name = "";
        }
        try {
            Map<String, Light> mapIndexToLight = mapper.readValue(gateway.sendMessage("lights", null, RequestType.GET), new TypeReference<Map<String, Light>>() {
            });
            mapIndexToLight.entrySet().stream().filter(stringLightEntry -> name.equalsIgnoreCase(stringLightEntry.getValue().name)).findAny().ifPresent(stringLightEntry -> {
                gateway.sendMessage("lights/" + stringLightEntry.getKey() + "/state", "{ \"on\": false }", RequestType.PUT);
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
