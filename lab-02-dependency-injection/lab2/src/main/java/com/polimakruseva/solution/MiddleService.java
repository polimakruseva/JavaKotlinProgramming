package com.polimakruseva.solution;

import javax.inject.Inject;

public class MiddleService {
    @Inject
    public MiddleService(SingletonSmallService service) {
        mService = service;
    }

    public String getNameOfSmallService() {
        return mService.getName();
    }

    private final SingletonSmallService mService;
}
