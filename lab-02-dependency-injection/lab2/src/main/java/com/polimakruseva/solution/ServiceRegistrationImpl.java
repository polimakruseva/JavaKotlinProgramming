package com.polimakruseva.solution;

public class ServiceRegistrationImpl implements ServiceRegistration {
    public ServiceRegistrationImpl(Class<?> classType, TypeOfService typeOfService) {
        this.mClassType = classType;
        this.mTypeOfService = typeOfService;
    }

    @Override
    public Class<?> getClassType() {
        return mClassType;
    }

    @Override
    public TypeOfService getTypeOfService() {
        return mTypeOfService;
    }

    final private Class<?> mClassType;
    final private TypeOfService mTypeOfService;
}
