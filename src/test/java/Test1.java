import common.report.ExtentReportManager;
import constant.Constants;
import controller.jiraapi.Issue;
import controller.jiraapi.dataobjects.NewIssue;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import testbase.TestBase;

public class Test1 extends TestBase {
    @Test(testName = "C0001", priority = 1, description = "description for test 1")
    public void loginTest(){
        ExtentReportManager.stepNode("ABC", "Doremi");
        ExtentReportManager.stepJsonNode("AUTH_RESPONSE", Constants.CLOUD_ID_RESPONSE);
        Assert.assertTrue(true, "This testcase is failed");

        Issue issueController = new Issue(NewIssue.NEW_ISSUE);

        Response createIssue = issueController.createIssue();
    }

//    @Test(testName = "C0002", priority = 1, dependsOnMethods = "loginTest", description = "description for test 2")
//    public void test2(){
//        Assert.assertEquals(true, false);
//    }
}
