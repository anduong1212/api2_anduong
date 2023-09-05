package jiraapi.controller.issue.dataobjects;

import io.restassured.response.ResponseBody;

import java.util.Map;

public class DataTransfer {
    private Map<String, Object> responseBody;

    public Map<String, Object> getResponseBody(){
        return this.responseBody;
    }

    public void setResponseBody(ResponseBody responseBody){
        this.responseBody = (Map<String, Object>) responseBody.as(Object.class);
    }
}
