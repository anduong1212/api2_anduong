package jiraapi.controller.issuecomment.dataobject;

import utilities.DateTimeUtils;
import utilities.JsonHelper;

public enum UpdateComment {
    UPDATE_COMMENT(JsonHelper.formatJsonValue("updateComment","body", DateTimeUtils.getCurrentDateTime()));

    private String updateCommentBody;
    UpdateComment(String updateCommentBody){
        this.updateCommentBody = updateCommentBody;
    }

    public String getUpdateCommentBody(){
        return updateCommentBody;
    }
}
