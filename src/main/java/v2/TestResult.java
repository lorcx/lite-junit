package v2;


import v2.exception.AssertionFailedError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 测试结果
 * Created by lx on 2017/8/19.
 */
public class TestResult {
    // 测试失败
    public List<TestFailure> failures;
    // 测试错误
    public List<TestFailure> errors;
    // 监听者
    public List<TestListener> listeners;
    // 测试用例数量
    protected int testCount;
    // 是否停止
    private boolean isStop;

    public TestResult() {
        failures = new ArrayList<>();
        errors = new ArrayList<>();
        listeners = new ArrayList<>();
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
        for (TestListener listener : listeners) {
            listener.addError(t, e);
        }
    }

    /**
     * 添加失败信息
     * @param t
     * @param e
     */
    public void addFailure(Test t, AssertionFailedError e) {
        failures.add(new TestFailure(t, e));
        for (TestListener listener : listeners) {
            listener.addFailure(t, e);
        }
    }

    /**
     * 调用测试前结果前执行
     * 记录测试用例次数
     * @param test
     */
    public void startTest(Test test) {
        int count = test.countTestCases();
        testCount += count;
        for (TestListener listener : listeners) {
            listener.startTest(test);
        }
    }

    /**
     * 调用测试结果后执行
     * @param test
     */
    public void endTest(Test test) {
        for (TestListener listener : listeners) {
            listener.endTest(test);
        }
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

    /**
     * 返回errors的Iterator
     * @return
     */
    public Iterator<TestFailure> errors() {
        return errors.iterator();
    }

    /**
     * 返回failures的Iterator
     * @return
     */
    public Iterator<TestFailure> failures() {
        return failures.iterator();
    }

    /**
     * 添加反馈
     * @param listener
     */
    public void addListener(TestListener listener) {
        listeners.add(listener);
    }

    /**
     * 删除反馈
     * @param listener
     */
    public void removeListener(TestListener listener) {
        listeners.remove(listener);
    }

    /**
     * 运行受保护的测试
     * @param test
     * @param p
     */
    public void runProtected(Test test, Protectable p) {
        try {
            p.protect();
        } catch (AssertionFailedError e) {
            addFailure(test, e);
        } catch (Throwable e) {
            addError(test, e);
        }
    }
}
