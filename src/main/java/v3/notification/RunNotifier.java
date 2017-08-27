package v3.notification;

import v3.runner.Description;
import v3.runner.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/8/27.
 */
public class RunNotifier {
    private List<RunListener> fListeners = new ArrayList<>();
    private boolean fPleaseStop = false;

    public void addListener(RunListener listener) {
        fListeners.add(listener);
    }

    public void removeListener(RunListener listener) {
        fListeners.remove(listener);
    }

    private abstract class SafeNotifier {
        void run() {
            for (RunListener runListener : fListeners) {
                try {
                    notifyListener(runListener);
                } catch (Exception e) {
                    removeListener(runListener);
                    fireTestFailure(new Failure(Description.TEST_MECHANISM, e));
                }
            }
        }

        protected abstract void notifyListener(RunListener each) throws Exception;
    }

    public void fireTestFailure(final Failure failure) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testFailure(failure);
            }
        }.run();
    }

    public void fireTestRunStarted(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testRunStarted(description);
            }
        }.run();
    }

    public void fireTestRunFinished(final Result result) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testRunFinished(result);
            }
        }.run();
    }

    public void fireTestStarted(final Description description) throws StoppedByUserException {
        if (fPleaseStop) {
            throw new StoppedByUserException();
        }
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testStarted(description);
            }
        }.run();
    }

    public void fireTestIgnored(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testIgnored(description);
            }
        }.run();
    }

    public void fireTestFinished(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(RunListener each) throws Exception {
                each.testFinished(description);
            }
        }.run();
    }

    public void pleaseStop() {
        fPleaseStop = true;
    }
}
