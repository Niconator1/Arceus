package org.nico;

import org.nico.command.Command;
import org.nico.command.CommandLightOff;
import org.nico.command.CommandLightOn;
import org.nico.gateway.Gateway;
import org.nico.gateway.GatewayFinder;

import java.util.ArrayList;
import java.util.List;

public class Arceus {

    private static Arceus instance;
    private GatewayFinder gatewayFinder;
    private List<Command> listCommand = new ArrayList<>();

    private Arceus() {
        registerCommands();
        registerGatewayFinder();
    }

    public static Arceus getInstance() {
        if (Arceus.instance == null) {
            Arceus.instance = new Arceus();
        }
        return Arceus.instance;
    }

    private void registerCommands() {
        listCommand.add(new CommandLightOff("Licht aus"));
        listCommand.add(new CommandLightOff("Mach bitte das Licht aus"));
        listCommand.add(new CommandLightOff("Licht ausmachen"));
        listCommand.add(new CommandLightOn("Licht an"));
        listCommand.add(new CommandLightOn("Mach bitte das Licht an"));
        listCommand.add(new CommandLightOn("Licht anmachen"));
    }

    private void registerGatewayFinder() {
        gatewayFinder = new GatewayFinder();
        new Thread(gatewayFinder).start();
    }
    
    public void handleRequest(String text) {
        listCommand.parallelStream().filter(command -> command.getKeyWord().equalsIgnoreCase(text)).findAny().ifPresent(command -> command.handleCommand(text));
    }

    public Gateway getGateway() {
        return this.gatewayFinder.getGateway();
    }
}
