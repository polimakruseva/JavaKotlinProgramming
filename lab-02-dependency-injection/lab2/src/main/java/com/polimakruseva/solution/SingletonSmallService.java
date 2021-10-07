package com.polimakruseva.solution;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonSmallService {
    @Inject
    public SingletonSmallService() {}

    public String getName() {
        return mName;
    }

    private final String mName = "Hi, I am singleton";
}
