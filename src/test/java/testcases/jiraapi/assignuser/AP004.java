package testcases.jiraapi.assignuser;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import controller.jiraapi.Issue;
import controller.jiraapi.dataobjects.Assignee;
import controller.jiraapi.dataobjects.NewIssue;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class AP004 extends TestBase {
    Issue assignIssueMethods = new Issue();

    String newIssueID = "";

    @Test(testName = "AP004", description = "Verify that an successfull response is given when user assign an user to specific ticket")
    public void AP004(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION]: Create new issue with valid fields");
        Response newIssueResponse = assignIssueMethods.createIssue(NewIssue.NEW_ISSUE);

        newIssueID = ResponseUtils.getValueFromBody(newIssueResponse, "id");

        ExtentReportManager.stepNode("Step 1: Assign User to the ticket", "User ID" + Assignee.ASSIGNEE.getAccountId());
        Response assignIssueResponse= assignIssueMethods.assignIssue(newIssueID, Assignee.ASSIGNEE);

        ExtentReportManager.logVerifyStep("[VERIFY] - Verify that the status code is 204");
        Assert.assertEquals(assignIssueResponse.getStatusCode(), StatusCode.CODE_204.getStatusCode());

        ExtentReportManager.stepNode("[POST-CONDITION]: Delete the created issue", "IssueID: " + newIssueID);
        assignIssueMethods.deleteIssue(newIssueID);
    }
}
