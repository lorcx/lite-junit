package v3.requests;

import v3.runner.Request;
import v3.runner.Runner;
import v3.runners.TestClassRunner;
import java.lang.reflect.Constructor;

/**
 * Created by Administrator on 2017/8/28 0028.
 */
public class ClassRequest extends Request{
    private final Class<?> fTestClass;

    public ClassRequest(Class<?> fTestClass) {
        this.fTestClass = fTestClass;
    }

    @Override
    public Runner getRunner() {
        Class runnerClass = getRunnerClass(fTestClass);
        try {
            Constructor constructor = runnerClass.getConstructor(Class.class);
            Runner runner = (Runner) constructor.newInstance(new Object[] {fTestClass});
            return runner;
        } catch (Exception e) {
            return null;
        }
    }

    private Class getRunnerClass(Class<?> fTestClass) {
        return TestClassRunner.class;
    }
}
