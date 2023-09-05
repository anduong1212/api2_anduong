package jiraapi.testcases.deleteissue;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import io.restassured.response.Response;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.NewIssue;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.FakerUtils;
import utilities.ResponseUtils;

public class API006 extends TestBase {
    Issue deleteIssueMethods = new Issue();
    SoftAssert softAssert = new SoftAssert();
    String latestIssueID = "";

    String randomIssueID;

    @Test(testName = "AP006", description = "Verify that a unsuccessful response is given when user delete an non-existing issue")
    public void AP006(){
        Response lastestIssueIdResponse = deleteIssueMethods.getAllIssuesFromProject();
        latestIssueID = ResponseUtils.getResponseAsList(lastestIssueIdResponse.getBody(), "issues").get(0).get("id").toString();
        randomIssueID = String.valueOf(FakerUtils.generateRandomIssueID(latestIssueID));
        ExtentReportManager.logMessage(Status.INFO, "Step 1: Delete an non-existing issue ID: " + randomIssueID);
        Response deleteIssueResponse = deleteIssueMethods.deleteIssue(randomIssueID);

        ExtentReportManager.logVerifyStep("[VERIFY] - Verify that response code is 404");
        softAssert.assertEquals(deleteIssueResponse.getStatusCode(), StatusCode.CODE_404_ISSUE_DOES_NOT_EXIST.getStatusCode());

        ExtentReportManager.logVerifyStep("[VERIFY] - Verify that an error message is returned to inform that an issue doesn't exist");
        softAssert.assertEquals(ResponseUtils.getValueFromBody(deleteIssueResponse,"errorMessages[0]"), StatusCode.CODE_404_ISSUE_DOES_NOT_EXIST.getErrorMessage());
        softAssert.assertAll();

    }
}
