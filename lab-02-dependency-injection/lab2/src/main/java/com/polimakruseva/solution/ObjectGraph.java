package com.polimakruseva.solution;

public interface ObjectGraph {
    void addRegisteredService(String name, TypeOfService typeOfService);
    Object getCreatedSingleton(String name);
    void addCreatedSingleton(String className, Object object);
    TypeOfService getTypeOfService(String name);
    void addObjectNode(ObjectNode node);
    ObjectNode getObjectNode(String requiredNode);

    boolean isRegistered(String name);
    boolean isCreated(String name);
}
