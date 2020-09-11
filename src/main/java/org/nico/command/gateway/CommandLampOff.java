package org.nico.command.gateway;

import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

public class CommandLampOff extends CommandLamp {

    public CommandLampOff(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, String lamp, Gateway gateway) {
        gateway.sendMessage("lights/" + lamp + "/state", "{ \"on\": false }", RequestType.PUT);
    }
}
