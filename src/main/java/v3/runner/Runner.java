package v3.runner;

import v3.notification.RunNotifier;

/**
 * Created by lx on 2017/8/27.
 */
public abstract class Runner {
    public abstract Description getDescription();

    public abstract void run(RunNotifier notifier);

    public int testCount() {
        return getDescription().testCount();
    }
}
