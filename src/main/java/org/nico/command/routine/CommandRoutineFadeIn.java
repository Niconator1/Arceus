package org.nico.command.routine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.command.CommandGateway;
import org.nico.data.Group;
import org.nico.gateway.Gateway;
import org.nico.rest.RequestType;

import java.util.Map;

public class CommandRoutineFadeIn extends CommandGateway {
    public CommandRoutineFadeIn(String keyWord) {
        super(keyWord);
    }

    @Override
    public void handleCommand(String text, Gateway gateway) {
        new Thread(new FadeIn(gateway)).start();
    }

    private static class FadeIn implements Runnable {

        private final Gateway gateway;

        public FadeIn(Gateway gateway) {
            this.gateway = gateway;
        }

        @Override
        public void run() {
            ObjectMapper mapper = new ObjectMapper();
            for (int i = 1; i < 255; i++) {
                final int value = i;
                try {
                    Map<String, Group> mapIndexToGroup = mapper.readValue(gateway.sendMessage("groups", null, RequestType.GET), new TypeReference<Map<String, Group>>() {
                    });
                    mapIndexToGroup.keySet().forEach(id -> gateway.sendMessage("groups/" + id + "/action", "{ \"on\": true, \"bri\": " + value + " }", RequestType.PUT));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
