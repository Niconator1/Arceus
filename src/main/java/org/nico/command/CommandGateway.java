package org.nico.command;

import org.nico.Arceus;
import org.nico.gateway.Gateway;

import java.util.Objects;

public abstract class CommandGateway extends Command {

    public CommandGateway(String keyWord) {
        super(keyWord);
    }

    public abstract void handleCommand(String text, Gateway gateway);

    public void handleCommand(String text) {
        Gateway gateway = Arceus.getInstance().getGateway();
        if (Objects.nonNull(gateway)) {
            handleCommand(text, gateway);
        } else {
            System.out.println("Gateway unreachable");
        }
    }
}
