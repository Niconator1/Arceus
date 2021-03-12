package org.nico.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nico.Arceus;
import org.nico.data.group.Group;
import org.nico.data.light.Light;
import org.nico.data.sensor.Sensor;
import org.nico.rest.RestRequest;

import java.util.HashMap;
import java.util.Map;

public class DataManager implements Runnable {

    private volatile Map<String, Group> mapIndexToGroup = new HashMap<>();
    private volatile Map<String, Light> mapIndexToLights = new HashMap<>();
    private volatile Map<String, Sensor> mapIndexToSensors = new HashMap<>();

    @Override
    public void run() {
        while (true) {
            if (Arceus.getInstance().getGateway() != null) {
                updateLists();
                long delay = 60 * 1000L;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateLists() {
        ObjectMapper mapper = new ObjectMapper();
        RestRequest restRequestGroups = new RestRequest("groups", "GET", null);
        restRequestGroups.send();
        String resultGroups = restRequestGroups.getResponse();
        if (resultGroups != null) {
            try {
                Map<String, Group> mapIndexToGroup = mapper.readValue(resultGroups, new TypeReference<>() {
                });
                if (mapIndexToGroup != null && !mapIndexToGroup.isEmpty()) {
                    this.mapIndexToGroup = mapIndexToGroup;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        RestRequest restRequestLights = new RestRequest("lights", "GET", null);
        restRequestLights.send();
        String resultLights = restRequestLights.getResponse();
        if (resultLights != null) {
            try {
                Map<String, Light> mapIndexToLights = mapper.readValue(resultLights, new TypeReference<>() {
                });
                if (mapIndexToLights != null && !mapIndexToLights.isEmpty()) {
                    this.mapIndexToLights = mapIndexToLights;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        RestRequest restRequestSensors = new RestRequest("sensors", "GET", null);
        restRequestSensors.send();
        String resultSensors = restRequestSensors.getResponse();
        if (resultSensors != null) {
            try {
                Map<String, Sensor> mapIndexToSensors = mapper.readValue(resultSensors, new TypeReference<>() {
                });
                if (mapIndexToSensors != null && !mapIndexToSensors.isEmpty()) {
                    this.mapIndexToSensors = mapIndexToSensors;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Virtual environment updated");
    }

    public Map<String, Group> getGroupMap() {
        return this.mapIndexToGroup;
    }

    public Map<String, Light> getLightMap() {
        return this.mapIndexToLights;
    }

    public Map<String, Sensor> getSensorMap() {
        return this.mapIndexToSensors;
    }
}
