package v2;


import v2.exception.AssertionFailedError;

/**
 * 监听一个测试用例
 * Created by lx on 2017/8/26.
 */
public interface TestListener {
    /**
     * 添加一个错误
     * @param test
     * @param e
     */
    void addError(Test test, Throwable e);

    /**
     * 添加一个失败
     * @param test
     * @param t
     */
    void addFailure(Test test, AssertionFailedError t);

    /**
     * 开始测试前执行
     * @param t
     */
    void startTest(Test t);

    /**
     * 结束测试前执行
     * @param t
     */
    void endTest(Test t);
}
