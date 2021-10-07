package com.polimakruseva.solution;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ObjectNodeImpl implements ObjectNode {
    public ObjectNodeImpl(String name, ArrayList<ObjectNode> parameters, Constructor<?> constructor) {
        mName = name;
        mParameters = parameters;
        mConstructor = constructor;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public ArrayList<ObjectNode> getParameters() {
        return mParameters;
    }

    @Override
    public Constructor<?> getConstructor() {
        return mConstructor;
    }

    private final String mName;
    private final ArrayList<ObjectNode> mParameters;
    private final Constructor<?> mConstructor;
}
