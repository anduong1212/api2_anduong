package utilities;

import com.google.gson.JsonObject;
import constant.Constants;

public class JsonFormatter {
    public static JsonObject getJsonTemplateObject(){
        return JsonUtils.getJsonObjects(Constants.TEMPLATE_DIRECTORY);
    }

    public static String getFieldFromTemplate(String templateName, String field){
        return getJsonTemplateObject().getAsJsonObject(templateName).get(field).getAsString();
    }
}
