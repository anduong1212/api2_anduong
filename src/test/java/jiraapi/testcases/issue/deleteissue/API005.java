package jiraapi.testcases.issue.deleteissue;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import io.restassured.response.Response;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.NewIssue;
import org.testng.Assert;
import org.testng.annotations.Test;
import testbase.TestBase;
import utilities.ResponseUtils;

public class API005 extends TestBase {
    Issue deleteIssueMethods = new Issue();
    String createdIssueID = "";

    @Test(testName = "AP005", description = "Verify that a successful response is given when user delete an exist issue")
    public void AP005(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION]: Create a new issue");
        Response createIssueResponse = deleteIssueMethods.createIssue(NewIssue.NEW_ISSUE);

        createdIssueID = ResponseUtils.getValueFromBody(createIssueResponse, "id");
        ExtentReportManager.stepNode("[CREATED ISSUE]: " + createdIssueID, createdIssueID);

        ExtentReportManager.logMessage(Status.INFO, "Step 1: Delete an exist issue: " + createdIssueID);
        Response deleteIssueResponse = deleteIssueMethods.deleteIssue(createdIssueID);

        ExtentReportManager.logVerifyStep("[VERIFY] - Verify that response code is 204");
        Assert.assertEquals(deleteIssueResponse.getStatusCode(), StatusCode.CODE_204.getStatusCode());

    }

}
