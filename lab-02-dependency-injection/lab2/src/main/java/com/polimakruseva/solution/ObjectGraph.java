package com.polimakruseva.solution;

public interface ObjectGraph {
    void addRegisteredService(String name, ServiceRegistrationImpl service);
    Object getCreatedSingleton(String name);
    void addCreatedSingleton(String className, Object object);
    ServiceRegistrationImpl getRegisteredService(String name);

    boolean isRegistered(Class<?> service);
    boolean isCreated(String name);
}
