package utilities;

import common.propmanager.PropertiesManager;
import io.restassured.response.Response;

public class ResponseUtils {
    public static String getValueFromBody(Response response, String key){
        return response.getBody().jsonPath().getString(key);
    }

    public static void archiveValueToProp(Response response,String storingKey){
        String value = response.getBody().jsonPath().getString(storingKey);
        PropertiesManager.setConfigPropValue(storingKey, value);
    }
}
