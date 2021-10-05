package com.polimakruseva.solution;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonClient {
    @Inject
    public SingletonClient(SingletonSmallService firstService, MiddleService secondService) {
        mFirstService = firstService;
        mSecondService = secondService;
    }

    public String getNameThroughMiddle() {
        return mSecondService.getNameOfSmallService() + " from middle";
    }

    public String getName() {
        return mFirstService.getName();
    }

    private final SingletonSmallService mFirstService;
    private final MiddleService mSecondService;
}
