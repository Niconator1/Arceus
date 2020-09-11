package org.nico;

import org.nico.command.Command;
import org.nico.command.gateway.*;
import org.nico.command.routine.CommandRoutineFadeIn;
import org.nico.command.routine.CommandRoutineFadeOut;
import org.nico.gateway.Gateway;
import org.nico.gateway.GatewayFinder;
import org.nico.speech.SpeechManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Arceus {

    private GatewayFinder gatewayFinder;
    private SpeechManager speechManager;
    private List<Command> listCommand = new ArrayList<>();

    private static Arceus instance;

    private Arceus() {
        registerCommands();
        registerGatewayFinder();
        registerSpeechManager();
    }

    public static void main(String[] args) {
        Arceus arceus = Arceus.getInstance();
        System.out.println(arceus);
    }

    private void registerSpeechManager() {
        speechManager = new SpeechManager();
        new Thread(speechManager).start();
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
        listCommand.add(new CommandLightOff("aus"));
        listCommand.add(new CommandLightOn("an( \\d* prozent)?"));
        listCommand.add(new CommandLightDim("dimmen \\d* prozent"));
        listCommand.add(new CommandLightColor("Farbe .+"));

        listCommand.add(new CommandLampOff("aus"));
        listCommand.add(new CommandLampOn("an( \\d* prozent)?"));
        listCommand.add(new CommandLampDim("dimmen( \\d* prozent)?"));
        listCommand.add(new CommandLampColor("farbe .+"));

        listCommand.add(new CommandRoutineFadeIn("routine fade in"));
        listCommand.add(new CommandRoutineFadeOut("routine fade out"));
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
