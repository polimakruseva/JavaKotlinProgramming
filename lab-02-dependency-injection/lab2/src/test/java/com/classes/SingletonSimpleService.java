package com.classes;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonSimpleService {
    @Inject
    public SingletonSimpleService() {}

    public String getName() {
        return mName;
    }

    private final String mName = "I am singleton simple service";
}
