package com.classes;

import javax.inject.Inject;

public class MiddleService {
    @Inject
    public MiddleService(SingletonSimpleService service) {
        mService = service;
    }

    public MiddleService() {
        mService = null;
    }

    public String getName() {
        return "I am middle service";
    }

    public SingletonSimpleService getService() {
        return mService;
    }

    private final SingletonSimpleService mService;
}

