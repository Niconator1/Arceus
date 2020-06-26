package org.nico.command;

import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

public class CommandLightOff extends CommandLight {

    public CommandLightOff(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, String group, Gateway gateway) {
        gateway.sendMessage("groups/" + group + "/action", "{ \"on\": false }", RequestType.PUT);
    }
}
