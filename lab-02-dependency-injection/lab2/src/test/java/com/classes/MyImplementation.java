package com.classes;

import javax.inject.Inject;

public class MyImplementation implements MyInterface{
    @Inject
    public MyImplementation() {
    }

    @Override
    public String getMyString() {
        return "Implementation created";
    }
}
