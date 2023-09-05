import common.report.ExtentReportManager;
import jiraapi.controller.issue.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;

public class Test1 extends TestBase {
    Issue issueController = new Issue();
    SoftAssert softAssert = new SoftAssert();
    @Test(testName = "C0001", priority = 1, description = "description for test 1")
    public void loginTest(){
        ExtentReportManager.stepNode("GET ALL ISSUE", "ADAPI");
        Response getAllIssues = issueController.getAllIssuesFromProject();
        ExtentReportManager.stepJsonNode("Step 2: Verify response", getAllIssues.getBody().asString());

    }

}
