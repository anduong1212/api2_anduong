package common.apibase;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.logger.Log;
import common.report.ExtentReportManager;
import constant.Constants;
import groovy.transform.Undefined;
import groovyjarjarpicocli.CommandLine;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import testbase.TestBase;
import utilities.JacksonObjectUtils;

import java.time.Instant;
import java.util.function.LongFunction;

public class TokenManager{

    //Create a payload for direct the user to the authorization URL to get an authorization code
    private static Object oauth2Payload(){

        // The payload definition using the Jackson library
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = jsonNodeFactory.objectNode();
        {
            payload.put("grant_type", "client_credentials");
            payload.put("client_id", Constants.CLIENT_ID);
            payload.put("client_secret", Constants.CLIENT_SECRET);
            payload.put("scope", "read:write");
            payload.put("redirect_uri", Constants.API_URL);
        }

        JacksonObjectUtils.connectJacksonObjectMapperToUnirest();

        return payload;
    }

    public static synchronized void extractAccessTokenResponse(){
        Log.info("GETTING ACCESS TOKEN");
        try {
            if(Constants.ACCESS_TOKEN != null && !Instant.now().isAfter(Constants.EXPIRED_TIME)) {
                Log.info("Token is good to use");
            } else {
                JSONObject access_token_response_body = new JSONObject (((
                        (RestAssured.given(SpecBuilder.getAuthRequestSpec(Constants.TOKEN_URI))
                                .contentType(ContentType.JSON)
                                .body(oauth2Payload())
                                .when()
                                .post())
                                .then())
                        .extract().response()).getBody()
                        .asString());

                Constants.AUTH_RESPONSE = access_token_response_body.toString();

                Constants.ACCESS_TOKEN = access_token_response_body.getString("access_token");
                Constants.AUTHORIZATION = String.format("Bearer %s", Constants.ACCESS_TOKEN);
                Constants.EXPIRED_TIME = Instant.now().plusSeconds((long) (access_token_response_body.getInt("expires_in")) - 300);

            }
        } catch (Exception exception){
            Log.error(exception.getMessage());
            throw new RuntimeException("[ACCESS TOKEN] - FAILED to get TOKEN");
        }
    }

    public static void extractCloudIDResponse(){
        try {
            if(Constants.CLOUD_ID == null){
                JSONArray cloud_id_response_body = new JSONArray((((
                        RestAssured.given(SpecBuilder.getAuthRequestSpec(Constants.CLOUD_ID_URI))
                                .header(RequestHeaders.AUTHORIZATION.toString(), Constants.AUTHORIZATION)
                                .when()
                                .get())
                        .then())
                        .extract()
                        .response())
                        .getBody()
                        .asString());

                Log.info("cloud_ID" + cloud_id_response_body);

                Constants.CLOUD_ID = ((JSONObject) cloud_id_response_body.get(0)).getString("id");
            }
        } catch (Exception exception){
            Log.error(exception.getMessage());
            throw new RuntimeException("[CLOUD ID] - Failed to get Cloud ID");
        }
    }

    public static void getAccessToken(){
        if (Constants.ACCESS_TOKEN == null){
            extractAccessTokenResponse();
            Log.info("Access Token: " + Constants.ACCESS_TOKEN);
        }
    }

    public static void getCloudID(){
        if(Constants.CLOUD_ID == null){
            extractCloudIDResponse();
            Log.info("Cloud ID: " + Constants.CLOUD_ID);
        }
    }
}
