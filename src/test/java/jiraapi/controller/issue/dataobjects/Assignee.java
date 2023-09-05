package jiraapi.controller.issue.dataobjects;

import utilities.JsonHelper;

public enum Assignee {
    ASSIGNEE(JsonHelper.getFieldFromTemplate("assignee", "accountId"));

    private final String accountId;

    Assignee(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId(){
        return accountId;
    }
}
