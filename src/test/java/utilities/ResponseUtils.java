package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import common.logger.Log;
import common.propmanager.PropertiesManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResponseUtils {
    public static String getValueFromBody(Response response, String key){
        return response.getBody().jsonPath().getString(key);
    }

    public static boolean isValueIsNullOrEmpty(Response response, String key){
        String value = getValueFromBody(response, key);
        return value.isEmpty() || value == null;
    }

    public static List<Map<String, Object>> getResponseAsList(ResponseBody response, String fields){
        Map<String, Object> responseAsObject = (Map<String, Object>) response.as(Object.class);

        return (List<Map<String, Object>>) responseAsObject.get(fields);
    }

    public static Boolean isValueContainInListRes(ResponseBody responseBody, String fields, String value){
        Boolean result = false;
        Iterator iterator = getResponseAsList(responseBody, fields).listIterator();
        while (iterator.hasNext()){
            result =((Map<String, Object>) iterator.next()).containsValue(value);
            if (result == true){
                break;
            }
        }
        return result;
    }

}
