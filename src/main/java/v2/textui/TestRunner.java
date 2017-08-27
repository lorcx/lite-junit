package v2.textui;

import v2.Test;
import v2.TestFailure;
import v2.TestResult;
import v2.exception.AssertionFailedError;
import v2.runner.BaseTestRunner;

import java.io.PrintStream;

/**
 * 文本ui类，启动自动化测试的人口类
 * Created by lx on 2017/8/26.
 */
public class TestRunner extends BaseTestRunner {
    // 控制台输出
    PrintStream writer = System.out;
    // 记录测试用例个数
    int column;

    /**
     * 当运行失败时
     * @param message
     */
    @Override
    protected void runFailed(String message) {
        System.err.println(message);
        System.exit(-1);
    }

    /**
     * 添加一个错误
     * @param test
     * @param e
     */
    @Override
    public void addError(Test test, Throwable e) {
        writer.print("E");
    }

    /**
     * 添加一个失败
     * @param test
     * @param t
     */
    @Override
    public void addFailure(Test test, AssertionFailedError t) {
        writer.print("F");
    }

    /**
     * 框架开始执行内部方法
     * 每执行一个输出. 超过40个字符换行
     * 出错E 失败F
     * @param t
     */
    @Override
    public void startTest(Test t) {
        writer.print(".");
        if (column++ >= 40) {
            writer.println();
            column = 0;
        }
    }

    /**
     * 框架结束执行内部方法
     * @param t
     */
    @Override
    public void endTest(Test t) {

    }

    /**
     * 反射调用 suite方法（root）
     * @param args
     * @return
     * @throws Exception
     */
    protected TestResult start(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("Usage: TestRunner testCaseName");
        }
        String testCase = args[0];
        try {
            //这个suite 为根部suite
            Test suite = getTest(testCase);
            return doRun(suite);
        } catch (Exception e) {
            throw new Exception("Could not create and run test suite: " + e);
        }
    }

    /**
     * 将自己作为listener
     * 计算运行时间
     * 打印测试结果
     * @param suite
     * @return
     */
    public TestResult doRun(Test suite) {
        TestResult result = new TestResult();
        result.addListener(this);
        long startTime = System.currentTimeMillis();
        suite.run(result);
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        writer.println();
        writer.println("Time: " + elapsedTimeAsString(runtime));
        print(result);
        writer.println();
        return result;
    }

    /**
     * 打印结果
     * @param result
     */
    public void print(TestResult result) {
        printErrors(result);
        printFailures(result);
        printHeader(result);
    }

    /**
     * 打印错误信息
     * @param result
     */
    public void printErrors(TestResult result) {
        if (result.errorCount() != 0) {
            if (result.errorCount() == 1) {
                writer.print("There was " + result.errorCount() + " error:");
            } else {
                writer.print("There were " + result.errorCount() + " errors");
            }
            int i = 1;
            for (TestFailure failure : result.errors) {
                writer.println(i + ")" + failure.failedTest());
                writer.print(getFilteredTrace(failure.thrownException()));
            }

        }
    }

    /**
     * 打印失败信息
     * @param result
     */
    public void printFailures(TestResult result) {
        if (result.failurCount() != 0) {
            if (result.failurCount() == 1) {
                writer.println("There was " + result.failurCount() + " failure:");
            } else {
                writer.println("There were " + result.failurCount() + " failures:");
            }
            int i = 1;
            for (TestFailure failure : result.failures) {
                writer.println(i + ")" + failure.failedTest());
                writer.print(getFilteredTrace(failure.thrownException()));
            }
        }
    }

    /**
     * 打印开头信息
     * @param result
     */
    public void printHeader(TestResult result) {
        if (result.wasSuccessful()) {
            writer.println();
            writer.print("OK");
            writer.print(" (" + result.runCount() + " tests)");
        } else {
            writer.println();
            writer.print("FAILURES!!!  ");
            writer.print("Test run: " + result.runCount() +
                    ", Failures: " + result.failurCount() +
                    ", Errors: " + result.errorCount());
        }
    }

    public static void main(String[] args) {
        // args : sample.AllTest
        TestRunner testRunner = new TestRunner();
        try {
            TestResult r = testRunner.start(args);
            if (!r.wasSuccessful()) {
                System.exit(-1);
            }
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-2);
        }
    }

}
