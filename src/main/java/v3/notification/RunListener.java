package v3.notification;


import v3.runner.Description;
import v3.runner.Result;

/**
 * Created by lx on 2017/8/27.
 */
public class RunListener {
    /**
     * 在任何测试之前调用
     * @param description
     * @throws Exception
     */
    public void testRunStarted(Description description) throws Exception {

    }

    /**
     * 在所有测试完成后调用
     * @param description
     * @throws Exception
     */
    public void testRunFinished(Result result) throws Exception {

    }

    /**
     * 当一个原子测试即将开始的时候。
     * @param description
     * @throws Exception
     */
    public void testStarted(Description description) throws Exception{

    }

    /**
     * 当一个原子测试完成的时候。
     * @param description
     * @throws Exception
     */
    public void testFinished(Description description) throws Exception{

    }

    /**
     * 当原子测试失败时
     * @param failure
     */
    public void testFailure(Failure failure) {

    }

    /**
     * 当测试不运行时调用，通常是因为测试方法被注释为< code > @忽略< / code >。
     * @param description
     * @throws Exception
     */
    public void testIgnored(Description description)throws Exception{

    }

}
