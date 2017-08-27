package v2.runner;

import v2.Test;
import v2.TestListener;
import v2.TestSuite;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;

/**
 * 基本的抽象文本ui类，客户端必须存在 static suite 方法
 * 该方法作为case树的根节点，从这个方法开始运行
 * Created by lx on 2017/8/26.
 */
public abstract class BaseTestRunner implements TestListener {
    public static final String SUITE_METHODNAME = "suite";

    /**
     * 返回一个过滤的堆栈跟踪
     * @param t
     * @return
     */
    public static String getFilteredTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        String trace = buffer.toString();
        return trace;
    }

    /**
     * 获取并调用指定的suite方法
     * @param suiteClassName
     * @return
     */
    public Test getTest(String suiteClassName) {
        if (suiteClassName.length() <= 0) {
            return null;
        }

        Class testClass = null;
        try {
            testClass = loadSuiteClass(suiteClassName);
        } catch (ClassNotFoundException e) {
            String clazz = e.getMessage();
            if (clazz == null) {
                clazz = suiteClassName;
            }
            runFailed("Error: " + e.toString());
            return null;
        } catch (Exception e) {
            runFailed("Error: " + e.toString());
            return null;
        }

        Method suiteMethod = null;
        try {
            suiteMethod = testClass.getMethod(SUITE_METHODNAME, new Class[0]);
        } catch (NoSuchMethodException e) {
            return new TestSuite(testClass);
        }

        Test test = null;
        try {
            test = (Test) suiteMethod.invoke(null, new Class[0]);// static method
        } catch (IllegalAccessException e) {
            runFailed("Failed to invoke suite():" + e);
            return null;
        } catch (InvocationTargetException e) {
            runFailed("Failed to invoke suite():" + e.getTargetException());
            return null;
        }
        return test;
    }

    /**
     * 加载一个suite类
     * @param suiteClassName
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> loadSuiteClass(String suiteClassName) throws ClassNotFoundException {
        return Class.forName(suiteClassName);
    }

    /**
     * 将long 时间转换成 String 时间 秒
     * @param runTime
     * @return
     */
    public String elapsedTimeAsString(long runTime) {
        return NumberFormat.getInstance().format((double) runTime / 1000);
    }

    /**
     * 执行失败
     * @param message
     */
    protected abstract void runFailed(String message);

}
