package v3.runner;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述了一个将要运行或已经运行的测试。
 * Created by lx on 2017/8/27.
 */
public class Description {
    public static Description TEST_MECHANISM = new Description("Test mechanism");
    private final List<Description> fChildren = new ArrayList<>();
    private final String fDisplayName;

    private Description(final String displayName) {
        this.fDisplayName = displayName;
    }

    /**
     * Create a <code>Description</code> named <code>name</code>.
     * Generally, you will add children to this <code>Description</code>.
     * @param name The name of the <code>Description</code>
     * @return A <code>Description</code> named <code>name</code>
     */
    public static Description createSuiteDescription(String name) {
        return new Description(name);
    }

    /**
     * Create a generic <code>Description</code> that says there are tests in <code>testClass</code>.
     * This is used as a last resort when you cannot precisely describe the individual tests in the class.
     * @param testClass A <code>Class</code> containing tests
     * @return A <code>Description</code> of <code>testClass</code>
     */
    public static Description createSuiteDescription(Class<?> testClass) {
        return new Description(testClass.getName());
    }

    /**
     * Create a <code>Description</code> of a single test named <code>name</code> in the class <code>clazz</code>.
     * Generally, this will be a leaf <code>Description</code>.
     * @param clazz The class of the test
     * @param name The name of the test (a method name for test annotated with <code>@Test</code>)
     * @return A <code>Description</code> named <code>name</code>
     */
    public static Description createTestDescription(Class clazz, String name) {
        return new Description(String.format("%s(%s)", name, clazz.getName()));
    }

    public String getDisplayName() {
        return fDisplayName;
    }

    public void addChild(Description description) {
        getChildren().add(description);
    }

    public List<Description> getChildren() {
        return fChildren;
    }

    public boolean isSuite() {
        return !isTest();
    }

    public boolean isTest() {
        return getChildren().isEmpty();
    }

    public int testCount() {
        if (isTest()) {
            return 1;
        }
        int result = 0;
        for (Description child : getChildren()) {
            result += child.testCount();
        }
        return result;
    }

    @Override
    public int hashCode() {
        return getDisplayName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Description)) {
            return false;
        }
        Description d = (Description) obj;
        return getDisplayName().equals(d.getDisplayName()) &&
                getChildren().equals(d.getChildren());
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
