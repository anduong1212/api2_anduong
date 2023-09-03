package utilities;

import com.google.gson.JsonObject;
import constant.Constants;
import controller.jiraapi.issueenum.IssueType;

public class JsonHelper {
    public static JsonObject getJsonTemplateObject(){
        return JsonUtils.getJsonObjects(Constants.TEMPLATE_DIRECTORY);
    }

    public static String getFieldFromTemplate(String templateName, String field){
        return getJsonTemplateObject().getAsJsonObject(templateName).get(field).getAsString();
    }

    public static String formatJsonValue(String templateName, String field, String replaceString){
        return String.format(getFieldFromTemplate(templateName, field), replaceString);
    }
}
