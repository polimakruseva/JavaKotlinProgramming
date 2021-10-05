package com.polimakruseva.solution;

import java.lang.reflect.InvocationTargetException;

enum TypeOfService {
    SINGLETON,
    TRANSIENT
}

public interface DependenciesInjector {
    void register(Class<?> service) throws ClassNotFoundException;
    <T> void register(Class<T> implementedInterface, Class<? extends T> implementation);
    Object resolve(Class<?> service) throws InvocationTargetException, InstantiationException, IllegalAccessException;
    void completeRegistration();
}
