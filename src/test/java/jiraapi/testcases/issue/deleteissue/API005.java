package jiraapi.testcases.issue.deleteissue;

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
import utilities.ResponseUtils;

public class API005 extends TestBase {
    Issue issue = new Issue();
    SoftAssert softAssert = new SoftAssert();
    String createdIssueID = "";

    @Test(testName = "AP005", description = "Verify that a successful response is given when user delete an exist issue")
    public void AP005(){
        ExtentReportManager.logStep("", "[PRE-CONDITION]: Create issue with valid field");
        Response newIssueResponse = issue.createIssue(NewIssue.NEW_ISSUE);
        createdIssueID = ResponseUtils.getValueFromBody(newIssueResponse, "id");
        ExtentReportManager.stepJsonNode("", "CREATED issue: " + createdIssueID, newIssueResponse.asPrettyString());

        ExtentReportManager.stepNode("1", "Delete an existing issue", createdIssueID);
        Response deleteIssueRes = issue.deleteIssue(createdIssueID);
        ExtentReportManager.stepNode("", "DELETE issue: " + createdIssueID, createdIssueID);

        ExtentReportManager.stepNodeVerify("Step 2: Verify that the status code is 204", String.valueOf(deleteIssueRes.getStatusCode()));
        softAssert.assertEquals(deleteIssueRes.getStatusCode(), StatusCode.CODE_204.getStatusCode());

        ExtentReportManager.stepNodeVerify("Step 3: Verify that issue is not existed in current project", createdIssueID);
        Response getAllIssueRes = issue.getAllIssuesFromProject();
        ExtentReportManager.stepJsonNode("", "GET ALL ISSUE from current project", getAllIssueRes.asPrettyString());
        softAssert.assertFalse(ResponseUtils.isValueContainInListRes(getAllIssueRes.getBody(), "issues", createdIssueID));
        softAssert.assertAll();

    }

}
