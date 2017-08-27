package v3.runners;

import v3.Before;
import v3.Ignore;
import v3.Test;
import v3.sample.BeforeClass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lx on 2017/8/27.
 */
public class TestIntrospector {
    private final Class<?> testClass;

    public TestIntrospector(Class<?> testClass) {
        this.testClass = testClass;
    }

    public List<Method> getTestMethods(Class<? extends Annotation> anclClass) {
        List<Method> results = new ArrayList<>();
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(anclClass);
            if (annotation != null && !isShadowed(method, results)) {
                results.add(method);
            }
        }

        if (runsTopToBottom(anclClass)) {
            Collections.reverse(results);
        }
        return results;

    }

    public boolean isIgnored(Method eachMethod) {
        return eachMethod.getAnnotation(Ignore.class) != null;
    }

    private boolean runsTopToBottom(Class<? extends Annotation> anclClass) {
        return anclClass.equals(Before.class) || anclClass.equals(BeforeClass.class);
    }

    private boolean isShadowed(Method method, List<Method> results) {
        for (Method m : results) {
            if (m.getName().equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    long getTimeout(Method method) {
        Test annotation = method.getAnnotation(Test.class);
        long timeout = annotation.timeOut();
        return timeout;
    }

    Class<? extends Throwable> expectedException(Method method) {
        Test annotation = method.getAnnotation(Test.class);
        if (annotation.expected() == Test.None.class) {
            return null;
        } else {
            return annotation.expected();
        }
    }
}
