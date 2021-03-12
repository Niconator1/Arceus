package org.nico;

import org.nico.command.Command;
import org.nico.data.DataManager;
import org.nico.gateway.Gateway;
import org.nico.gateway.GatewayManager;
import org.nico.util.CommandUtil;
import org.nico.voice.VoiceManager;

import java.util.Optional;
import java.util.Scanner;

public class Arceus {

    private static Arceus arceus;
    private final GatewayManager gatewayManager;
    private final DataManager dataManager;
    private final VoiceManager voiceManager;

    private Arceus() {
        voiceManager = new VoiceManager();
        new Thread(voiceManager).start();
        gatewayManager = new GatewayManager();
        new Thread(gatewayManager).start();
        dataManager = new DataManager();
        new Thread(dataManager).start();
    }

    public static Arceus getInstance() {
        if (arceus == null) {
            arceus = new Arceus();
        }
        return arceus;
    }

    public static void main(String[] args) {
        Arceus arceus = Arceus.getInstance();
        Scanner in = new Scanner(System.in);
        while (true) {
            String text = in.nextLine();
            arceus.handleCommand(text);
        }
    }

    public Gateway getGateway() {
        return gatewayManager.getGateway();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void handleCommand(String text) {
        Command command = CommandUtil.getCommand(text);
        Optional<Command> optionalCommand = Optional.ofNullable(command);
        optionalCommand.ifPresent(Command::handleCommand);
    }

}