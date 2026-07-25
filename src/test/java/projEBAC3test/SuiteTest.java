package projEBAC3test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Test Suite")
@SelectClasses({
        ClienteTest.class,
        ProdutoTest.class
})
public class SuiteTest {
}
