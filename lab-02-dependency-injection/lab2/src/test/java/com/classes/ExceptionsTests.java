package com.classes;

import com.polimakruseva.solution.DependenciesInjectorImpl;
import org.junit.jupiter.api.Assertions;

public class ExceptionsTests {
    private final DependenciesInjectorImpl myDI = new DependenciesInjectorImpl();

    @org.junit.jupiter.api.Test
    public void SeveralInjectConstructors() {
        try {
            myDI.register(SeveralRightConstructors.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Several inject constructors in provided class " +
                    "com.classes.SeveralRightConstructors. Only one can be supported.");
        }
    }

    @org.junit.jupiter.api.Test
    public void PrivateInjectConstructor() {
        try {
            myDI.register(PrivateConstructor.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "No public inject constructor found for service " +
                    "com.classes.PrivateConstructor.");
        }
    }

    @org.junit.jupiter.api.Test
    public void NoInjectConstructor() {
        try {
            myDI.register(NoSuitableConstructor.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "No inject constructor in provided class " +
                    "com.classes.NoSuitableConstructor.");
        }
    }

    @org.junit.jupiter.api.Test
    public void SeveralImplementations() {
        try {
            myDI.register(MyInterface.class, MyImplementation.class);
            myDI.register(MyInterface.class, MySingletonImplementation.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Attempt to register another class that implements Interface " +
                    "when Interface is already registered.");
        }
    }

    @org.junit.jupiter.api.Test
    public void WrongRegistrationParameters() {
        try {
            myDI.register(MyInterface.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "You can't register interface com.classes.MyInterface " +
                    "without implementation.");
        }

        try {
            myDI.register(MyAbstractClass.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "You can't register an abstract class com.classes.MyAbstractClass.");
        }

        try {
            myDI.register(MyImplementation.class, MyChildClass.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "First argument should de an interface.");
        }

        try {
            myDI.register(MyImplementation.class, MyChildClass.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "First argument should de an interface.");
        }

        try {
            myDI.register(MyInterface.class, AlsoMyInterface.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Second class should be an implementation of first, can't be " +
                    "abstract or interface.");
        }

        try {
            myDI.register(MyInterface.class, MyAbstractClass.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Second class should be an implementation of first, can't be " +
                    "abstract or interface.");
        }
    }

    @org.junit.jupiter.api.Test
    public void CircularDependency() {
        try {
            myDI.register(ClientWithCircularDependency.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Circular dependency during registration occurred.");
        }
    }

    @org.junit.jupiter.api.Test
    public void NotRegistered() {
        try {
            myDI.resolve(SuperClient.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Required service is not registered.");
        }
    }

    @org.junit.jupiter.api.Test
    public void CompleteRegistration() {
        myDI.completeRegistration();
        try {
            myDI.register(SingletonClient.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Registration is already complete.");
        }

        try {
            myDI.register(MyInterface.class, MyImplementation.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Registration is already complete.");
        }
    }
}
