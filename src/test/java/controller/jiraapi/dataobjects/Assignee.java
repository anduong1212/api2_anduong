package controller.jiraapi.dataobjects;

import utilities.JsonHelper;

public enum Assignee {
    ASSIGNEE("712020:1e7fbba3-995d-4129-b6c6-ca975c8fdb23");

    private final String accountId;

    Assignee(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId(){
        return accountId;
    }
}
