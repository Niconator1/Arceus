package org.nico.command;

public abstract class Command {

    private String keyWord;

    public Command(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public abstract void handleCommand(String text);
}
