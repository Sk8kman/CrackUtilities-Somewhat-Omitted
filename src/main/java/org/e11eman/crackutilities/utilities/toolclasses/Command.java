package org.e11eman.crackutilities.utilities.toolclasses;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Command {
    public String name;
    public String description;
    public String example;

    public Command(String command, String description, String example) {
        this.name = command;
        this.description = description;
        this.example = example;
    }

    public abstract void execute(ArrayList<String> arguments) throws IOException;
}
