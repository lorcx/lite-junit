package v3.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lx on 2017/8/27.
 */
public abstract class BeforeAndAfterRunner {
    private static class FailedBefore extends Exception {
        private static final long serialVersionUID= 1L;
    }

    private final Class<? extends Annotation> beforeAnnotion;

    private final Class<? extends Annotation> afterAnnotion;

    private TestIntrospector testIntrospector;

    private Object test;

    public BeforeAndAfterRunner(Class<?> testClass,
                                Class<? extends Annotation> beforeAnnotion,
                                Class<? extends Annotation> afterAnnotion,
                                Object test) {
        this.beforeAnnotion = beforeAnnotion;
        this.afterAnnotion = afterAnnotion;
        this.testIntrospector = new TestIntrospector(testClass);
        this.test = test;
    }

    public void runProtected() {
        try {
            runBefores();
            runUnprotected();
        } catch (FailedBefore e) {

        } finally {
            runAfters();
        }
    }

    private void runAfters() {
        List<Method> afters= testIntrospector.getTestMethods(afterAnnotion);
        for (Method after : afters) {
            try {
                invokeMethod(after);
            } catch (InvocationTargetException e) {
                addFailure(e.getTargetException());
            } catch (Throwable e) {
                addFailure(e); // Untested, but seems impossible
            }
        }
    }

    protected abstract void runUnprotected();

    protected abstract void addFailure(Throwable targetException);

    protected abstract void runFailure(Throwable targetException);

    private void runBefores()throws FailedBefore{
        List<Method> befores = testIntrospector.getTestMethods(beforeAnnotion);
        try {
            for (Method before : befores) {
                invokeMethod(before);
            }
        } catch (InvocationTargetException e) {
            addFailure(e.getTargetException());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invokeMethod(Method method) throws Exception {
        method.invoke(test);
    }
}
