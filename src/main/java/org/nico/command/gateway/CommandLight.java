package org.nico.command.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.command.CommandGateway;
import org.nico.data.Group;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CommandLight extends CommandGateway {

    private String lightKeyword;

    public CommandLight(String keyWord) {
        super("Licht (.+ )?" + keyWord);
        this.lightKeyword = keyWord;
    }

    public abstract void handleCommand(String text, String group, Gateway gateway);

    @Override
    public void handleCommand(String text, Gateway gateway) {
        Pattern patternCommand = Pattern.compile(lightKeyword, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommand = patternCommand.matcher(text);
        if (matcherCommand.find()) {
            Pattern patternName = Pattern.compile("Licht (.+ )+" + lightKeyword, Pattern.CASE_INSENSITIVE);
            Matcher matcherName = patternName.matcher(text);
            final String groupName;
            if (matcherName.find()) {
                groupName = matcherName.group().substring(6, matcherName.group().length() - matcherCommand.group().length() - 1);
            } else {
                groupName = "";
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Group> mapIndexToGroup = mapper.readValue(gateway.sendMessage("groups", null, RequestType.GET), new TypeReference<Map<String, Group>>() {
                });
                if (groupName.isEmpty()) {
                    mapIndexToGroup.keySet().forEach(id -> handleCommand(text, id, gateway));
                } else {
                    mapIndexToGroup.entrySet().stream().filter(stringGroupEntry -> groupName.equalsIgnoreCase(stringGroupEntry.getValue().name)).forEach(stringGroupEntry -> handleCommand(text, stringGroupEntry.getKey(), gateway));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
