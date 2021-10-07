package com.classes;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MySingletonImplementation implements MyInterface {
    @Inject
    public MySingletonImplementation() {}

    @Override
    public String getMyString() {
        return "Singleton implementation created";
    }
}
