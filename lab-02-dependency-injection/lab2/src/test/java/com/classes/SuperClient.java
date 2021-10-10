package com.classes;

import javax.inject.Inject;

public class SuperClient {
    @Inject
    public SuperClient(SingletonSimpleService firstService, MiddleService secondService, SimpleService thirdService,
                       SingletonClient fourthService) {
        mFirstService = firstService;
        mSecondService = secondService;
        mThirdService = thirdService;
        mFourthService = fourthService;
    }

    public String getName() {
        return mFirstService.getName() + " + " + mSecondService.getName() + " + " + mThirdService.getName() + " + " +
                mFourthService.getName();
    }

    public SingletonSimpleService getSingletonService() {
        return mFirstService;
    }

    public MiddleService getMiddleService() {
        return mSecondService;
    }

    public SimpleService getService() {
        return mThirdService;
    }

    public SingletonClient getSingletonClient() {
        return mFourthService;
    }

    private final SingletonSimpleService mFirstService;
    private final MiddleService mSecondService;
    private final SimpleService mThirdService;
    private final SingletonClient mFourthService;
}
