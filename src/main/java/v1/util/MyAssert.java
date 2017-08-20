package v1.util;

import v1.exception.AssertionFailedError;

/**
 * 学习junit断言工具类编写
 * Created by lx on 2017/8/20.
 */
public class MyAssert {
    protected MyAssert() {

    }

    /**
     * 断言是否为true,并给一个失败的信息
     * @param msg
     * @param condition
     */
    public static void assertTrue(String msg, boolean condition) {
        if (!condition) {
            fail(msg);
        }
    }

    /**
     * 断言是否为true
     * @param condition
     */
    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    /**
     * 给定一个失败的错误信息，并抛出异常
     * @param msg
     */
    public static void fail(String msg) {
        throw new AssertionFailedError(msg);
    }

    /**
     * 抛出失败异常
     */
    public static void fail() {
        fail(null);
    }

    /**
     * 断言是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(String msg, Object expected, Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (null != expected && expected.equals(actual)) {
            return;
        }

        failNotEquals(msg, expected, actual);
    }

    /**
     * 断言是否相等
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * 断言2个double是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     * @param delta
     */
    public static void assertEquals(String msg, double expected, double actual, double delta) {
       if (Double.isInfinite(expected)) {
           if (expected != actual) {
               failNotEquals(msg, expected, delta);
           }
       } else {
           failNotEquals(msg, expected, actual);
       }
    }

    /**
     * 断言2个double是否相等
     * @param expected 期望值
     * @param actual 实际值
     * @param delta
     */
    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * 断言2个float是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     * @param delta
     */
    public static void assertEquals(String msg, float expected, float actual, float delta) {
        if (Float.isInfinite(expected)) {
            if (expected != actual) {
                failNotEquals(msg, expected, delta);
            }
        } else {
            failNotEquals(msg, expected, actual);
        }
    }

    /**
     * 断言2个float是否相等
     * @param expected 期望值
     * @param actual 实际值
     * @param delta
     */
    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * 断言2个long是否相等
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * 断言2个long是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(String msg, long expected, long actual) {
        assertEquals(msg, new Long(expected), new Long(actual));
    }

    /**
     * 断言2个布尔是否相等
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(boolean expected, boolean actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * 断言2个布尔是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(String msg, boolean expected, boolean actual) {
        assertEquals(msg, new Boolean(expected), new Boolean(actual));
    }

    /**
     * 断言2个byte是否相等
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(byte expected, byte actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * 断言2个byte是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(String msg, byte expected, byte actual) {
        assertEquals(msg, new Byte(expected), new Byte(actual));
    }

    /**
     * 断言2个short是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(String msg, short expected, short actual) {
        assertEquals(msg, new Short(expected), new Short(actual));
    }

    /**
     * 断言2个short是否相等
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(short expected, short actual) {
        assertEquals(null, expected, actual);
    }


    /**
     * 断言2个int是否相等
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(String msg, int expected, int actual) {
        assertEquals(msg, new Integer(expected), new Integer(actual));
    }

    /**
     * 断言2个int是否相等
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertEquals(int expected, int actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * 断言对象是否不为空
     * @param obj
     */
    public static void assertNotNull(Object obj) {
        assertNotNull(null, obj);
    }

    /**
     * 断言对象是否不为空
     * @param msg 失败信息
     * @param obj
     */
    public static void assertNotNull(String msg, Object obj) {
        assertTrue(msg, (obj != null));
    }

    /**
     * 断言对象是否为空
     * @param obj
     */
    public static void assertNull(Object obj) {
        assertNull(null, obj);
    }

    /**
     * 断言对象是否为空
     * @param obj
     */
    public static void assertNull(String msg, Object obj) {
        assertTrue(msg, (obj == null));
    }

    /**
     * 断言对象是否为空
     * @param msg 失败后显示的信息
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertSame(String msg, Object expected, Object actual) {
        if (expected == actual) {
            return;
        }
        failNotSame(msg, expected, actual);
    }

    /**
     * 断言对象是否为空
     * @param expected 期望值
     * @param actual 实际值
     */
    public static void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    /**
     * 两个值不相等
     * @param msg
     * @param expected
     * @param actual
     */
    private static void failNotEquals(String msg, Object expected, Object actual) {
        String formatted = "";
        if (null != msg) {
            formatted = msg + " ";
        }
        fail(formatted + "excepted:<" + expected + "> but was:<" + actual + ">");
    }

    /**
     * 两个对象引用不相等
     * @param msg
     * @param expected
     * @param actual
     */
    private static void failNotSame(String msg, Object expected, Object actual) {
        String formatted = "";
        if (null != msg) {
            formatted = msg + " ";
        }
        fail(formatted + "excepted same");
    }

    public static void main(String[] args) {
        try {
//            assertTrue(false);
//            assertTrue("sssss", false);
//            assertTrue("sssss", true);


            Object o1 = new Object();
            Object o2 = o1;
            Object o3 = new Object();
//            assertEquals("qqq", o1, o2);
//            assertEquals("qqq", o1, o3);
//            assertEquals(o1, o3);

//              double d1 = 1d, d2 = d1, d3 = 2d;
//              assertEquals(d1, d2);
//              assertEquals(d1, d3);

//            float f1 = 1f, f2 = f1, f3 = 2f;
//            assertEquals(f1, f2);
//            assertEquals(f1, f3);

//            byte b1 = 1, b2 = b1, b3 = 2;
//            assertEquals(b1, b2);
//            assertEquals(b1, b3);

//            int i1 = 1, i2 = i1, i3 = 2;
//            assertEquals(i1, i2);
//            assertEquals(i1, i3);

//            assertSame(o1, o2);
//            assertSame(o1, o3);

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
