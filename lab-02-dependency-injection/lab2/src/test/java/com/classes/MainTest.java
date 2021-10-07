package com.classes;

import com.polimakruseva.solution.DependenciesInjectorImpl;

public class MainTest {
    public static void main(String[] args) throws Exception {
        DependenciesInjectorImpl myDi = new DependenciesInjectorImpl();
        myDi.register(MyInterface.class, MyImplementation.class);
        MyImplementation impl = (MyImplementation) myDi.resolve(MyInterface.class);
        System.out.println(impl.getMyString());
        myDi.register(MyInterface.class, MySingletonImplementation.class);
    }
}
