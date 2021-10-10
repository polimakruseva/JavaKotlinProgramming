package com.polimakruseva.solution;

import java.util.*;

public class ObjectGraphImpl implements ObjectGraph {
    @Override
    public void addRegisteredService(String name, TypeOfService typeOfService) {
        mRegisteredClasses.put(name, typeOfService);
    }

    @Override
    public Object getCreatedSingleton(String name) {
        return mCreatedSingletons.get(name);
    }

    @Override
    public void addCreatedSingleton(String className, Object object) {
        mCreatedSingletons.put(className, object);
    }

    @Override
    public TypeOfService getTypeOfService(String name) {
        return mRegisteredClasses.get(name);
    }

    @Override
    public void addObjectNode(ObjectNode node) {
        mObjectGraph.add(node);
    }

    @Override
    public ObjectNode getObjectNode(String requiredNode) {
        return findObjectNode(requiredNode, mObjectGraph);
    }

    @Override
    public boolean isRegistered(String name) {
        return mRegisteredClasses.containsKey(name);
    }

    @Override
    public boolean isCreated(String name) {
        return mCreatedSingletons.containsKey(name);
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

    private final Map<String, TypeOfService> mRegisteredClasses = new HashMap<>();
    private final Map<String, Object> mCreatedSingletons = new HashMap<>();
    private final ArrayList<ObjectNode> mObjectGraph = new ArrayList<>();
}
