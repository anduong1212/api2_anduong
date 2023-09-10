package common.apibase;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_204(204, ""),
    CODE_400(400, ""),
    CODE_401(401, ""),
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
