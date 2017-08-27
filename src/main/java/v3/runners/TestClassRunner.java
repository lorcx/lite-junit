package v3.runners;

import v3.After;
import v3.Before;
import v3.notification.Failure;
import v3.notification.RunNotifier;
import v3.runner.Description;
import v3.runner.Runner;

/**
 * Created by lx on 2017/8/27.
 */
public class TestClassRunner extends Runner{
    protected final Runner enclosedRunner;
    private final Class<?> testClass;

    public TestClassRunner(Class<?> testClass) {
        this(testClass, new TestClassMethodsRunner(testClass));
    }

    public TestClassRunner(Class<?> testClass, Runner enclosedRunner) {
        this.enclosedRunner = enclosedRunner;
        this.testClass = testClass;
    }

    @Override
    public Description getDescription() {
        return enclosedRunner.getDescription();
    }

    protected Class<?> getTestClass() {
        return testClass;
    }

    @Override
    public void run(RunNotifier notifier) {
        BeforeAndAfterRunner runner = new BeforeAndAfterRunner(getTestClass(),
                Before.class, After.class, null) {
            @Override
            protected void runUnprotected() {
                enclosedRunner.run(notifier);
            }

            @Override
            protected void addFailure(Throwable targetException) {
                notifier.fireTestFailure(new Failure(getDescription(), targetException));
            }

            @Override
            protected void runFailure(Throwable targetException) {

            }
        };

        runner.runProtected();
    }
}
