package org.nico.util;

import org.nico.command.Command;
import org.nico.command.CommandLamp;
import org.nico.command.CommandLight;
import org.nico.command.CommandRadiator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtil {
    public static Command getCommand(String text) {
        String commandText = getCommandText(text);
        if (commandText != null) {
            return getCommandFromText(commandText);
        } else {
            System.out.println("Keyword not found");
        }
        return null;
    }

    private static String getCommandText(String text) {
        String jarvisRegex = "(jarvis )(.*)";
        Pattern patternCommand = Pattern.compile(jarvisRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommand = patternCommand.matcher(text);
        if (matcherCommand.find()) {
            if (matcherCommand.groupCount() >= 2) {
                return matcherCommand.group(2);
            }
        }
        return null;
    }

    private static Command getCommandFromText(String commandText) {
        String commandTypeRegex = "([^\\s]+)( )?(.*)";
        Pattern patternCommandType = Pattern.compile(commandTypeRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcherCommandType = patternCommandType.matcher(commandText);
        if (matcherCommandType.find()) {
            if (matcherCommandType.groupCount() >= 3) {
                String commandType = matcherCommandType.group(1);
                String params = matcherCommandType.group(3);
                System.out.println("Detected Command Type: " + commandType + " with params " + params);
                if (commandType != null && params != null) {
                    switch (commandType.toLowerCase()) {
                        case "licht":
                            return new CommandLight(commandType, params);
                        case "lampe":
                            return new CommandLamp(commandType, params);
                        case "heizung":
                            return new CommandRadiator(commandType, params);
                        default:
                            break;
                    }
                }
            }
        }
        return null;
    }
}
