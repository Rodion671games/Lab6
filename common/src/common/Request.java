package common;

import common.models.HumanBeing;
import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String commandName;
    private final String[] arguments;
    private final HumanBeing element;

    public Request(String commandName, String[] arguments, HumanBeing element) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.element = element;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public HumanBeing getElement() {
        return element;
    }
}