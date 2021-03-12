package org.nico.command;

import org.nico.Arceus;
import org.nico.color.HSVColor;
import org.nico.data.light.Light;
import org.nico.gateway.Gateway;
import org.nico.util.CommandParamUtil;

import java.util.Map;
import java.util.Map.Entry;

public class CommandLamp extends CommandGateway {

    public CommandLamp(String type, String params) {
        super(type, params);
    }

    @Override
    public void handleCommand(Gateway gateway) {
        Map<String, Object> mapParamToValue = CommandParamUtil.mapLampParams(params);
        if (mapParamToValue.get("lamp.name") != null) {
            String lampName = "" + mapParamToValue.get("lamp.name");
            Entry<String, Light> lightEntry = Arceus.getInstance().getDataManager().getLightMap().entrySet().stream().filter(lightEntryTemp -> lampName.equals(lightEntryTemp.getValue().name)).findFirst().orElse(null);
            if (lightEntry != null) {
                Light light = lightEntry.getValue();
                String lightType = "" + mapParamToValue.getOrDefault("lamp.type", "");
                switch (lightType) {
                    case "dim" -> {
                        Double lightValue = (Double) mapParamToValue.getOrDefault("lamp.value", 1.0);
                        light.state.setModifiedOn(true);
                        light.state.setModifiedBri((int) (lightValue * 255.0));
                    }
                    case "off" -> light.state.setModifiedOn(false);
                    case "color" -> {
                        String lightColor = "" + mapParamToValue.get("lamp.color");
                        HSVColor color = HSVColor.getMapNameToHSVColor().getOrDefault(lightColor, HSVColor.colorDefault);
                        light.state.setModifiedSat(color.getSat());
                        light.state.setModifiedHue(color.getHue());
                    }
                }
                light.synchronize(lightEntry.getKey());
            }
        }
        System.out.println(mapParamToValue);
    }
}
