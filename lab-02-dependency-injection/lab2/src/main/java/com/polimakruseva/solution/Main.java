package com.polimakruseva.solution;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        DependenciesInjectorImpl myDI = new DependenciesInjectorImpl();
        myDI.register(SingletonClient.class);
        myDI.register(MiddleService.class);
        myDI.register(SingletonSmallService.class);
        myDI.completeRegistration();

        SingletonSmallService ser;
        Constructor[] constructors = SingletonSmallService.class.getConstructors();
        for (Constructor constr : constructors) {
            if (Modifier.isPublic(constr.getModifiers()) && constr.getParameterCount() == 0) {
                ser = (SingletonSmallService) constr.newInstance();
                System.out.println(ser.getName());
            }
        }
        SingletonSmallService a = (SingletonSmallService) myDI.resolve(SingletonSmallService.class);
        System.out.println(a.getName());
        SingletonSmallService b = (SingletonSmallService) myDI.resolve(SingletonSmallService.class);
        if (a == b) {
            System.out.println("great");
        }

        SingletonClient client = (SingletonClient) myDI.resolve(SingletonClient.class);

        System.out.println(client.getName());
        System.out.println(client.getNameThroughMiddle());

    }
}
