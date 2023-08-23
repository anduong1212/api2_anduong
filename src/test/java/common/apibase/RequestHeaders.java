package common.apibase;

public enum RequestHeaders {
    AUTHORIZATION("Authorization"),
    ACCEPT("Accept"),
    CONTENT_TYPE("Content-Type");

    private String requestHeaderValue;

    private RequestHeaders(String requestHeaderValue){
        this.requestHeaderValue = requestHeaderValue;
    }

    @Override
    public String toString(){
        return this.requestHeaderValue;
    }

}
