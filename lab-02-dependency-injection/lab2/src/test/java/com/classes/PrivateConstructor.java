package com.classes;

import javax.inject.Inject;

public class PrivateConstructor {
    public PrivateConstructor() {
        myString = "Bye";
    }

    @Inject
    private PrivateConstructor(String string) {
        myString = string;
    }

    private final String myString;
}
