package jiraapi.testcases.issue.createissue;

import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.NewIssue;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class API003 extends TestBase {
    Issue createIssueMethods = new Issue();
    SoftAssert softAssert = new SoftAssert();
    String createdIssueID = "";

    @Test(testName = "AP003", description = "Verify that an successful response is given when user create issue with valid fields")
    public void AP003(){

        ExtentReportManager.logStep("1", "Create issue with valid fields");
        Response createIssueResponse = createIssueMethods.createIssue(NewIssue.NEW_ISSUE);
        createdIssueID = ResponseUtils.getValueFromBody(createIssueResponse, "id");
        ExtentReportManager.stepJsonNode("", "CREATED issue: " + createdIssueID, createIssueResponse.asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify the status code is 201", String.valueOf(createIssueResponse.getStatusCode()));
        softAssert.assertEquals(createIssueResponse.getStatusCode(), StatusCode.CODE_201.getStatusCode());

        ExtentReportManager.stepNodeVerify("Step 3: Verify that issue has been exist in current project", createdIssueID);
        Response getIssueRes = createIssueMethods.getIssue(createdIssueID);
        ExtentReportManager.stepJsonNode("", "GET new created issue from current project" + createdIssueID, getIssueRes.asPrettyString());
        softAssert.assertEquals(ResponseUtils.getValueFromBody(getIssueRes,"id"), createdIssueID);

        ExtentReportManager.stepNode("4", "Verify that issue summary is correct", NewIssue.NEW_ISSUE.getIssueSummary());
        softAssert.assertEquals(ResponseUtils.getValueFromBody(getIssueRes, "fields.summary"), NewIssue.NEW_ISSUE.getIssueSummary());
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION] Delete created issue","", createdIssueID);
        createIssueMethods.deleteIssue(createdIssueID);

    }
}
