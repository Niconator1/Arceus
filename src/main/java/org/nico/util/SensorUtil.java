package org.nico.util;

import org.nico.data.sensor.SensorGroup;

import java.util.Arrays;
import java.util.List;

public class SensorUtil {
    public static List<SensorGroup> getSensorGroupList() {
        return Arrays.asList(new SensorGroup("Schlafzimmer", Arrays.asList("00:15:8d:00:03:c4:74:7f-01-0201")));
    }
}
