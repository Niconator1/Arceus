package org.nico.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.data.Group;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Map;

public class CommandLightOn extends CommandGateway {

    public CommandLightOn(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, Gateway gateway) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Group> mapIndexToGroup = mapper.readValue(gateway.sendMessage("groups", null, RequestType.GET), new TypeReference<Map<String, Group>>() {
            });
            mapIndexToGroup.keySet().forEach(id -> gateway.sendMessage("groups/" + id + "/action", "{ \"on\": true, \"bri\": 254 }", RequestType.PUT));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
