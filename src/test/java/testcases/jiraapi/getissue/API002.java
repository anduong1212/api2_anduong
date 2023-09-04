package testcases.jiraapi.getissue;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import controller.jiraapi.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.FakerUtils;
import utilities.ResponseUtils;

public class API002 extends TestBase {
    Issue getIssueMethods = new Issue();
    SoftAssert softAssert = new SoftAssert();

    String latestIssueId = "";

    @Test(testName = "API002", description = "Verify that an succesful response is given when user get an un-exist issue id")
    public void API002(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] - Get latest issue ID");
        Response lastestIssueIdResponse = getIssueMethods.getAllIssuesFromProject();
        latestIssueId = ResponseUtils.getResponseAsList(lastestIssueIdResponse.getBody(), "issues").get(0).get("id").toString();

        ExtentReportManager.stepNode("1: Get an un-exist issue", String.valueOf(FakerUtils.generateRandomIssueID(latestIssueId)));
        Response randomIssueIdResponse = getIssueMethods.getIssue(String.valueOf(FakerUtils.generateRandomIssueID(latestIssueId)));

        softAssert.assertEquals(randomIssueIdResponse.getStatusCode(), StatusCode.CODE_404_ISSUE_DOES_NOT_EXIST.getStatusCode());
        softAssert.assertEquals(ResponseUtils.getValueFromBody(randomIssueIdResponse,"errorMessages[0]"), StatusCode.CODE_404_ISSUE_DOES_NOT_EXIST.getErrorMessage());
        softAssert.assertAll();

    }
}
