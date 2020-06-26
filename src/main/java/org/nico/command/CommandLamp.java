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

public abstract class CommandLamp extends CommandGateway {

    private String lampKeyword;

    public CommandLamp(String keyWord) {
        super("Lampe (.+ )+" + keyWord);
        this.lampKeyword = keyWord;
    }

    public abstract void handleCommand(String text, String lamp, Gateway gateway);

    @Override
    public void handleCommand(String text, Gateway gateway) {
        Pattern patternCommand = Pattern.compile(lampKeyword, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommand = patternCommand.matcher(text);
        if (matcherCommand.find()) {
            Pattern patternName = Pattern.compile("Lampe (.+ )+" + lampKeyword, Pattern.CASE_INSENSITIVE);
            Matcher matcherName = patternName.matcher(text);
            final String groupName;
            if (matcherName.find()) {
                groupName = matcherName.group().substring(6, matcherName.group().length() - matcherCommand.group().length() - 1);
            } else {
                groupName = "";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Light> mapIndexToLight = mapper.readValue(gateway.sendMessage("lights", null, RequestType.GET), new TypeReference<Map<String, Light>>() {
                });
                if (groupName.isEmpty()) {
                    mapIndexToLight.keySet().forEach(index -> handleCommand(text, index, gateway));
                } else {
                    mapIndexToLight.entrySet().stream().filter(stringLightEntry -> groupName.equalsIgnoreCase(stringLightEntry.getValue().name)).forEach(stringLightEntry -> handleCommand(text, stringLightEntry.getKey(), gateway));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
