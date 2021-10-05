package com.polimakruseva.solution;

import java.util.HashMap;
import java.util.Map;

public class ObjectGraphImpl implements ObjectGraph {
    @Override
    public void addRegisteredService(String name, ServiceRegistrationImpl service) {
        if (!registeredClasses.containsKey(name)) {
            registeredClasses.put(name, service);
        }
    }

    @Override
    public Object getCreatedSingleton(String name) {
        return createdSingletons.get(name);
    }

    @Override
    public void addCreatedSingleton(String className, Object object) {
        createdSingletons.put(className, object);
    }

    @Override
    public ServiceRegistrationImpl getRegisteredService(String name) {
        return registeredClasses.get(name);
    }

    @Override
    public boolean isRegistered(Class<?> service) {
        return registeredClasses.containsKey(service.getTypeName());
    }

    @Override
    public boolean isCreated(String name) {
        return createdSingletons.containsKey(name);
    }

    private final Map<String, ServiceRegistrationImpl> registeredClasses = new HashMap<>();
    private final Map<String, Object> createdSingletons = new HashMap<>();
}
