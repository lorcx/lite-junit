package v3.runners;

import v3.notification.RunListener;
import v3.notification.RunNotifier;
import v3.runner.Result;


/**
 * Created by lx on 2017/8/27.
 */
public class JUnitCore {
    private RunNotifier notifier;

    public JUnitCore() {
        this.notifier = new RunNotifier();
    }

    public static void runClass(Class<?> clz) {
//        try {
            TestClassRunner runner = new TestClassRunner(clz);
            JUnitCore core = new JUnitCore();
            core.addListener(new TextListener());
            Result result = core.run(runner);
//        } catch (InitializationError e) {
//            e.printStackTrace();
//        }
    }

    private Result run(TestClassRunner runner) {
        Result result = new Result();
        RunListener listener = result.createListener();
        addListener(listener);
        try {
            notifier.fireTestRunStarted(runner.getDescription());
            runner.run(notifier);
            notifier.fireTestRunFinished(result);
        } finally {
            removeListener(listener);
        }
        return result;
    }

    public void addListener(RunListener listener) {
        notifier.addListener(listener);
    }

    public void removeListener(RunListener listener) {
        notifier.removeListener(listener);
    }
}
