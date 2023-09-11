package jiraapi.testcases.issue.deleteissue;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import io.restassured.response.Response;
import jiraapi.controller.ErrorMessages;
import jiraapi.controller.issue.Issue;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.FakerUtils;
import utilities.ResponseUtils;

public class API006 extends TestBase {
    Issue issue = new Issue();
    SoftAssert softAssert = new SoftAssert();
    String latestIssueID;
    String randomIssueID;

    @Test(testName = "AP006", description = "Verify that a unsuccessful response is given when user delete an non-existing issue")
    public void AP006(){

        ExtentReportManager.logStep("PRE-CONDITION", "Get latest issue id");
        Response latestIssueRes = issue.getAllIssuesFromProject();
        latestIssueID = ResponseUtils.getResponseAsList(latestIssueRes.getBody(),"issues").get(0).get("id").toString();
        randomIssueID = String.valueOf(FakerUtils.generateRandomIssueID(latestIssueID));

        ExtentReportManager.logStep("1", "Delete non-exist issue" + randomIssueID);
        Response deleteIssueRes = issue.deleteIssue(randomIssueID);
        ExtentReportManager.stepJsonNode("", "DELETE NON-EXIST ISSUE: " + randomIssueID, deleteIssueRes.asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that response code is 404", String.valueOf(deleteIssueRes.getStatusCode()));
        softAssert.assertEquals(deleteIssueRes.getStatusCode(), StatusCode.CODE_404.getStatusCode());

        ExtentReportManager.stepNodeVerify("Step 3: Verify that error message is returned", ResponseUtils.getValueFromBody(deleteIssueRes, "errorMessages[0]"));
        softAssert.assertEquals(ResponseUtils.getValueFromBody(deleteIssueRes, "errorMessages[0]"), ErrorMessages.ISSUE_DOES_NOT_EXIST.toString());
        softAssert.assertAll();



    }
}
