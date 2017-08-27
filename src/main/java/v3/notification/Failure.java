package v3.notification;

import v3.runner.Description;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by lx on 2017/8/27.
 */
public class Failure {
    protected Description fDescription;
    protected Throwable thrownException;

    public Failure(Description description, Throwable thrownException) {
        this.fDescription = description;
        this.thrownException = thrownException;
    }

    public String getTestHeader() {
        return fDescription.getDisplayName();
    }

    public Description getDescription() {
        return fDescription;
    }

    /**
     * 获得thrownException
     * @return
     */
    public Throwable getException() {
        return thrownException;
    }

    /**
     * @return 返回失败的简短描述
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(fDescription + "：" + thrownException.getMessage());
        return buffer.toString();
    }

    public String getTrace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        getException().printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    public String getMessage() {
        return getException().getMessage();
    }
}
