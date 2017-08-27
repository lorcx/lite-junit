package extension;

import v2.Protectable;
import v2.Test;
import v2.TestResult;

/**
 * Created by lx on 2017/8/26.
 */
public class TestSetup extends TestDecorator{
    public TestSetup(Test test) {
        super(test);
    }

    @Override
    public void run(final TestResult tr) {
        Protectable p = () -> {
            setUp();
            basicRun(tr);
            tearDown();
        };
        tr.runProtected(this, p);
    }

    protected void setUp() throws Exception {

    }

    protected void tearDown() throws Exception {

    }

}
