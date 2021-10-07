package com.polimakruseva.solution;

import java.util.*;

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
    public void addObjectNode(ObjectNode node) {
        objectGraph.add(node);
    }

    @Override
    public ObjectNode getObjectNode(String requiredNode) {
        return findObjectNode(requiredNode, objectGraph);
    }

    @Override
    public boolean isRegistered(String name) {
        return registeredClasses.containsKey(name);
    }

    @Override
    public boolean isCreated(String name) {
        return createdSingletons.containsKey(name);
    }

    private ObjectNode findObjectNode(String requiredNode, ArrayList<ObjectNode> parameters) {
        for (ObjectNode parameter : parameters) {
            if (parameter.getName().equals(requiredNode)) {
                return parameter;
            } else {
                ObjectNode result = findObjectNode(requiredNode, parameter.getParameters());
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private final Map<String, ServiceRegistrationImpl> registeredClasses = new HashMap<>();
    private final Map<String, Object> createdSingletons = new HashMap<>();
    private final ArrayList<ObjectNode> objectGraph = new ArrayList<>();
}
