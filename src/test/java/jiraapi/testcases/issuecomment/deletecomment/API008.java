package jiraapi.testcases.issuecomment.deletecomment;

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

public class API008 extends TestBase {
    Issue issue = new Issue();
    IssueComment issueComment = new IssueComment();
    SoftAssert softAssert = new SoftAssert();
    String createdIssueId;
    String addedCommentId;

    @Test(testName = "API008", description = "Verify that successful response response is given when user delete an existing comment")
    public void API008(){
        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Create new issue ");
        Response createdIssueRes = issue.createIssue(NewIssue.NEW_ISSUE);
        createdIssueId = ResponseUtils.getValueFromBody(createdIssueRes, "id");
        ExtentReportManager.stepJsonNode("CREATED new issue: " + createdIssueId, createdIssueRes.asPrettyString());

        ExtentReportManager.logMessage(Status.INFO, "[PRE-CONDITION] Add new comment to issue: " + createdIssueId);
        Response addedCommentRes = issueComment.addNewComment(createdIssueId, NewComment.NEW_COMMENT);
        addedCommentId = ResponseUtils.getValueFromBody(addedCommentRes,"id");
        ExtentReportManager.stepJsonNode("ADDED Comment to Issue: " + createdIssueId, addedCommentRes.asPrettyString());

        ExtentReportManager.stepNode("Step 1: Delete a comment from exist issue", addedCommentId);
        Response deletedCommentRes = issueComment.deleteComment(createdIssueId, addedCommentId);

        ExtentReportManager.stepNodeVerify("Step 2: Verify that status code is 204", String.valueOf(deletedCommentRes.getStatusCode()));
        softAssert.assertEquals(deletedCommentRes.getStatusCode(), StatusCode.CODE_204.getStatusCode());

        Response getAllCommentRes = issueComment.getAllComments(createdIssueId);
        ExtentReportManager.stepNodeVerify("Step 3: Verify that deleted comment is not exist", getAllCommentRes.getBody().asPrettyString());
        softAssert.assertFalse(ResponseUtils.isValueContainInListRes(getAllCommentRes.getBody(), "comments", addedCommentId));
        softAssert.assertAll();

        ExtentReportManager.stepNode("[POST-CONDITION] Delete created issue", createdIssueId);
        issue.deleteIssue(createdIssueId);

    }
}
