package org.nico.util;

import org.nico.color.HSVColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParamUtil {

    private static String generateColorRegex() {
        StringBuilder resultBuilder = new StringBuilder();
        HSVColor.getMapNameToHSVColor().keySet().forEach(colorName -> resultBuilder.append(colorName).append("|"));
        resultBuilder.deleteCharAt(resultBuilder.length() - 1);
        return resultBuilder.toString();
    }

    public static Map<String, Object> mapLightParams(String params) {
        Map<String, Object> mapResult = new HashMap<>();
        // Light on with no arguments
        if (params.isEmpty()) {
            mapResult.put("light.group", Constants.GROUP_DEFAULT);
            mapResult.put("light.type", "dim");
            mapResult.put("light.value", 1.0);
            return mapResult;
        }
        // Light off command
        String commandTypeOffRegex = "(((.*)( ))?\\b(aus)\\b)";
        Pattern patternCommandTypeOff = Pattern.compile(commandTypeOffRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOff = patternCommandTypeOff.matcher(params);
        if (matcherCommandTypeOff.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOff.group(3));
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "off");
            return mapResult;
        }
        String colorRegex = generateColorRegex();
        // Light color with "Farbe" command
        String commandTypeColorFarbeRegex = "(((.*)( ))?(?=Farbe))(Farbe )(" + colorRegex + ")";
        Pattern patternCommandTypeColorFarbe = Pattern.compile(commandTypeColorFarbeRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeColorFarbe = patternCommandTypeColorFarbe.matcher(params);
        if (matcherCommandTypeColorFarbe.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeColorFarbe.group(3));
            String textColor = matcherCommandTypeColorFarbe.group(6);
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "color");
            mapResult.put("light.color", textColor);
            return mapResult;
        }
        // Light color without "Farbe" command
        String commandTypeColorRegex = "(((.*)( ))?(?!Farbe))(" + colorRegex + ")";
        Pattern patternCommandTypeColor = Pattern.compile(commandTypeColorRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeColor = patternCommandTypeColor.matcher(params);
        if (matcherCommandTypeColor.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeColor.group(3));
            String textColor = matcherCommandTypeColor.group(5);
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "color");
            mapResult.put("light.color", textColor);
            return mapResult;
        }
        // Light dim with "dimmen" command
        String commandTypeDimDimmenRegex = "(((.*)( ))?(?=dimmen|an))(dimmen |an )(\\d+)( )(%)";
        Pattern patternCommandTypeDimDimmen = Pattern.compile(commandTypeDimDimmenRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeDimDimmen = patternCommandTypeDimDimmen.matcher(params);
        if (matcherCommandTypeDimDimmen.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeDimDimmen.group(3));
            String textValue = matcherCommandTypeDimDimmen.group(6);
            double value = Double.parseDouble(textValue) / 100.0;
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "dim");
            mapResult.put("light.value", value);
            return mapResult;
        }
        // Light dim without "dimmen" command
        String commandTypeDimRegex = "(((.*)( ))?(?!dimmen|an))(\\d+)( )(%)";
        Pattern patternCommandTypeDim = Pattern.compile(commandTypeDimRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeDim = patternCommandTypeDim.matcher(params);
        if (matcherCommandTypeDim.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeDim.group(3));
            String textValue = matcherCommandTypeDim.group(5);
            double value = Double.parseDouble(textValue) / 100.0;
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "dim");
            mapResult.put("light.value", value);
            return mapResult;
        }
        // Light on with "an" command
        String commandTypeOnAnRegex = "(((.*)( ))?(?=an))\\b(an)\\b";
        Pattern patternCommandTypeOnAn = Pattern.compile(commandTypeOnAnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOnAn = patternCommandTypeOnAn.matcher(params);
        if (matcherCommandTypeOnAn.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOnAn.group(3));
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "dim");
            mapResult.put("light.value", 1.0);
            return mapResult;
        }
        // Light on without "an" command
        String commandTypeOnRegex = "((.*)(?!an))";
        Pattern patternCommandTypeOn = Pattern.compile(commandTypeOnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOn = patternCommandTypeOn.matcher(params);
        if (matcherCommandTypeOn.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOn.group(2));
            mapResult.put("light.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("light.type", "dim");
            mapResult.put("light.value", 1.0);
            return mapResult;
        }
        return mapResult;
    }

    public static Map<String, Object> mapLampParams(String params) {
        Map<String, Object> mapResult = new HashMap<>();
        // Lamp off command
        String commandTypeOffRegex = "(((.*)( ))\\b(aus)\\b)";
        Pattern patternCommandTypeOff = Pattern.compile(commandTypeOffRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOff = patternCommandTypeOff.matcher(params);
        if (matcherCommandTypeOff.find()) {
            String textName = matcherCommandTypeOff.group(3);
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "off");
            return mapResult;
        }
        String colorRegex = generateColorRegex();
        // Lamp color with "Farbe" command
        String commandTypeColorFarbeRegex = "(((.*)( ))(?=Farbe))(Farbe )(" + colorRegex + ")";
        Pattern patternCommandTypeColorFarbe = Pattern.compile(commandTypeColorFarbeRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeColorFarbe = patternCommandTypeColorFarbe.matcher(params);
        if (matcherCommandTypeColorFarbe.find()) {
            String textName = matcherCommandTypeColorFarbe.group(3);
            String textColor = matcherCommandTypeColorFarbe.group(6);
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "color");
            mapResult.put("lamp.color", textColor);
            return mapResult;
        }
        // Lamp color without "Farbe" command
        String commandTypeColorRegex = "(((.*)( ))(?!Farbe))(" + colorRegex + ")";
        Pattern patternCommandTypeColor = Pattern.compile(commandTypeColorRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeColor = patternCommandTypeColor.matcher(params);
        if (matcherCommandTypeColor.find()) {
            String textName = matcherCommandTypeColor.group(3);
            String textColor = matcherCommandTypeColor.group(5);
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "color");
            mapResult.put("lamp.color", textColor);
            return mapResult;
        }
        // Lamp dim with "dimmen" command
        String commandTypeDimDimmenRegex = "(((.*)( ))(?=dimmen|an))(dimmen |an )(\\d+)( )(%)";
        Pattern patternCommandTypeDimDimmen = Pattern.compile(commandTypeDimDimmenRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeDimDimmen = patternCommandTypeDimDimmen.matcher(params);
        if (matcherCommandTypeDimDimmen.find()) {
            String textName = matcherCommandTypeDimDimmen.group(3);
            String textValue = matcherCommandTypeDimDimmen.group(6);
            double value = Double.parseDouble(textValue) / 100.0;
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "dim");
            mapResult.put("lamp.value", value);
            return mapResult;
        }
        // Lamp dim without "dimmen" command
        String commandTypeDimRegex = "(((.*)( ))(?!dimmen|an))(dimmen |an )?(\\d+)( )(%)";
        Pattern patternCommandTypeDim = Pattern.compile(commandTypeDimRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeDim = patternCommandTypeDim.matcher(params);
        if (matcherCommandTypeDim.find()) {
            String textName = matcherCommandTypeDim.group(3);
            String textValue = matcherCommandTypeDim.group(6);
            double value = Double.parseDouble(textValue) / 100.0;
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "dim");
            mapResult.put("lamp.value", value);
            return mapResult;
        }
        // Lamp on with "an" command
        String commandTypeOnAnRegex = "(((.*)( ))(?=an))\\b(an)\\b";
        Pattern patternCommandTypeOnAn = Pattern.compile(commandTypeOnAnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOnAn = patternCommandTypeOnAn.matcher(params);
        if (matcherCommandTypeOnAn.find()) {
            String textName = matcherCommandTypeOnAn.group(3);
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "dim");
            mapResult.put("lamp.value", 1.0);
            return mapResult;
        }
        // Lamp on without "an" command
        String commandTypeOnRegex = "((.*)(?!an))";
        Pattern patternCommandTypeOn = Pattern.compile(commandTypeOnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOn = patternCommandTypeOn.matcher(params);
        if (matcherCommandTypeOn.find()) {
            String textName = matcherCommandTypeOn.group(2);
            mapResult.put("lamp.name", textName);
            mapResult.put("lamp.type", "dim");
            mapResult.put("lamp.value", 1.0);
            return mapResult;
        }
        return mapResult;
    }

    public static Map<String, Object> mapRadiatorParams(String params) {
        Map<String, Object> mapResult = new HashMap<>();
        // Radiator off command
        String commandTypeOffRegex = "(((.*)( ))?\\b(aus)\\b)";
        Pattern patternCommandTypeOff = Pattern.compile(commandTypeOffRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOff = patternCommandTypeOff.matcher(params);
        if (matcherCommandTypeOff.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOff.group(3));
            mapResult.put("radiator.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("radiator.type", "temperature");
            mapResult.put("radiator.value", Constants.RADIATOR_TEMPERATURE_OFF);
            return mapResult;
        }
        // Radiator on with "an" command
        String commandTypeOnTemperatureAnRegex = "(((.*)( ))?(?=an))(an )(\\d+)( )(°)";
        Pattern patternCommandTypeOnTemperatureAn = Pattern.compile(commandTypeOnTemperatureAnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOnTemperatureAn = patternCommandTypeOnTemperatureAn.matcher(params);
        if (matcherCommandTypeOnTemperatureAn.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOnTemperatureAn.group(3));
            String textValue = matcherCommandTypeOnTemperatureAn.group(6);
            mapResult.put("radiator.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("radiator.type", "temperature");
            mapResult.put("radiator.value", textValue);
            return mapResult;
        }
        // Radiator on without "an" command
        String commandTypeOnTemperatureRegex = "(((.*)( ))?(?!an))(\\d+)( )(°)";
        Pattern patternCommandTypeOnTemperature = Pattern.compile(commandTypeOnTemperatureRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOnTemperature = patternCommandTypeOnTemperature.matcher(params);
        if (matcherCommandTypeOnTemperature.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOnTemperature.group(3));
            String textValue = matcherCommandTypeOnTemperature.group(5);
            mapResult.put("radiator.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("radiator.type", "temperature");
            mapResult.put("radiator.value", textValue);
            return mapResult;
        }
        // Radiator on command without temperature
        String commandTypeOnRegex = "(((.*)( ))?(?=an))\\b(an)\\b";
        Pattern patternCommandTypeOn = Pattern.compile(commandTypeOnRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandTypeOn = patternCommandTypeOn.matcher(params);
        if (matcherCommandTypeOn.find()) {
            Optional<String> textGroup = Optional.ofNullable(matcherCommandTypeOn.group(3));
            mapResult.put("radiator.group", textGroup.orElse(Constants.GROUP_DEFAULT));
            mapResult.put("radiator.type", "temperature");
            mapResult.put("radiator.value", Constants.RADIATOR_TEMPERATURE_DEFAULT);
            return mapResult;
        }
        return mapResult;
    }
}
