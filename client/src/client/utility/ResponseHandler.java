package client.utility;

import common.Response;

public class ResponseHandler {
    private final Console console;

    public ResponseHandler(Console console) {
        this.console = console;
    }

    public void handle(Response response) {
        if (response.isSuccess()) {
            console.println(response.getMessage());
        } else {
            console.printError(response.getMessage());
        }
    }
}