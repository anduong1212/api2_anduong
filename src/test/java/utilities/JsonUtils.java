package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static JsonObject getJsonObjects(String filePath) {

        try {
            JsonObject jsonObject;
            Gson gson = new Gson();
            JsonReader jsonReader = getJsonReader(filePath);
            jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            return jsonObject;
        } catch (Exception e) {
            throw e;
        }

    }

    public static JsonReader getJsonReader(String filePath) {
        try {
            JsonReader jsonReader;
            jsonReader = new JsonReader(new FileReader(filePath));
            return jsonReader;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static <T> T convertJsonToObjects(JsonObject jsonObject, Class<T> tClass){
        return new Gson().fromJson(jsonObject, tClass);
    }

    public static <T> T convertToObjects(String jsonKey, JsonObject jsonObject, Class<T> tClass){
        JsonObject dataObject = jsonObject.getAsJsonObject(jsonKey);
        return new Gson().fromJson(dataObject, tClass);
    }

    public static String[] convertJsonArrayToArray(JsonObject dataObject, String name){
        JsonArray jsonArray = dataObject.getAsJsonArray(name);

        List<String> list = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++){
            list.add(jsonArray.get(i).getAsString());
        }

        return list.toArray(new String[list.size()]);
    }
}
