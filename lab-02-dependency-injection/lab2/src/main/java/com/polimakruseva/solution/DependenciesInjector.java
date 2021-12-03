package com.polimakruseva.solution;

enum TypeOfService {
    SINGLETON,
    NOTSINGLETON
}

public interface DependenciesInjector {
    void register(Class<?> service) throws Exception;
    <T> void register(Class<T> implementedInterface, Class<? extends T> implementation) throws Exception;
    Object resolve(Class<?> service) throws Exception;
    void completeRegistration();
}
