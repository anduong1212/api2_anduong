package jiraapi.testcases.issue.assignuser;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.Assignee;
import jiraapi.controller.issue.dataobjects.NewIssue;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class AP004 extends TestBase {
    Issue assignIssueMethods = new Issue();
    SoftAssert softAssert = new SoftAssert();
    String newIssueID = "";

    @Test(testName = "AP004", description = "Verify that an successful response is given when user assign an user to specific ticket")
    public void AP004(){
        ExtentReportManager.logStep("", "[PRE-CONDITION]: Create issue with valid field");
        Response newIssueResponse = assignIssueMethods.createIssue(NewIssue.NEW_ISSUE);
        newIssueID = ResponseUtils.getValueFromBody(newIssueResponse, "id");
        ExtentReportManager.stepJsonNode("", "CREATED issue: " + newIssueID, newIssueResponse.asPrettyString());

        ExtentReportManager.stepNode("1", "Assign an user to ticket with account ID", Assignee.ASSIGNEE.getAccountId());
        Response assignIssueRes = assignIssueMethods.assignIssue(newIssueID, Assignee.ASSIGNEE);
        ExtentReportManager.stepJsonNode("", "ASSIGN user id to issue with account id: " + Assignee.ASSIGNEE.getAccountId(), assignIssueRes.asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that the status code is 204", String.valueOf(assignIssueRes.getStatusCode()));
        softAssert.assertEquals(assignIssueRes.getStatusCode(), StatusCode.CODE_204.getStatusCode());

        ExtentReportManager.stepNodeVerify("Step 3: Verify that account id has been added to assigned issue", Assignee.ASSIGNEE.getAccountId());
        Response getIssueRes = assignIssueMethods.getIssue(newIssueID);
        softAssert.assertEquals(ResponseUtils.getValueFromBody(getIssueRes, "fields.assignee.accountId"), Assignee.ASSIGNEE.getAccountId());
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION] Delete created issue","", newIssueID);
        assignIssueMethods.deleteIssue(newIssueID);

    }
}
