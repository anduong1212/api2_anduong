package jiraapi.testcases.issue.getissue;

import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import jiraapi.controller.ErrorMessages;
import jiraapi.controller.issue.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.FakerUtils;
import utilities.ResponseUtils;

public class API002 extends TestBase {
    Issue issue = new Issue();
    SoftAssert softAssert = new SoftAssert();

    String latestIssueID;
    String randomIssueID;

    @Test(testName = "API002", description = "Verify that an unsuccessful response is given when user get an un-exist issue id")
    public void API002(){
        ExtentReportManager.logStep("PRE-CONDITION", "Get latest issue id");
        Response latestIssueRes = issue.getAllIssuesFromProject();
        latestIssueID = ResponseUtils.getResponseAsList(latestIssueRes.getBody(),"issues").get(0).get("id").toString();
        randomIssueID = String.valueOf(FakerUtils.generateRandomIssueID(latestIssueID));

        ExtentReportManager.logStep("1", "Get non-exist issue" + randomIssueID);
        Response getIssueRes = issue.getIssue(randomIssueID);
        ExtentReportManager.stepJsonNode("", "GET NON-EXIST ISSUE: " + randomIssueID, getIssueRes.asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that response code is 404", String.valueOf(getIssueRes.getStatusCode()));
        softAssert.assertEquals(getIssueRes.getStatusCode(), StatusCode.CODE_404.getStatusCode());

        ExtentReportManager.stepNodeVerify("Step 3: Verify that error message is returned", ResponseUtils.getValueFromBody(getIssueRes, "errorMessages[0]"));
        softAssert.assertEquals(ResponseUtils.getValueFromBody(getIssueRes, "errorMessages[0]"), ErrorMessages.ISSUE_DOES_NOT_EXIST.toString());
        softAssert.assertAll();

    }
}
