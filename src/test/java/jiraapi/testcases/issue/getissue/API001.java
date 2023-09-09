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
    Issue createIssueMethods = new Issue();
    SoftAssert softAssert = new SoftAssert();

    DataTransfer createdIssueDataTrans = new DataTransfer();
    String createdIssueID = "";

    @Test(testName = "AP001", description = "Verify that success response when user get an exist issue")
    public void API001(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION]: Create a new issue");
        Response createIssueResponse = createIssueMethods.createIssue(NewIssue.NEW_ISSUE);
        ExtentReportManager.stepJsonNode("[CREATED] - ISSUE IS CREATED", createIssueResponse.asString());

        createdIssueDataTrans.setResponseBody(createIssueResponse.getBody());
        createdIssueID = createdIssueDataTrans.getResponseBody().get("id").toString();
        ExtentReportManager.stepNode("STEP 1: Get an exist issue", "Issue ID: " + createdIssueID);
        Response getIssueResponse = createIssueMethods.getIssue(createdIssueID);

        softAssert.assertEquals(getIssueResponse.getStatusCode(), StatusCode.CODE_200.getStatusCode());
        softAssert.assertEquals(ResponseUtils.getValueFromBody(getIssueResponse, "id"), createdIssueID);
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION]: Delete the created issue", "IssueID: " + createdIssueID);
        createIssueMethods.deleteIssue(createdIssueID);
    }

}
