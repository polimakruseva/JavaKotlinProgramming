package com.classes;

import javax.inject.Inject;

public class SeveralRightConstructors {
    @Inject
    public SeveralRightConstructors() {
        myString = "Hello";
    }

    @Inject
    public SeveralRightConstructors(String string) {
        myString = string;
    }

    @Inject
    public SeveralRightConstructors(String firstStr, String secondStr) {
        if (firstStr.length() > secondStr.length()) {
            myString = firstStr;
        } else {
            myString = "Bye";
        }
    }

    private final String myString;
}
