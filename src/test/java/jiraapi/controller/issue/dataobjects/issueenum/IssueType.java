package jiraapi.controller.issue.dataobjects.issueenum;

import utilities.JsonHelper;

public enum IssueType {
    TASK("Task"),
    BUG("Bug");
    private String issueType;

    IssueType(String issueType){
        this.issueType = issueType;
    }

    public static IssueType getIssueType(String value) {
        for(IssueType iT : values()){
                if(iT.getValue().equalsIgnoreCase(value))
                    return iT;
        }
        return null;
    }

    public static String getIssueTypeFromJson(String templateFile) {
            return getIssueType(JsonHelper.getFieldFromTemplate(templateFile, "type")).getValue();
    }

    public String getValue(){
        return this.issueType;
    }
}
