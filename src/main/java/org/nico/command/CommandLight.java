package org.nico.command;

import org.nico.Arceus;
import org.nico.color.HSVColor;
import org.nico.data.group.Group;
import org.nico.gateway.Gateway;
import org.nico.util.CommandParamUtil;
import org.nico.util.Constants;

import java.util.Map;

public class CommandLight extends CommandGateway {

    public CommandLight(String type, String params) {
        super(type, params);
    }

    @Override
    public void handleCommand(Gateway gateway) {
        Map<String, Object> mapParamToValue = CommandParamUtil.mapLightParams(params);
        String lightGroup = "" + mapParamToValue.getOrDefault("light.group", Constants.GROUP_DEFAULT);
        Group group = Arceus.getInstance().getDataManager().getGroupMap().values().stream().filter(groupTemp -> lightGroup.equals(groupTemp.name)).findFirst().orElse(null);
        if (group != null) {
            String lightType = "" + mapParamToValue.getOrDefault("light.type", "");
            switch (lightType) {
                case "dim" -> {
                    Double lightValue = (Double) mapParamToValue.getOrDefault("light.value", 1.0);
                    group.action.setModifiedOn(true);
                    group.action.setModifiedBri((int) (lightValue * 255.0));
                }
                case "off" -> group.action.setModifiedOn(false);
                case "color" -> {
                    String lightColor = "" + mapParamToValue.get("light.color");
                    HSVColor color = HSVColor.getMapNameToHSVColor().getOrDefault(lightColor, HSVColor.colorDefault);
                    group.action.setModifiedSat(color.getSat());
                    group.action.setModifiedHue(color.getHue());
                }
            }
            group.synchronize();
        }
        System.out.println(mapParamToValue);
    }

}
