package jiraapi.testcases.issue.getissue;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.DataTransfer;
import jiraapi.controller.issue.dataobjects.NewIssue;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class API001 extends TestBase {
    Issue issue = new Issue();
    SoftAssert softAssert = new SoftAssert();
    DataTransfer createdIssueDataTrans = new DataTransfer();
    String createdIssueID = "";

    @Test(testName = "AP001", description = "Verify that success response when user get an exist issue")
    public void API001(){
        ExtentReportManager.logStep("", "[PRE-CONDITION]: Create issue with valid field");
        Response newIssueResponse = issue.createIssue(NewIssue.NEW_ISSUE);
        createdIssueID = ResponseUtils.getValueFromBody(newIssueResponse, "id");
        ExtentReportManager.stepJsonNode("", "CREATED issue: " + createdIssueID, newIssueResponse.asPrettyString());

        ExtentReportManager.stepNode("1", "Get an exist issue from current project", createdIssueID);
        Response getIssueRes = issue.getIssue(createdIssueID);
        ExtentReportManager.stepJsonNode("", "GET issue: " + createdIssueID, getIssueRes.asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that status code is 200", String.valueOf(getIssueRes.getStatusCode()));
        softAssert.assertEquals(getIssueRes.getStatusCode(), StatusCode.CODE_200.getStatusCode());

        ExtentReportManager.stepNodeVerify("Step 3: Verify that issue summary is correct", ResponseUtils.getValueFromBody(getIssueRes,"fields.summary"));
        softAssert.assertEquals(ResponseUtils.getValueFromBody(getIssueRes,"fields.summary"), NewIssue.NEW_ISSUE.getIssueSummary());
        softAssert.assertAll();

        ExtentReportManager.stepNode("","[POST-CONDITION] Delete created issue", createdIssueID);
        issue.deleteIssue(createdIssueID);
    }

}
