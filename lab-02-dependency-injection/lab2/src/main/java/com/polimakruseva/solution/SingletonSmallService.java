package com.polimakruseva.solution;

import javax.inject.Singleton;

@Singleton
public class SingletonSmallService {
    public String getName() {
        return mName;
    }

    private final String mName = "Hi, I am singleton";
}
