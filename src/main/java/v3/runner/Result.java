package v3.runner;

import v3.notification.Failure;
import v3.notification.RunListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2017/8/27.
 */
public class Result {
    private int fCount;
    private int fIgnoreCount;
    private List<Failure> fFailures = new ArrayList<>();
    private long fRuntime;
    private long fStartTime;

    public int getRunCount(){
        return fCount;
    }

    public int getFailureCount() {
        return fFailures.size();
    }

    public long getRunTime() {
        return fRuntime;
    }

    public int getIgnoreCount() {
        return fIgnoreCount;
    }

    public List<Failure> getFailures() {
        return fFailures;
    }

    public boolean wasSuccessful() {
        return getFailureCount() == 0;
    }

    private class Listener extends RunListener {
        @Override
        public void testRunStarted(Description description) throws Exception {
            fStartTime = System.currentTimeMillis();
        }

        @Override
        public void testRunFinished(Result result) throws Exception {
            long endTime = System.currentTimeMillis();
            fRuntime += endTime - fStartTime;
        }

        @Override
        public void testStarted(Description description) throws Exception {
            fCount++;
        }

        @Override
        public void testFailure(Failure failure) {
            fFailures.add(failure);
        }

        @Override
        public void testIgnored(Description description) throws Exception {
            fIgnoreCount++;
        }
    }

    public RunListener createListener() {
        return new Listener();
    }
}
