package extension;

import v2.Test;
import v2.TestResult;
import v2.util.Assert;

/**
 * 一个测试装饰器类，使用TestDecorator作为基类，子类可以在测试前后加入新的行为。
 * Created by lx on 2017/8/26.
 */
public class TestDecorator extends Assert implements Test {
    protected Test test;

    public TestDecorator(Test test) {
        this.test = test;
    }

    @Override
    public void run(TestResult tr) {
        basicRun(tr);
    }

    public void basicRun(TestResult tr) {
        test.run(tr);
    }

    @Override
    public int countTestCases() {
        return test.countTestCases();
    }

    public String toString() {
        return test.toString();
    }

    public Test getTest() {
        return test;
    }
}
