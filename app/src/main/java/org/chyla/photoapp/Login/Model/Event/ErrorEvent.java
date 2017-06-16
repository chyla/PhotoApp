package org.chyla.photoapp.Login.Model.Event;

public class ErrorEvent {

    public enum Type {
        PASSWORD_ERROR,
        MAIL_ERROR
    };

    private Type type;

    public ErrorEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}
