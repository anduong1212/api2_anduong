package jiraapi.testcases.issuecomment.getcomment;

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

public class API009 extends TestBase {
    Issue issue = new Issue();
    IssueComment issueComment = new IssueComment();
    SoftAssert softAssert = new SoftAssert();
    String createdIssueId;
    String addedCommentId;
    String getCommentId;
    String commentBody;
    @Test(testName = "API009", description = "Verify that successful response is given when user get an exist comment from an exist issue")
    public void API009(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Create new issue ");
        Response createdIssueRes = issue.createIssue(NewIssue.NEW_ISSUE);
        createdIssueId = ResponseUtils.getValueFromBody(createdIssueRes, "id");
        ExtentReportManager.stepJsonNode("CREATED new issue: " + createdIssueId, createdIssueRes.asPrettyString());

        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Add new comment to issue: " + createdIssueId);
        Response addedCommentRes = issueComment.addNewComment(createdIssueId, NewComment.NEW_COMMENT);
        addedCommentId = ResponseUtils.getValueFromBody(addedCommentRes,"id");
        ExtentReportManager.stepJsonNode("ADDED Comment to Issue: " + createdIssueId, addedCommentRes.asPrettyString());

        ExtentReportManager.stepNode("Step 1: Get a comment from exist issue", addedCommentId);
        Response getCommentRes = issueComment.getComment(createdIssueId, addedCommentId);
        ExtentReportManager.stepNode("GET comment from issue", getCommentRes.getBody().asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that status code is 200", String.valueOf(getCommentRes.getStatusCode()));
        softAssert.assertEquals(getCommentRes.getStatusCode(), StatusCode.CODE_200.getStatusCode());

        getCommentId = ResponseUtils.getValueFromBody(getCommentRes, "id");
        ExtentReportManager.stepNodeVerify("Step 3: Verify that comment id is returned", getCommentId);
        softAssert.assertFalse(ResponseUtils.isValueIsNullOrEmpty(addedCommentRes, "id"));

        commentBody = ResponseUtils.getValueFromBody(getCommentRes, "body.content[0].content[0].text");
        ExtentReportManager.stepNodeVerify("Step 4: Verify that comment body is correct", commentBody);
        softAssert.assertEquals(commentBody, NewComment.NEW_COMMENT.getCommentBody());
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION] Delete new added comment", addedCommentId);
        issueComment.deleteComment(createdIssueId, addedCommentId);

        ExtentReportManager.stepNode("[POST-CONDITION] Delete created issue", createdIssueId);
        issue.deleteIssue(createdIssueId);
    }
}
