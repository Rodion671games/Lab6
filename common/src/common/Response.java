package common;

import java.io.Serializable;
import java.util.Vector;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean success;
    private final String message;
    private final Vector<String> collection;

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.collection = null;
    }

    public Response(boolean success, String message, Vector<String> collection) {
        this.success = success;
        this.message = message;
        this.collection = collection;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Vector<String> getCollection() {
        return collection;
    }
}