package v3.runner;

import v3.requests.ClassRequest;

/**
 * Created by lx on 2017/8/27.
 */
public abstract class Request {
    public static Request aClass(Class<?> clazz) {
        return new ClassRequest(clazz);
    }

    public abstract Runner getRunner();
}

