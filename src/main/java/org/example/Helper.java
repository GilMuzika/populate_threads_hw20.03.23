package org.example;

public class Helper {
    private String _identifier;
    private int _value;

    Helper(String identifier, int value) {
        _identifier = identifier;
        _value = value;
    }

    @Override
    public String toString() {
        return String.format("{ %s } => { %s }", _identifier, _value);
    }
}
