package org.nico.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.data.Group;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLightOn extends CommandGateway {

    public CommandLightOn(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, Gateway gateway) {
        ObjectMapper mapper = new ObjectMapper();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        final int value;
        if (matcher.find()) {
            String percent = matcher.group();
            value = (int) (Math.max(0.0, Math.min(100.0, Double.parseDouble(percent))) / 100.0 * 254.0 + 0.5);
        } else {
            value = 254;
        }
        try {
            Map<String, Group> mapIndexToGroup = mapper.readValue(gateway.sendMessage("groups", null, RequestType.GET), new TypeReference<Map<String, Group>>() {
            });
            mapIndexToGroup.keySet().forEach(id -> gateway.sendMessage("groups/" + id + "/action", "{ \"on\": true, \"bri\": " + value + " }", RequestType.PUT));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
