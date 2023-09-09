package jiraapi.controller.issuecomment.dataobject;

import utilities.DateTimeUtils;
import utilities.JsonHelper;

public enum NewComment {
    NEW_COMMENT(JsonHelper.formatJsonValue("newComment", "body", DateTimeUtils.getCurrentDateTime())),
    NEW_COMMENT_WITH_BLANK_BODY("");

    private final String commentBody;

    NewComment(String commentBody){
        this.commentBody = commentBody;
    }

    public String getCommentBody(){
        return this.commentBody;
    }

}
