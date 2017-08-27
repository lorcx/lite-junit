package v2.exception;

/**
 * 当断言失败时抛出
 * Created by lx on 2017/8/19.
 */
public class AssertionFailedError extends Error{
    public AssertionFailedError() {
    }

    public AssertionFailedError(String message) {
        super(message);
    }
}
