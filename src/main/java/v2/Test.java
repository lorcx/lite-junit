package v2;

/**
 * 测试接口
 * Created by lx on 2017/8/19.
 */
public interface Test {
    /**
     * 运行一个测试
     * @param tr
     */
    void run(TestResult tr);

    /**
     * 记录测试用例个数
     * @return
     */
    int countTestCases();
}
