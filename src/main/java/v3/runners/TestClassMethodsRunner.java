package v3.runners;

import v3.Test;
import v3.notification.RunNotifier;
import v3.runner.Description;
import v3.runner.Runner;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lx on 2017/8/27.
 */
public class TestClassMethodsRunner extends Runner {
    private final List<Method> testMethods;
    private final Class<?> testClass;
    private Class<?> name;

    public TestClassMethodsRunner(Class<?> testClass) {
        this.testClass = testClass;
        this.testMethods = new TestIntrospector(testClass).getTestMethods(Test.class);
    }

    @Override
    public Description getDescription() {
        Description spec = Description.createSuiteDescription(getName());
        List<Method> testMethods = this.testMethods;
        for (Method method : testMethods) {
            spec.addChild(methodDescription(method));
        }
        return spec;
    }

    private Description methodDescription(Method method) {
        return Description.createTestDescription(getTestClass(), testName(method));
    }

    private String testName(Method method) {
        return method.getName();
    }

    @Override
    public void run(RunNotifier notifier) {
        for (Method method : testMethods) {
            invokeTestMethod(method, notifier);
        }
    }

    protected void invokeTestMethod(Method method, RunNotifier notifier) {
        Object test;
        try {
            test = createTest();
        } catch (Exception e) {
            return;
        }
        createMethodRunner(test, method, notifier).run();
    }

    private TestMethodRunner createMethodRunner(Object test, Method method, RunNotifier notifier) {
        return new TestMethodRunner(test, method, notifier, methodDescription(method));
    }

    public String getName() {
        return getTestClass().getName();
    }

    public Object createTest() throws Exception {
        return getTestClass().getConstructor().newInstance();
    }

    public Class<?> getTestClass() {
        return testClass;
    }
}
