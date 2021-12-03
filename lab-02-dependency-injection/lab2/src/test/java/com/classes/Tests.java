package com.classes;

import com.polimakruseva.solution.DependenciesInjectorImpl;
import org.junit.jupiter.api.Assertions;

public class Tests {
    private final DependenciesInjectorImpl myDI = new DependenciesInjectorImpl();

    @org.junit.jupiter.api.Test
    public void SimpleTest() throws Exception {
        myDI.register(SimpleService.class);
        myDI.register(MyInterface.class, MyImplementation.class);

        myDI.completeRegistration();

        SimpleService simpleService = (SimpleService) myDI.resolve(SimpleService.class);
        Assertions.assertEquals(simpleService.getName(), "I am simple service");
        SimpleService anotherSimpleService = (SimpleService) myDI.resolve(SimpleService.class);

        Assertions.assertNotEquals(simpleService, anotherSimpleService);

        MyImplementation impl = (MyImplementation) myDI.resolve(MyInterface.class);
        Assertions.assertEquals(impl.getMyString(), "Implementation created");
        MyImplementation anotherImpl = (MyImplementation) myDI.resolve(MyInterface.class);

        Assertions.assertNotEquals(impl, anotherImpl);
    }

    @org.junit.jupiter.api.Test
    public void SingletonSimpleTest() throws Exception {
        myDI.register(SingletonSimpleService.class);
        myDI.register(MyInterface.class, MySingletonImplementation.class);

        myDI.completeRegistration();

        SingletonSimpleService singletonService = (SingletonSimpleService) myDI.resolve(SingletonSimpleService.class);
        Assertions.assertEquals(singletonService.getName(), "I am singleton simple service");
        SingletonSimpleService anotherSingletonService = (SingletonSimpleService) myDI.resolve(SingletonSimpleService.class);

        Assertions.assertEquals(singletonService, anotherSingletonService);

        MySingletonImplementation impl = (MySingletonImplementation) myDI.resolve(MyInterface.class);
        Assertions.assertEquals(impl.getMyString(), "Singleton implementation created");
        MySingletonImplementation anotherImpl = (MySingletonImplementation) myDI.resolve(MyInterface.class);

        Assertions.assertEquals(impl, anotherImpl);
    }

    @org.junit.jupiter.api.Test
    public void MiddleTest() throws Exception {
        myDI.register(MiddleService.class);
        myDI.completeRegistration();

        MiddleService middle = (MiddleService) myDI.resolve(MiddleService.class);
        Assertions.assertEquals(middle.getName(), "I am middle service");
        Assertions.assertNotNull(middle.getService());
        MiddleService anotherMiddle = (MiddleService) myDI.resolve(MiddleService.class);
        Assertions.assertNotNull(anotherMiddle.getService());

        Assertions.assertNotEquals(middle, anotherMiddle);
        Assertions.assertEquals(middle.getService(), anotherMiddle.getService());
    }

    @org.junit.jupiter.api.Test
    public void HardTest() throws Exception {
        myDI.register(SingletonClient.class);

        SingletonClient singletonClient = (SingletonClient) myDI.resolve(SingletonClient.class);
        Assertions.assertEquals(singletonClient.getName(), "I am singleton client");

        myDI.register(SuperClient.class);
        myDI.completeRegistration();

        SuperClient client = (SuperClient) myDI.resolve(SuperClient.class);
        Assertions.assertEquals(client.getName(), "I am singleton simple service + I am middle service + I am simple " +
                "service + I am singleton client");

        Assertions.assertEquals(singletonClient, client.getSingletonClient());
        Assertions.assertEquals(singletonClient.getSingletonService(), client.getSingletonService());
        Assertions.assertNotEquals(singletonClient.getMiddleService(), client.getMiddleService());

        SuperClient anotherClient = (SuperClient) myDI.resolve(SuperClient.class);
        Assertions.assertNotEquals(anotherClient, client);
        Assertions.assertEquals(client.getSingletonService(), anotherClient.getSingletonService());
        Assertions.assertEquals(client.getSingletonClient(), anotherClient.getSingletonClient());
        Assertions.assertNotEquals(client.getMiddleService(), anotherClient.getMiddleService());
        Assertions.assertNotEquals(client.getService(), anotherClient.getService());
    }
}
