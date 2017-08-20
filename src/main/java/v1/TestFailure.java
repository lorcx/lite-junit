package v1;

/**
 * 测试失败类
 * Created by lx on 2017/8/19.
 */
public class TestFailure {
    protected Test failedTest;
    protected Throwable thrownException;

    public TestFailure(Test failedTest, Throwable thrownException) {
        this.failedTest = failedTest;
        this.thrownException = thrownException;
    }

    /**
     * 获得失败的Test
     * @return
     */
    public Test failedTest() {
        return failedTest;
    }

    /**
     * 获得thrownException
     * @return
     */
    public Throwable thrownException() {
        return thrownException;
    }

    /**
     * @return 返回失败的简短描述
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(failedTest + "：" + thrownException.getMessage());
        return buffer.toString();
    }
}
