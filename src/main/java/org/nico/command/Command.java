package org.nico.command;

public abstract class Command {
    protected final String type;
    protected final String params;

    public Command(String type, String params) {
        this.type = type;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public String getParams() {
        return params;
    }

    public abstract void handleCommand();
}
