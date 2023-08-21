package common.apibase;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constant.Constants;
import org.json.JSONObject;
import utilities.JacksonObjectUtils;

import java.time.Instant;

public class TokenManager {

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

    public static synchronized void getAccessTokenResponse(){
        try {
            if(Constants.ACCESS_TOKEN == null || Instant.now().isAfter(Constants.EXPIRED_TIME)){
                JSONObject access_token_response_body = new JSONObject(get)
            }
        }
    }
}
