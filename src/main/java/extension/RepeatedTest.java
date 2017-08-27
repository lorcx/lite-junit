package extension;

import v2.Test;
import v2.TestResult;

/**
 * Created by lx on 2017/8/26.
 */
public class RepeatedTest extends TestDecorator{
    private int fTimeRepeat;

    public RepeatedTest(Test test, int repeat) {
        super(test);
        if (repeat < 0) {
            throw new IllegalArgumentException("Repetition count must be > 0");
        }
        fTimeRepeat = repeat;
    }

    @Override
    public int countTestCases() {
        return super.countTestCases() * fTimeRepeat;
    }

    @Override
    public void run(TestResult tr) {
        for (int i = 0; i < fTimeRepeat; i++) {
            if (tr.shouldStop()) {
                break;
            }
            super.run(tr);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "(repeated)";
    }
}
