package jiraapi.controller.issue;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.apibase.SpecBuilder;
import common.logger.Log;
import common.propmanager.PropertiesManager;
import constant.Constants;
import jiraapi.controller.issue.dataobjects.Assignee;
import jiraapi.controller.issue.dataobjects.NewIssue;
import io.restassured.response.Response;

public class Issue {

    private String setIssueEndPoint(String issueIdOrKey){
        return String.format(Constants.ISSUE_ENDPOINT, issueIdOrKey);
    }

    private String setSearchEndPoint(String project_key){
        return String.format(Constants.SEARCH_ENDPOINT, project_key);
    }

    public Object payloadCreateIssue(NewIssue newIssue){
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode createIssuePayload = jsonNodeFactory.objectNode();
        {
            ObjectNode fields = createIssuePayload.putObject("fields");
            {
                if (newIssue.getIssueSummary() != "") fields.put("summary", newIssue.getIssueSummary());
                if (newIssue.getIssueType() != ""){
                    ObjectNode issueType = fields.putObject("issuetype");
                    {
                        issueType.put("name", newIssue.getIssueType());
                    }
                }

                if(newIssue.getProjectKey() != ""){
                    ObjectNode projectKey = fields.putObject("project");
                    {
                        projectKey.put("key", newIssue.getProjectKey());
                    }
                }

                if(newIssue.getDescription() != ""){
                    ObjectNode description = fields.putObject("description");
                    {
                        description.put("type", "doc");
                        description.put("version", 1);

                        ArrayNode content = description.putArray("content");
                        ObjectNode content0 = content.addObject();
                        {
                            content0.put("type","paragraph");
                            ArrayNode content_para = content0.putArray("content");
                            ObjectNode content0_para = content_para.addObject();
                            {
                                content0_para.put("text", newIssue.getDescription());
                                content0_para.put("type", "text");
                            }
                        }
                    }
                }

            }

        }
        return createIssuePayload;
    }

    public Object payloadAssignAnUser(Assignee assignee){
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode assignAnUserPayload = jsonNodeFactory.objectNode();
        {
            assignAnUserPayload.put("accountId", assignee.getAccountId());
        }

        return assignAnUserPayload;
    }

    public synchronized Response createIssue(NewIssue newIssue) {
        String issueEndPoint = setIssueEndPoint(newIssue.getIssueIdOrKey());
        Log.info("[CREATING AN ISSUE] - using these endpoint: " + issueEndPoint);

        return SpecBuilder.post(issueEndPoint, PropertiesManager.getDefaultPropValue("access_token"), payloadCreateIssue(newIssue));
    }

    public synchronized Response deleteIssue(String issueIdOrKey) {
        String issueEndPoint = setIssueEndPoint(issueIdOrKey);
        Log.info("[DELETING AN ISSUE] - using these endpoint: " + issueEndPoint);

        return SpecBuilder.delete(issueEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }

    public synchronized Response getIssue(String issueIdOrKey){
        String issueEndPoint = setIssueEndPoint(issueIdOrKey);
        Log.info("[GETTING AN ISSUE] - using these endpoint: " + issueEndPoint);

        return SpecBuilder.get(issueEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }

    public synchronized Response getAllIssuesFromProject(){
        String searchEndPoint = setSearchEndPoint("ADAPI");
        Log.info("[GETTING ALL ISSUE] - using these endpoint: " + searchEndPoint);

        return SpecBuilder.get(searchEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }

    public synchronized Response assignIssue(String issueIdOrKey, Assignee assignee){
        String assignIssueEndPoint = setIssueEndPoint(issueIdOrKey + "/assignee");
        Log.info("[ASSIGN AN USER] - using these endpoint: " + assignIssueEndPoint);

        return SpecBuilder.put(assignIssueEndPoint, PropertiesManager.getDefaultPropValue("access_token"), payloadAssignAnUser(Assignee.ASSIGNEE));
    }

}
