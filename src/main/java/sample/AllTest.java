package sample;

import extension.RepeatedTest;
import extension.TestSetup;
import v2.Test;
import v2.TestSuite;

/**
 * 所有包的测试用例
 * Created by lx on 2017/8/26.
 */
public class AllTest {

    /**
     * 该方法必须为static suite
     * @return
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("All TEST");
        suite.addTest(CalculatorSuite.suite());
        suite.addTest(new RepeatedTest(new TestSuite(PersonTest.class), 1));
        return new OverallTestSetup(suite);
    }

    /**
     * 使用装饰器模式扩展总的测试用例
     */
    static class OverallTestSetup extends TestSetup {

        public OverallTestSetup(Test test) {
            super(test);
        }

        @Override
        protected void setUp() throws Exception {
            System.out.println("this is overall testsetup");
        }

        @Override
        protected void tearDown() throws Exception {
            System.out.println("this is overall teardown");
        }
    }
}
