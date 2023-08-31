package controller.jiraapi.dataobjects;

import utilities.DateTimeUtils;
import utilities.JsonFormatter;

public enum NewIssue {
    NEW_ISSUE(String.format(JsonFormatter.getFieldFromTemplate("newIssue", "summary"), DateTimeUtils.getCurrentDateTime())
            ,JsonFormatter.getFieldFromTemplate("newIssue", "type")
            ,JsonFormatter.getFieldFromTemplate("newIssue", "projectKey")
            ,String.format(JsonFormatter.getFieldFromTemplate("newIssue", "description"), DateTimeUtils.getCurrentDate()));
    private final String issueSummary;
    private final String issueType;
    private final String projectKey;
    private final String description;

    private NewIssue(String issueSummary, String issueType, String projectKey, String description) {
        this.issueSummary = issueSummary;
        this.issueType = issueType;
        this.projectKey = projectKey;
        this.description = description;
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
}
