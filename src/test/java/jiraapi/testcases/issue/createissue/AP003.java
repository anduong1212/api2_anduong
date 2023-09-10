package jiraapi.testcases.issue.createissue;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.NewIssue;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class AP003 extends TestBase {
    Issue createIssueMethods = new Issue();
    SoftAssert softAssert = new SoftAssert();

    String createdIssueID = "";

    @Test(testName = "AP003", description = "Verify that an successful response is given when user create issue with valid fields")
    public void AP003(){
        ExtentReportManager.logMessage(Status.INFO, "Step 1: Create an issue with valid");
        Response createIssueResponse = createIssueMethods.createIssue(NewIssue.NEW_ISSUE);

        ExtentReportManager.stepJsonNode("[CREATED - ISSUE] " + ResponseUtils.getValueFromBody(createIssueResponse, "key"), createIssueResponse.getBody().asString());
        createdIssueID = ResponseUtils.getValueFromBody(createIssueResponse, "id");

        ExtentReportManager.logVerifyStep("[VERIFY] - Verify that the response status code is 201");
        softAssert.assertEquals(createIssueResponse.getStatusCode(), StatusCode.CODE_201.getStatusCode());

        ExtentReportManager.logVerifyStep("[VERIFY] - Verify that created issue ID and Key is returned");
        softAssert.assertFalse(ResponseUtils.isValueIsNullOrEmpty(createIssueResponse, "id"));
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION]: Delete the created issue", "IssueID: " + createdIssueID);
        createIssueMethods.deleteIssue(createdIssueID);

        ExtentReportManager.("1", "Create an issue with valid field");



    }
}
