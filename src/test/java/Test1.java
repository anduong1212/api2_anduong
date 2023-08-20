import common.report.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test1 {
    @Test(testName = "C0001", priority = 1, description = "description for test 1")
    public void loginTest(){
        Assert.assertTrue(false, "This testcase is failed");
    }

    @Test(testName = "C002", priority = 1, dependsOnMethods = "loginTest", description = "description for test 2")
    public void test2(){
        Assert.assertEquals(true, true);
    }
}
