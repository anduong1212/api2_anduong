package controller.jiraapi;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.apibase.SpecBuilder;
import common.logger.Log;
import common.propmanager.PropertiesManager;
import constant.Constants;
import controller.jiraapi.dataobjects.NewIssue;
import io.restassured.response.Response;
import utilities.JacksonObjectUtils;

public class Issue {
    private String issueIdOrKey;
    private String issueSummary;
    private String issueType;
    private String project_key;
    private String description;

    public Issue(NewIssue newIssue){
        this.issueIdOrKey = newIssue.getIssueIdOrKey();
        this.issueSummary = newIssue.getIssueSummary();
        this.issueType = newIssue.getIssueType();
        this.project_key = newIssue.getProjectKey();
        this.description = newIssue.getDescription();
    }

    private String setIssueEndPoint(String issueIdOrKey){
        return String.format(Constants.ISSUE_ENDPOINT, issueIdOrKey);
    }

    private String setSearchEndPoint(String project_key){
        return String.format(Constants.SEARCH_ENDPOINT, project_key);
    }

    public Object payloadCreateIssue(){
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode createIssuePayload = jsonNodeFactory.objectNode();
        {
            ObjectNode fields = createIssuePayload.putObject("fields");
            {
                if (this.issueSummary != null) fields.put("summary", NewIssue.NEW_ISSUE.getIssueSummary());
                if (this.issueType != null){
                    ObjectNode issueType = fields.putObject("issuetype");
                    {
                        issueType.put("name", NewIssue.NEW_ISSUE.getIssueType());
                    }
                }

                if(this.project_key != null){
                    ObjectNode projectKey = fields.putObject("project");
                    {
                        projectKey.put("key", NewIssue.NEW_ISSUE.getProjectKey());
                    }
                }

                if(this.description != null){
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
                                content0_para.put("text", NewIssue.NEW_ISSUE.getDescription());
                                content0_para.put("type", "text");
                            }
                        }
                    }
                }

            }

        }
        JacksonObjectUtils.connectJacksonObjectMapperToUnirest();
        return createIssuePayload;
    }

    public synchronized Response createIssue() {
        String issueEndPoint = setIssueEndPoint(NewIssue.NEW_ISSUE.getIssueIdOrKey());
        Log.info("[CREATING AN ISSUE] - using these endpoint: " + issueEndPoint);

        return SpecBuilder.post(issueEndPoint, PropertiesManager.getDefaultPropValue("access_token"), payloadCreateIssue());
    }

    public synchronized Response getAllIssuesFromProject(){
        String searchEndPoint = setSearchEndPoint("ADAPI");
        Log.info("[GETTING ALL ISSUE] - using these endpoint: " + searchEndPoint);

        return SpecBuilder.get(searchEndPoint, PropertiesManager.getDefaultPropValue("access_token"));
    }

}
