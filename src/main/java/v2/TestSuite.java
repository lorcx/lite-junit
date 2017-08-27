package v2;


import sample.CalculatorTest;
import v1.util.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 一组测试用例的抽象
 * Created by lx on 2017/8/19.
 */
public class TestSuite extends Assert implements Test {
    // 存储多个测试用例
    private List<Test> tests = new ArrayList<>(10);
    // 类名
    private String name;

    public TestSuite() {
    }

    /**
     * 通过反射收集当前类所有以test为开头的方法
     * 1.通过构造方法创建对象实例
     * 2.通过反射获取所有方法
     * 3.调用以test为开头的方法，用创建好的实例去调用
     * @param theClass
     */
    public TestSuite(final Class<?> theClass) {
        this.name = theClass.getName();
        Constructor<?> constructor = null;
        try {
            constructor = getConstructor(theClass);
        } catch (NoSuchMethodException e) {
            addTest(warning("Class " + theClass.getName() + "has no public constructor TestCase(String name)"));
            return;
        }

        if (!Modifier.isPublic(theClass.getModifiers())) {
            addTest(warning("Class " + theClass.getName() + " is not public"));
            return;
        }

        Vector<String> names = new Vector<>();
        Method[] methods = theClass.getDeclaredMethods();
        for (Method m : methods) {
            addtestMethod(m, names, constructor);
        }

        if (tests.size() == 0) {
            addTest(warning("No tests found in " + theClass.getName()));
        }
    }

    /**
     * name 为主题
     * @param name
     */
    public TestSuite(String name) {
        this.name = name;
    }

    /**
     * 添加测试方法，反射生成test对象
     * @param m
     * @param names
     * @param constructor
     */
    private void addtestMethod(Method m, Vector<String> names, Constructor<?> constructor) {
        String name = m.getName();
        if (names.contains(name)) {
            return;
        }

        if (isPublicTestMethod(m)) {
            names.addElement(name);

            Object[] args = new Object[]{name};
            try {
                addTest((Test) constructor.newInstance(args));
            } catch (InstantiationException e) {
                e.printStackTrace();
                addTest(warning("Cannot instantiate test case：" + name + "(" + exceptionsToString(e) + ")"));
            } catch (IllegalAccessException e) {
                addTest(warning("Excetpiong in constructor：" + name + "(" + exceptionsToString(e) + ")"));
            } catch (InvocationTargetException e) {
                addTest(warning("Cannot access test case：" + name + "(" + exceptionsToString(e.getTargetException()) + ")"));
            }
        } else {
            if (isTestMethod(m)) {
                addTest(warning("Test method isn\'t public：" + m.getName()));
            }
        }
    }

    /**
     * 是否为public 的test方法
     * @param m
     * @return
     */
    private boolean isPublicTestMethod(Method m) {
        return isTestMethod(m) && Modifier.isPublic(m.getModifiers());
    }

    /**
     * 是否以test为开头,无返回值，无参数的方法
     * @param m
     * @return
     */
    private boolean isTestMethod(Method m) {
        String name = m.getName();
        Class<?>[] paramters = m.getParameterTypes();
        Class<?> returnType = m.getReturnType();
        return paramters.length == 0 && name.startsWith("test") && returnType.equals(Void.TYPE);
    }

    /**
     * 获取一个类的构造方法
     * @param theClass
     * @return
     * @throws NoSuchMethodException
     */
    private Constructor<?> getConstructor(Class<?> theClass) throws NoSuchMethodException {
        Class<?>[] args = {String.class};
        return theClass.getConstructor(args);
    }

    /**
     * 运行一个测试
     * @param tr
     */
    @Override
    public void run(TestResult tr) {
        for (Test r : tests) {
            if (tr.shouldStop()) {
                break;
            }
            r.run(tr);
        }
    }

    /**
     * 计算一组测试用例个数
     * @return
     */
    @Override
    public int countTestCases() {
        int count = 0;
        for (Test t : tests) {
            count += t.countTestCases();
        }
        return count;
    }

    /**
     * 添加一个测试
     * @param t
     */
    public void addTest(Test t) {
        tests.add(t);
    }

    /**
     * 添加一个suite (组合模式)
     * @param testClass
     */
    public void addTestSuite(Class<CalculatorTest> testClass) {
        addTest(new TestSuite(testClass));
    }

    /**
     * 添加警告信息
     * @param message
     * @return
     */
    private Test warning(final String message) {
        return new TestCase("warning") {
            @Override
            public void doRun() throws Throwable {
                fail(message);
            }
        };
    }

    /**
     * 打印异常
     * @param t
     * @return
     */
    private String exceptionsToString(Throwable t) {
        StringWriter buffer = new StringWriter();
        PrintWriter writer = new PrintWriter(buffer);
        t.printStackTrace(writer);
        return writer.toString();
    }
}
