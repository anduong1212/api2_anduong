package jiraapi.testcases.issuecomment.addcomment;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import io.restassured.response.Response;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.NewIssue;
import jiraapi.controller.issuecomment.IssueComment;
import jiraapi.controller.issuecomment.dataobject.NewComment;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class API007 extends TestBase {

    Issue issue = new Issue();
    IssueComment issueComment = new IssueComment();
    SoftAssert softAssert = new SoftAssert();
    String createdIssueId;
    String addedCommentId;

    @Test(testName = "API007", description = "Verify that successful response is given when user add new comment to exist issue")
    public void API007(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Create new issue ");
        Response createdIssueRes = issue.createIssue(NewIssue.NEW_ISSUE);

        createdIssueId = ResponseUtils.getValueFromBody(createdIssueRes, "id");
        ExtentReportManager.stepJsonNode("","CREATED new issue: " + createdIssueId, createdIssueRes.asString());

        ExtentReportManager.logMessage(Status.INFO, "Step 1: Add new comment to issue: " + createdIssueId);
        Response addedCommentRes = issueComment.addNewComment(createdIssueId, NewComment.NEW_COMMENT);
        ExtentReportManager.stepJsonNode("","ADDED Comment to Issue: " + createdIssueId, addedCommentRes.asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that status code is 201", String.valueOf(addedCommentRes.getStatusCode()));
        softAssert.assertEquals(addedCommentRes.getStatusCode(), StatusCode.CODE_201.getStatusCode());

        addedCommentId = ResponseUtils.getValueFromBody(addedCommentRes, "id");
        ExtentReportManager.stepNodeVerify("Step 3: Verify that comment id is returned", addedCommentId);
        softAssert.assertFalse(ResponseUtils.isValueIsNullOrEmpty(addedCommentRes, "id"));
        softAssert.assertAll();


        ExtentReportManager.stepNode("[POST-CONDITION] Delete new added comment","", addedCommentId);
        issueComment.deleteComment(createdIssueId, addedCommentId);

        ExtentReportManager.stepNode("[POST-CONDITION] Delete created issue","", createdIssueId);
        issue.deleteIssue(createdIssueId);
    }
}
