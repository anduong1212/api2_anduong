package jiraapi.controller.issuecomment;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.apibase.SpecBuilder;
import common.logger.Log;
import common.propmanager.PropertiesManager;
import constant.Constants;
import io.restassured.response.Response;
import jiraapi.controller.issuecomment.dataobject.NewComment;

public class IssueComment {
    private String setIssueCommentEndPoint(String issueIdOrKey, String commentID){
        return String.format(Constants.ISSUE_ENDPOINT, issueIdOrKey + "/comment/" + commentID);
    }

    private String setIssueEndPoint(String issueIdOrKey){
        return String.format(Constants.ISSUE_ENDPOINT, issueIdOrKey);
    }

    private Object commentPayload(NewComment newComment){
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode issueCommentPayload = jsonNodeFactory.objectNode();
        {
            ObjectNode body = issueCommentPayload.putObject("body");
            {
                ArrayNode content = body.putArray("content");
                ObjectNode content0 = content.addObject();
                {
                    content0.put("type", "paragraph");
                    ArrayNode content_para = content0.putArray("content");
                    ObjectNode content0_para = content_para.addObject();
                    {
                        content0_para.put("text", newComment.getCommentBody());
                        content0_para.put("type", "text");
                    }
                }

                body.put("type","doc");
                body.put("version",1);
            }
        }

        issueCommentPayload.toPrettyString();

        return issueCommentPayload;
    }

    public synchronized Response addNewComment(String issueIdOrKey, NewComment newComment){
        String commentEndPoint = setIssueEndPoint(issueIdOrKey + "/comment");
        Log.info("[ADD NEW COMMENT FOR ISSUE: " + issueIdOrKey + "] - using these endpoint" + commentEndPoint);

        return SpecBuilder.post(commentEndPoint, PropertiesManager.getDefaultPropValue("access_token"), commentPayload(newComment));
    }

    public synchronized Response deleteComment(String issueIdOrKey, String commentId){
        String commentEndPoint = setIssueCommentEndPoint(issueIdOrKey, commentId);
        Log.info("[DELETE COMMENT: " + commentId + "] - using these endpoint: " + commentEndPoint);

        return SpecBuilder.delete(commentEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }

    public synchronized Response getComment(String issueIdOrKey, String commentId){
        String commentEndPoint = setIssueCommentEndPoint(issueIdOrKey, commentId);
        Log.info("[DELETE COMMENT: " + commentId + "] - using these endpoint: " + commentEndPoint);

        return SpecBuilder.get(commentEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }

    public synchronized Response getAllComments(String issueIdOrKey){
        String commentEndPoint = setIssueEndPoint(issueIdOrKey + "/comment");
        Log.info("[GET ALL COMMENT FROM ISSUE:" + issueIdOrKey + "] - using these endpoint: " + commentEndPoint);

        return SpecBuilder.get(commentEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }


}
