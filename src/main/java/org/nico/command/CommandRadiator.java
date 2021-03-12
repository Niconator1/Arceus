package org.nico.command;

import org.nico.Arceus;
import org.nico.data.sensor.Sensor;
import org.nico.data.sensor.SensorGroup;
import org.nico.gateway.Gateway;
import org.nico.util.CommandParamUtil;
import org.nico.util.Constants;
import org.nico.util.SensorUtil;

import java.util.Map;

public class CommandRadiator extends CommandGateway {

    public CommandRadiator(String type, String params) {
        super(type, params);
    }

    @Override
    public void handleCommand(Gateway gateway) {
        Map<String, Object> mapParamToValue = CommandParamUtil.mapRadiatorParams(params);
        String radiatorGroup = "" + mapParamToValue.getOrDefault("radiator.group", Constants.GROUP_DEFAULT);
        SensorGroup sensorGroup = SensorUtil.getSensorGroupList().stream().filter(sensorGroupTemp -> radiatorGroup.equals(sensorGroupTemp.getName())).findFirst().orElse(null);
        if (sensorGroup != null) {
            String radiatorType = "" + mapParamToValue.getOrDefault("radiator.type", "");
            switch (radiatorType) {
                case "temperature" -> {
                    String radiatorValue = "" + mapParamToValue.getOrDefault("radiator.value", Constants.RADIATOR_TEMPERATURE_DEFAULT);
                    Integer radiatorValueNumber = (int) (Double.parseDouble(radiatorValue) * 100.0);
                    sensorGroup.getSensorList().forEach(sensor -> {
                        Map.Entry<String, Sensor> stringSensorEntry = Arceus.getInstance().getDataManager().getSensorMap().entrySet().stream().filter(sensorEntryTemp -> sensor.equals(sensorEntryTemp.getValue().uniqueid)).findFirst().orElse(null);
                        if (stringSensorEntry != null) {
                            String index = stringSensorEntry.getKey();
                            stringSensorEntry.getValue().config.setModifiedHeatSetPoint(radiatorValueNumber);
                            stringSensorEntry.getValue().synchronize(index);
                        }
                    });
                }
            }
        }
        System.out.println(mapParamToValue);
    }

}
