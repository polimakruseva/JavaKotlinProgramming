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
    public void register(Class<?> service) throws Exception {
        if (mIsRegistrationComplete) {
            throw new RuntimeException("Registration is already complete.");
        }
        if (Modifier.isInterface(service.getModifiers())) {
            throw new Exception("You can't register interface " + service.getTypeName() + " without implementation.");
        }
        if (Modifier.isAbstract(service.getModifiers())) {
            throw new Exception("You can't register an abstract class " + service.getTypeName() + ".");
        }
        ObjectNode node = getObjectNodeForRegistration(service, service.getTypeName(), new HashSet<>(),
                service.getTypeName());
        mObjectGraph.addObjectNode(node);
    }

    @Override
    public <T> void register(Class<T> baseClass, Class<? extends T> subClass) throws Exception {
        if (mIsRegistrationComplete) {
            throw new RuntimeException("Registration is already complete.");
        }
        if (!Modifier.isInterface(baseClass.getModifiers())) {
            throw new Exception("First argument should de an interface.");
        }
        if (Modifier.isInterface(subClass.getModifiers()) || Modifier.isAbstract(subClass.getModifiers())) {
            throw new Exception("Second class should be an implementation of first, can't be abstract or interface.");
        }
        if (mObjectGraph.isRegistered(baseClass.getTypeName())) {
            throw new Exception("Attempt to register another class that implements Interface when Interface is already " +
                    "registered.");
        }
        ObjectNode node = getObjectNodeForRegistration(subClass, baseClass.getTypeName(), new HashSet<>(),
                baseClass.getTypeName());
        mObjectGraph.addObjectNode(node);
    }

    @Override
    public Object resolve(Class<?> service) throws Exception {
        if (!mObjectGraph.isRegistered(service.getTypeName())) {
            throw new Exception("Required service is not registered.");
        }
        ObjectNode node = mObjectGraph.getObjectNode(service.getTypeName());
        return getObject(node);
    }

    @Override
    public void completeRegistration() {
        mIsRegistrationComplete = true;
    }

    private Object getObject(ObjectNode node) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        String requestedService = node.getName();
        TypeOfService typeOfService = mObjectGraph.getTypeOfService(requestedService);
        if (typeOfService == TypeOfService.SINGLETON && mObjectGraph.isCreated(requestedService)) {
            return mObjectGraph.getCreatedSingleton(requestedService);
        }
        Constructor<?> constructor = node.getConstructor();
        if (node.getParameters().size() == 0) {
            Object result = constructor.newInstance();
            if (typeOfService == TypeOfService.SINGLETON) {
                mObjectGraph.addCreatedSingleton(requestedService, result);
            }
            return result;
        }
        List<Object> constructorParameters = new ArrayList<>();
        for (ObjectNode parameter : node.getParameters()) {
            constructorParameters.add(getObject(parameter));
        }
        Object result = constructor.newInstance(constructorParameters.toArray());
        if (typeOfService == TypeOfService.SINGLETON) {
            mObjectGraph.addCreatedSingleton(requestedService, result);
        }
        return result;
    }

    private void checkForInjectConstructors(Class<?> service) throws Exception {
        Constructor<?>[] constructors = service.getDeclaredConstructors();
        int numberOfInjectConstructors = 0;
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                ++numberOfInjectConstructors;
            }
        }
        if (numberOfInjectConstructors == 0) {
            throw new Exception("No inject constructor in provided class " + service.getTypeName() + ".");
        }
        if (numberOfInjectConstructors > 1) {
            throw new Exception("Several inject constructors in provided class " + service.getTypeName() + ". Only " +
                    "one can be supported.");
        }
    }

    private void registerDataAboutService(String name, Class<?> service) {
        if (mObjectGraph.isRegistered(service.getTypeName())) {
            return;
        }
        if (service.isAnnotationPresent(Singleton.class)) {
            mObjectGraph.addRegisteredService(name, TypeOfService.SINGLETON);
        } else {
            mObjectGraph.addRegisteredService(name, TypeOfService.NOTSINGLETON);
        }
    }

    private Constructor<?> getSuitableConstructor(Class<?> service) throws Exception {
        Constructor<?>[] constructors = service.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.isAnnotationPresent(Inject.class)) {
                return constructor;
            }
        }
        throw new Exception("No public inject constructor found for service " + service.getTypeName() + ".");
    }

    private ObjectNode getObjectNodeForRegistration(Class<?> service, String name, HashSet<String> dependencies, String parentNodeName)
            throws Exception {
        if (dependencies.contains(name)) {
            throw new Exception("Circular dependency during registration occurred.");
        }
        checkForInjectConstructors(service);
        Constructor<?> constructor = getSuitableConstructor(service);
        registerDataAboutService(name, service);
        ArrayList<ObjectNode> parametersOfNode = new ArrayList<>();
        for (Parameter parameter : constructor.getParameters()) {
            if (name.equals(parentNodeName)) {
                dependencies.clear();
            }
            dependencies.add(name);
            parametersOfNode.add(getObjectNodeForRegistration(parameter.getType(), parameter.getType().getTypeName(),
                    dependencies, parentNodeName));
        }
        return new ObjectNodeImpl(name, parametersOfNode, constructor);
    }


    private final ObjectGraphImpl mObjectGraph = new ObjectGraphImpl();
    private boolean mIsRegistrationComplete = false;
}
