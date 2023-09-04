package common.apibase;

import common.propmanager.PropertiesManager;
import constant.Constants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    public static RequestSpecification getAuthRequestSpec(String URI){
        return (new RequestSpecBuilder())
                .setBaseUri(URI)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpec(){
        String issueEndPoint = String.format(Constants.BASE_URL, PropertiesManager.getDefaultPropValue("cloud_id"));
        return (new RequestSpecBuilder())
                .setBaseUri(issueEndPoint)
                .setBasePath("")
                .build();
    }

    public static ResponseSpecification getResponseStatusSpec(){
        return (new ResponseSpecBuilder()).log(LogDetail.STATUS).build();
    }

    public static ResponseSpecification getResponseBodySpec(){
        return (new ResponseSpecBuilder()).log(LogDetail.BODY).build();
    }

    public static Response post(String path, String token, Object object){
        return (((RestAssured.given(getRequestSpec())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(object)
                    .auth()
                    .oauth2(token)
                    .when()
                    .post(path))
                .then())
                .spec(getResponseStatusSpec()))
                .spec(getResponseBodySpec()).extract().response();
    }

    public static Response get(String path, String token){
        return (((RestAssured.given(getRequestSpec())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(token)
                .when()
                .get(path))
                .then())
                .spec(getResponseStatusSpec()))
                .spec(getResponseBodySpec()).extract().response();
    }

    public static Response delete(String path, String token){
        return (((RestAssured.given(getRequestSpec())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(token)
                .when()
                .delete(path))
                .then())
                .spec(getResponseStatusSpec()))
                .spec(getResponseBodySpec()).extract().response();
    }


}
