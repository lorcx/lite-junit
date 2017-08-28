package v3.sample;


import v3.After;
import v3.AfterClass;
import v3.Before;
import v3.Test;
import v3.runners.JUnitCore;

import static v3.Assert.*;

public class CalculatorTest {

    Calculator calculator = null;

    @Before
    public void prepare() {
        calculator = new Calculator();
    }

    @After
    public void clean() {
        calculator = null;
    }

    @Test
    public void testAdd() {

        calculator.add(10);
        assertEquals(10, calculator.getResult());
    }

    @Test
    public void testSubtract() {
        calculator.add(10);
        calculator.subtract(5);
        assertEquals(5, calculator.getResult());
    }

    @BeforeClass
    public static void prepareGlobalResouce() {
        System.err.println("prepare global resource");
    }

    @AfterClass
    public static void cleanGlobalResouce() {
        System.err.println("clean global resource");
    }


    public static void main(String[] args) {
        JUnitCore.runClass(CalculatorTest.class);

    }
}
