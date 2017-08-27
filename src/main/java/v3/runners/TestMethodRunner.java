package v3.runners;

import v3.After;
import v3.Before;
import v3.notification.Failure;
import v3.notification.RunNotifier;
import v3.runner.Description;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lx on 2017/8/27.
 */
public class TestMethodRunner extends BeforeAndAfterRunner {
    private final Object test;
    private final Method method;
    private final RunNotifier notifier;
    private TestIntrospector testIntrospector;
    private final Description description;

    public TestMethodRunner(Object test, Method method, RunNotifier notifier, Description description) {
        super(test.getClass(), Before.class, After.class, test);
        this.test= test;
        this.method= method;
        this.notifier= notifier;
        testIntrospector= new TestIntrospector(test.getClass());
        this.description= description;
    }

    public void run() {
        notifier.fireTestStarted(description);
        try {
            runMethod();
        } finally {
            notifier.fireTestFinished(description);
        }
    }

    private void runMethod() {
        runProtected();
    }

    @Override
    protected void runUnprotected() {
        try {
            excuteMethodBody();
        } catch (InvocationTargetException e) {
            addFailure(e);
        } catch (Throwable e) {
            addFailure(e);
        }
    }

    private void excuteMethodBody() throws InvocationTargetException, IllegalAccessException {
        method.invoke(test);
    }

    @Override
    protected void addFailure(Throwable e) {
        notifier.fireTestFailure(new Failure(description, e));
    }

    @Override
    protected void runFailure(Throwable targetException) {

    }
}
