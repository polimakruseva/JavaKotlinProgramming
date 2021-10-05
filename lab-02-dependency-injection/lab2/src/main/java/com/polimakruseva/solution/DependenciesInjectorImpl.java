package com.polimakruseva.solution;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DependenciesInjectorImpl implements DependenciesInjector {
    @Override
    public void register(Class<?> service) {
        if (isRegistrationComplete) {
            // throw exception
        }

        // exceptions for abstract classes and interfaces
        ServiceRegistrationImpl registeredService;
        if (service.isAnnotationPresent(Singleton.class)) {
            registeredService = new ServiceRegistrationImpl(service, TypeOfService.SINGLETON);
        } else {
            registeredService = new ServiceRegistrationImpl(service, TypeOfService.TRANSIENT);
        }
        objectGraph.addRegisteredService(service.getTypeName(), registeredService);
    }

    @Override
    public <T> void register(Class<T> baseClass, Class<? extends T> subClass) {
        if (!Modifier.isInterface(baseClass.getModifiers())) {
            // exception
        }
        ServiceRegistrationImpl registeredService;
        if (subClass.isAnnotationPresent(Singleton.class)) {
            registeredService = new ServiceRegistrationImpl(subClass, TypeOfService.SINGLETON);
        } else {
            registeredService = new ServiceRegistrationImpl(subClass, TypeOfService.TRANSIENT);
        }
        objectGraph.addRegisteredService(baseClass.getTypeName(), registeredService);
    }

    @Override
    public Object resolve(Class<?> service) throws InvocationTargetException, InstantiationException, IllegalAccessException {
       if (!objectGraph.isRegistered(service)) {
           // exception

       }
       return getClass(service, new HashSet<>());
    }

    @Override
    public void completeRegistration() {
        isRegistrationComplete = true;
    }

    private Object getClass(Class<?> service, HashSet<String> dependencies) throws
            InvocationTargetException, InstantiationException, IllegalAccessException {
        String requestedClassType = service.getTypeName();
        ServiceRegistrationImpl registeredService = objectGraph.getRegisteredService(requestedClassType);

        if (registeredService.getTypeOfService() == TypeOfService.SINGLETON &&
                objectGraph.isCreated(requestedClassType)) {
            return objectGraph.getCreatedSingleton(requestedClassType);
        }

        if (dependencies.contains(requestedClassType)) {
            // exception
        }

        dependencies.add(requestedClassType);

        Constructor<?>[] constructors = registeredService.getClassType().getConstructors();

        Object result = null;
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.isAnnotationPresent(Inject.class)) {
                List<Object> constructorParameters = new ArrayList<>();
                for (Parameter parameter : constructor.getParameters()) {
                    Object createdParameter = getClass(parameter.getType(), dependencies);
                    constructorParameters.add(createdParameter);
                }
                result = constructor.newInstance(constructorParameters.toArray());

                if (registeredService.getTypeOfService() == TypeOfService.SINGLETON) {
                    objectGraph.addCreatedSingleton(requestedClassType, result);
                }

                return result;
            } else if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterCount() == 0) {
                result = constructor.newInstance();
                if (registeredService.getTypeOfService() == TypeOfService.SINGLETON) {
                    objectGraph.addCreatedSingleton(requestedClassType, result);
                }
                return result;
            }
        }
        // exceptions
        return result;
    }

    private final ObjectGraphImpl objectGraph = new ObjectGraphImpl();
    private boolean isRegistrationComplete = false;
}
