package org.nico;

import org.nico.command.Command;
import org.nico.command.CommandLightDim;
import org.nico.command.CommandLightOff;
import org.nico.command.CommandLightOn;
import org.nico.gateway.Gateway;
import org.nico.gateway.GatewayFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Arceus {

    private GatewayFinder gatewayFinder;
    private List<Command> listCommand = new ArrayList<>();

    private static Arceus instance;

    private Arceus() {
        registerCommands();
        registerGatewayFinder();
    }

    public static void main(String[] args) throws InterruptedException {
        Arceus arceus = Arceus.getInstance();
        Thread.sleep(1000L);
        arceus.handleRequest("Licht an");
        Thread.sleep(1000L);
        arceus.handleRequest("Licht dimmen 10%");
        Thread.sleep(1000L);
        arceus.handleRequest("Licht aus");
    }

    private void registerGatewayFinder() {
        gatewayFinder = new GatewayFinder();
        new Thread(gatewayFinder).start();
    }

    public static Arceus getInstance() {
        if (Arceus.instance == null) {
            Arceus.instance = new Arceus();
        }
        return Arceus.instance;
    }

    private void registerCommands() {
        listCommand.add(new CommandLightOff("Licht aus"));
        listCommand.add(new CommandLightOn("Licht an( \\d*%)?"));
        listCommand.add(new CommandLightDim("Licht dimmen \\d*%"));
    }

    public Gateway getGateway() {
        return this.gatewayFinder.getGateway();
    }

    public void handleRequest(String text) {
        listCommand.parallelStream().filter(command -> {
            Pattern pattern = Pattern.compile(command.getKeyWord(), Pattern.CASE_INSENSITIVE);
            return pattern.matcher(text).matches();
        }).findAny().ifPresent(command -> command.handleCommand(text));
    }
}
