package org.nico.command;

import org.nico.Arceus;
import org.nico.gateway.Gateway;

public abstract class CommandGateway extends Command {

    public CommandGateway(String type, String params) {
        super(type, params);
    }

    public abstract void handleCommand(Gateway gateway);

    public void handleCommand() {
        Gateway gateway = Arceus.getInstance().getGateway();
        if (gateway == null) {
            System.out.println("Gateway missing");
            return;
        }
        handleCommand(gateway);
    }
}