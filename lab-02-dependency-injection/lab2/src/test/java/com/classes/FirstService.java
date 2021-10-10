package com.classes;

import javax.inject.Inject;

public class FirstService {
    @Inject
    public FirstService(SecondService service) {
        mMyService = service;
    }

    private final SecondService mMyService;
}
