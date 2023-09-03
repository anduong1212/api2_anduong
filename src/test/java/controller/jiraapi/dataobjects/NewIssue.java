package controller.jiraapi.dataobjects;

import controller.jiraapi.issueenum.IssueType;
import utilities.DateTimeUtils;
import utilities.JsonHelper;

public enum NewIssue {
    NEW_ISSUE(JsonHelper.formatJsonValue("newIssue", "summary", DateTimeUtils.getCurrentDate())
            , IssueType.getIssueTypeFromJson("newIssue")
            , JsonHelper.getFieldFromTemplate("newIssue", "projectKey")
            , JsonHelper.formatJsonValue("newIssue", "description", DateTimeUtils.getCurrentDate())
    , JsonHelper.getFieldFromTemplate("newIssue","issueIdOrKey")),

    NEW_ISSUE_WITHOUT_SUMMARY(null,
              JsonHelper.getFieldFromTemplate("newIssue", "type")
            , IssueType.getIssueTypeFromJson("newIssue")
            , JsonHelper.formatJsonValue("newIssue", "description", DateTimeUtils.getCurrentDate())
            , JsonHelper.getFieldFromTemplate("newIssue","issueIdOrKey"));
    private final String issueSummary;
    private final String issueType;
    private final String projectKey;
    private final String description;
    private String issueIdOrKey = "";

    private NewIssue(String issueSummary, String issueType, String projectKey, String description, String issueIdOrKey) {
        this.issueSummary = issueSummary;
        this.issueType = issueType;
        this.projectKey = projectKey;
        this.description = description;
        this.issueIdOrKey = issueIdOrKey;
    }

    public String getIssueSummary() {
        return issueSummary;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueIdOrKey(){ return  issueIdOrKey; }
}
