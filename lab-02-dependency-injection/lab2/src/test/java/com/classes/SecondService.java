package com.classes;

import javax.inject.Inject;

public class SecondService {
    @Inject
    public SecondService(FirstService service) {
        mMyService = service;
    }

    private final FirstService mMyService;
}
