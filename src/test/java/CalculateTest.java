import v1.TestCase;
import v1.TestResult;
import v1.TestSuite;

/**
 * Created by lx on 2017/8/19.
 */
public class CalculateTest extends TestCase{
    Calculateor calculateor = null;

    public CalculateTest(String name) {
        super(name);
    }

    public void setUp() {
        calculateor = new Calculateor();
    }

    public void tearDown() {
        calculateor = null;
    }

    public void testSubtract() {
        calculateor.add(10);
        calculateor.subtract(5);
        assertEquals(4, calculateor.getResult());
//        assertEquals(5, calculateor.getResult());
    }

    public void testAdd() {
        calculateor.add(10);
        assertEquals(10, calculateor.getResult());
    }

    public static void main(String[] args) {
//        TestCase t1 = new CalculateTest("test1");
//        TestCase t2 = new CalculateTest("test2");
//        t1.run();
//        t2.run();

        // 同时运行多个用例
//        TestSuite ts = new TestSuite();
//        ts.addTest(new CalculateTest("testSubtract"));
//        ts.addTest(new CalculateTest("testAdd"));
//        ts.run();

        TestSuite ts = new TestSuite(CalculateTest.class);
        TestResult tr = new TestResult();
        ts.run(tr);

        System.out.println(tr.wasSuccessful());
        tr.failures.forEach(System.err::println);
        tr.errors.forEach(System.err::println);
    }
}
