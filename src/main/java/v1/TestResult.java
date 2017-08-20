package v1;

import v1.exception.AssertionFailedError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 测试结果
 * Created by lx on 2017/8/19.
 */
public class TestResult {
    // 测试失败
    public List<TestFailure> failures = new ArrayList<>();
    // 测试错误
    public List<TestFailure> errors = new ArrayList<>();
    // 测试用例数量
    protected int testCount;
    // 是否停止
    private boolean isStop;

    public TestResult() {
        failures = new ArrayList<>();
        errors = new ArrayList<>();
        testCount = 0;
        isStop = false;
    }

    /**
     * 添加错误信息
     * @param t
     * @param e
     */
    public void addError(Test t, Throwable e) {
        errors.add(new TestFailure(t, e));
    }

    /**
     * 添加失败信息
     * @param t
     * @param e
     */
    public void addFailure(Test t, Throwable e) {
        failures.add(new TestFailure(t, e));
    }

    /**
     * 调用测试前结果前执行
     * 记录测试用例次数
     * @param test
     */
    public void startTest(Test test) {
        int count = test.countTestCases();
        testCount += count;
    }

    /**
     * 调用测试结果后执行
     * @param test
     */
    public void endTest(Test test) {

    }

    /**
     * 获得运行的测试用例次数
     * @return
     */
    public int runCount() {
        return testCount;
    }

    /**
     * 收集参数模式
     * 调用测试返回结果的模版方法
     * @param test
     */
    protected void run(final TestCase test) {
        startTest(test);
        try {
            test.doRun();
        } catch (AssertionFailedError e) {
            addFailure(test, e);
        } catch (Throwable t) {
            addError(test, t);
        }
        endTest(test);
    }

    /**
     * 判断是否应该停止测试
     * @return
     */
    public boolean shouldStop() {
        return isStop;
    }

    /**
     * 将当前测试结果设置为停止
     */
    public void stop() {
        isStop = true;
    }

    /**
     * 返回错误的测试用例个数
     * @return
     */
    public int errorCount() {
        return errors.size();
    }

    /**
     * 返回失败的测试用例个数
     * @return
     */
    public int failurCount() {
        return failures.size();
    }

    /**
     * 返回整个测试是否成功
     * @return
     */
    public boolean wasSuccessful() {
        return this.failurCount() == 0 && this.errorCount() == 0;
    }

    public Iterator<TestFailure> errors() {
        return errors.iterator();
    }

    public Iterator<TestFailure> failures() {
        return failures.iterator();
    }
}
