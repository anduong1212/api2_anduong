package common.apibase;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_204(204, ""),
    CODE_400(400, "Missing required field: name"),
    CODE_400_BLANK_SUMMARY(400, "You must specify a summary of the issue."),
    CODE_400_NULL_SUMMARY(400, "Operation value must be a string"),
    CODE_401(401, "Invalid access token"),
    CODE_404_INVALID_ACCESS_TOKEN(404, "Invalid access token"),
    CODE_404_ISSUE_DOES_NOT_EXIST(404, "Issue does not exist or you do not have permission to see it.");

    private final int code;
    private final String message;

    StatusCode(int code, String errorMessage){
        this.code = code;
        this.message = errorMessage;
    }

    public int getStatusCode(){
        return this.code;
    }

    public String getErrorMessage(){
        return this.message;
    }


}
