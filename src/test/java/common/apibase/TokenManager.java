package common.apibase;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TokenManager {

    //Create a payload for oauth2.0
    private static Object oauth2Payload(){
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = jsonNodeFactory.objectNode();

        payload.put("grant_name")
    }
}
