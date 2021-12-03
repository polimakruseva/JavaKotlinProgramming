package com.classes;

public class NoSuitableConstructor {
    public NoSuitableConstructor() {
        myString = "Hello";
    }

    public NoSuitableConstructor(String string) {
        myString = string;
    }

    private final String myString;
}
