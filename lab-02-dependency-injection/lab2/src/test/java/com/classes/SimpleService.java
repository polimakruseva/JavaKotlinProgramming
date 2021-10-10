package com.classes;

import javax.inject.Inject;

public class SimpleService {
    @Inject
    public SimpleService() {}

    public String getName() {
        return mName;
    }

    private final String mName = "I am simple service";
}
