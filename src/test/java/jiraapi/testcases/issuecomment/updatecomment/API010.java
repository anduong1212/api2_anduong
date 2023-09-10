package jiraapi.testcases.issuecomment.updatecomment;

import com.aventstack.extentreports.Status;
import common.apibase.StatusCode;
import common.report.ExtentReportManager;
import io.restassured.response.Response;
import jiraapi.controller.issue.Issue;
import jiraapi.controller.issue.dataobjects.NewIssue;
import jiraapi.controller.issuecomment.IssueComment;
import jiraapi.controller.issuecomment.dataobject.NewComment;
import jiraapi.controller.issuecomment.dataobject.UpdateComment;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testbase.TestBase;
import utilities.ResponseUtils;

public class API010 extends TestBase {
    Issue issue = new Issue();
    IssueComment issueComment = new IssueComment();
    SoftAssert softAssert = new SoftAssert();
    String createdIssueId;
    String addedCommentId;
    String commentBody;

    @Test(testName = "API010", description = "Verify that successful response is given when user update an exist comment with valid fields")
    public void API010(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Create new issue ");
        Response createdIssueRes = issue.createIssue(NewIssue.NEW_ISSUE);
        createdIssueId = ResponseUtils.getValueFromBody(createdIssueRes, "id");
        ExtentReportManager.stepJsonNode("","CREATED new issue: " + createdIssueId, createdIssueRes.asPrettyString());

        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Add new comment to issue: " + createdIssueId);
        Response addedCommentRes = issueComment.addNewComment(createdIssueId, NewComment.NEW_COMMENT);
        addedCommentId = ResponseUtils.getValueFromBody(addedCommentRes,"id");
        ExtentReportManager.stepJsonNode("","ADDED Comment to Issue: " + createdIssueId, addedCommentRes.asPrettyString());

        ExtentReportManager.stepNode("1", "Update comment from exist issue", addedCommentId);
        Response updateCommentRes = issueComment.updateComment(createdIssueId, addedCommentId, UpdateComment.UPDATE_COMMENT);
        ExtentReportManager.stepJsonNode("","UPDATE comment from issue", updateCommentRes.getBody().asPrettyString());

        ExtentReportManager.stepNodeVerify("Step 2: Verify that status code is 200", String.valueOf(updateCommentRes.getStatusCode()));
        softAssert.assertEquals(updateCommentRes.getStatusCode(), StatusCode.CODE_200.getStatusCode());

        commentBody = ResponseUtils.getValueFromBody(updateCommentRes, "body.content[0].content[0].text");
        ExtentReportManager.stepNodeVerify("Step 3: Verify that comment body is correct", commentBody);
        softAssert.assertEquals(commentBody, UpdateComment.UPDATE_COMMENT.getUpdateCommentBody());
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION]", "Delete new added comment", addedCommentId);
        issueComment.deleteComment(createdIssueId, addedCommentId);

        ExtentReportManager.stepNode("[POST-CONDITION]", "Delete created issue", createdIssueId);
        issue.deleteIssue(createdIssueId);
    }

}
