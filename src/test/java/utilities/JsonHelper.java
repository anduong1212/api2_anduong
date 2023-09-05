package utilities;

import com.google.gson.JsonObject;
import common.propmanager.PropertiesManager;
import constant.Constants;

public class JsonHelper {
    public static JsonObject getJsonTemplateObject(){
        String environment = PropertiesManager.getCustomPropValue("environment");
        JsonObject object = null;
        switch (environment){
            case "Default" :
                object = JsonUtils.getJsonObjects(Constants.TEMPLATE_DIRECTORY);
                break;
            case "QA":
                object = JsonUtils.getJsonObjects(Constants.QA_TEMPLATE_DIRECTORY);
        }

        return object;
    }

    public static String getFieldFromTemplate(String templateName, String field){
        return getJsonTemplateObject().getAsJsonObject(templateName).get(field).getAsString();
    }

    public static String formatJsonValue(String templateName, String field, String replaceString){
        return String.format(getFieldFromTemplate(templateName, field), replaceString);
    }
}
