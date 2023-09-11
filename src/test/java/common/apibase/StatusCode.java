package common.apibase;

public enum StatusCode {
    CODE_200(200),
    CODE_201(201),
    CODE_204(204),
    CODE_400(400),
    CODE_401(401),
    CODE_404(404);

    private final int code;

    StatusCode(int code){
        this.code = code;
    }

    public int getStatusCode(){
        return this.code;
    }



}
