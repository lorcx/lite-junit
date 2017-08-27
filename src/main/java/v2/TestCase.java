package v2;

import v2.util.Assert;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试用例
 * Created by lx on 2017/8/19.
 */
public abstract class TestCase extends Assert implements Test {
    // 测试用例的名字 测试的方法名称
    private String name;

    public TestCase(String name) {
        this.name = name;
    }

    /**
     * 运行测试
     */
    @Override
    public void run(TestResult tr) {
        tr.run(this);
    }

    /**
     * 运行测试用例
     * 执行准备工作
     * 测试
     * 执行清理工作
     * 模版方法
     * @throws Throwable
     */
    public void doRun() throws Throwable {
        setUp();
        try {
            runTest();
        } finally {
            // 每个测试失败后不影响下一个运行
            tearDown();
        }
    }

    /**
     * 调用测试用例方法
     * @throws Throwable
     */
    protected void runTest() throws Throwable {
        Method runMethod = null;
        try {
            runMethod = getClass().getMethod(name, null);
        } catch (NoSuchMethodException e) {
            fail("Method \"" + name + "\" not found");
        } catch (SecurityException e) {
            fail("Method \"" + name + "\" should be public");
        }

        try {
            runMethod.invoke(this, new Class[0]);
        } catch (InvocationTargetException e) {
            e.fillInStackTrace();
            throw e.getTargetException();
        } catch (IllegalAccessException e) {
            e.fillInStackTrace();
            throw e;
        }
    }

    /**
     * 测试用例数量
     * @return
     */
    @Override
    public int countTestCases() {
        return 1;
    }

    /**
     * 执行测试用例前准备操作
     */
    protected void setUp() {

    }

    /**
     * 执行测试用例后清理操作
     */
    protected void tearDown() {

    }
}
