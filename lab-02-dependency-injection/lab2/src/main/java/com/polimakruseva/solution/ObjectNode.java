package com.polimakruseva.solution;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public interface ObjectNode {
    String getName();
    ArrayList<ObjectNode> getParameters();
    Constructor<?> getConstructor();
}
