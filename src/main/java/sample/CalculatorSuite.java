package sample;


import v2.Test;
import v2.TestSuite;

/**
 * calculator包下的测试用例
 * 将他们组合成suite
 */
public class CalculatorSuite {
	public static Test suite(){
		TestSuite suite= new TestSuite("Calculator All Test");
		suite.addTestSuite(CalculatorTest.class);		
		return suite;
	}
}
