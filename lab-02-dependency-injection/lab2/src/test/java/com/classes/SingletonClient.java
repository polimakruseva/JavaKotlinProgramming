package com.classes;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonClient {
    @Inject
    public SingletonClient(SingletonSimpleService firstService, MiddleService secondService) {
        mFirstService = firstService;
        mSecondService = secondService;
    }

    public SingletonSimpleService getSingletonService() {
        return mFirstService;
    }

    public MiddleService getMiddleService() {
        return mSecondService;
    }

    public String getName() {
        return "I am singleton client";
    }

    private final SingletonSimpleService mFirstService;
    private final MiddleService mSecondService;
}
