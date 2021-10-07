package com.polimakruseva.solution;

import java.lang.reflect.InvocationTargetException;

enum TypeOfService {
    SINGLETON,
    TRANSIENT
}

public interface DependenciesInjector {
    void register(Class<?> service) throws Exception;
    <T> void register(Class<T> implementedInterface, Class<? extends T> implementation) throws Exception;
    Object resolve(Class<?> service) throws Exception;
    void completeRegistration();
}
