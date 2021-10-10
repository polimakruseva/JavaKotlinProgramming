package com.classes;

import javax.inject.Inject;

public class ClientWithCircularDependency {
    @Inject
    public ClientWithCircularDependency(FirstService firstService, SecondService secondService) {
        mMyFirstService = firstService;
        mMySecondService = secondService;
    }

    private final FirstService mMyFirstService;
    private final SecondService mMySecondService;
}
