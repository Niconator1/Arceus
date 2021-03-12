package org.nico.data.sensor;

import java.util.List;

public class SensorGroup {
    private final String name;
    private final List<String> sensorList;

    public SensorGroup(String name, List<String> sensorList) {
        this.name = name;
        this.sensorList = sensorList;
    }

    public List<String> getSensorList() {
        return sensorList;
    }

    public String getName() {
        return name;
    }
}
