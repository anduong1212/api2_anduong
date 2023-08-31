package common.apibase;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.logger.Log;
import common.propmanager.PropertiesManager;
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
import utilities.DateTimeUtils;
import utilities.JacksonObjectUtils;

import java.time.Instant;
import java.util.function.LongFunction;

public class TokenManager{

    private static final String CLIENT_ID = PropertiesManager.getDefaultPropValue("client_id");
    private static final String CLIENT_SECRET = PropertiesManager.getDefaultPropValue("client_secret");
    private static final String CLOUD_ID = PropertiesManager.getDefaultPropValue("cloud_id");
    private static final String API_SCOPE = PropertiesManager.getDefaultPropValue("scope");
    private static final String ACCESS_TOKEN = PropertiesManager.getDefaultPropValue("access_token");
    private static final Instant EXPIRED_TIME = DateTimeUtils.convertDateTime(PropertiesManager.getDefaultPropValue("expired_time"));

    //Create a payload for direct the user to the authorization URL to get an authorization code
    private static Object oauth2Payload(){

        // The payload definition using the Jackson library
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = jsonNodeFactory.objectNode();

        payload.put("grant_type", "client_credentials");
        payload.put("client_id", CLIENT_ID);
        payload.put("client_secret", CLIENT_SECRET);
//        payload.put("code", Constants.AUTHORIZATION_CODE);
        payload.put("scope", API_SCOPE);
        payload.put("redirect_uri", Constants.API_URL);

        JacksonObjectUtils.connectJacksonObjectMapperToUnirest();

        return payload;
    }

    public static synchronized void extractAccessTokenResponse(){
        Log.info("GETTING ACCESS TOKEN");
        try {
            if(ACCESS_TOKEN != null && !Instant.now().isAfter(EXPIRED_TIME)) {
                Log.info("[GET ACCESS TOKEN] Token is good to use");
            } else {
                JSONObject access_token_response_body = new JSONObject (((
                        (RestAssured.given(SpecBuilder.getAuthRequestSpec(Constants.TOKEN_URI))
                                .contentType(ContentType.JSON)
                                .body(oauth2Payload())
                                .when()
                                .post())
                                .then())
                        .extract()
                        .response())
                        .getBody()
                        .asString());

                Constants.AUTH_RESPONSE = access_token_response_body.toString();

                PropertiesManager.setDefaultPropValue("access_token", access_token_response_body.getString("access_token"));
                PropertiesManager.setDefaultPropValue("authorization", "Bearer " + access_token_response_body.getString("access_token"));
                PropertiesManager.setDefaultPropValue("expired_time", String.valueOf(Instant.now().plusSeconds((long) (access_token_response_body.getInt("expires_in")) - 300)));

            }
        } catch (Exception exception){
            Log.error(exception.getMessage());
            throw new RuntimeException("[ACCESS TOKEN] - FAILED to get TOKEN");
        }
    }

    public static void extractCloudIDResponse(){
        try {
            if(CLOUD_ID == null){
                JSONArray cloud_id_response_body = new JSONArray(((
                        (RestAssured.given(SpecBuilder.getAuthRequestSpec(Constants.CLOUD_ID_URI))
                                .header(RequestHeaders.AUTHORIZATION.toString(), PropertiesManager.getDefaultPropValue("authorization"))
                                .when()
                                .get())
                                .then())
                        .extract()
                        .response())
                        .getBody()
                        .asString());

                Constants.CLOUD_ID_RESPONSE = cloud_id_response_body.toString();
                PropertiesManager.setDefaultPropValue("cloud_id", ((JSONObject)cloud_id_response_body.get(0)).getString("id"));
            }
        } catch (Exception exception){
            Log.error(exception.getMessage());
            throw new RuntimeException("[CLOUD ID] - Failed to get Cloud ID");
        }
    }

    public static void getAccessToken(){
        if (ACCESS_TOKEN == null){
            extractAccessTokenResponse();
            Log.info("Access Token: " + PropertiesManager.getDefaultPropValue("access_token"));
        }
    }

    public static void getCloudID(){
        if(CLOUD_ID == null){
            extractCloudIDResponse();
            Log.info("Cloud ID: " + PropertiesManager.getDefaultPropValue("cloud_id"));
        }
    }
}
