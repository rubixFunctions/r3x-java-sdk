package util;

import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonHandler {

    Map<String, JsonElement>values;

    public JsonHandler(){
        values = new HashMap<>();
    }

    public JsonObject processString(String s) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(s);
        return json;
    }

    public Map processJsonElement(JsonElement e) {
        if (e.isJsonArray()) {
            processJsonArray(e.getAsJsonArray());
        } else if (e.isJsonNull()) {
            processJsonNull(e.getAsJsonNull());
        } else if (e.isJsonObject()) {
            processJsonObject(e.getAsJsonObject());
        } else if (e.isJsonPrimitive()) {
            processJsonPrimitive(e.getAsJsonPrimitive());
        }
        return values;
    }

    private void processJsonArray(JsonArray a) {
        for (JsonElement e : a) {
            processJsonElement(e);
        }
    }

    private void processJsonNull(JsonNull n) {
        System.out.println("null || : " + n);
    }

    private void processJsonObject(JsonObject o) {
        Set<Map.Entry<String, JsonElement>> members= o.entrySet();
        for (Map.Entry<String, JsonElement> e : members) {
            System.out.println("Processing object member: " + e.getKey());
            if (e.getValue().isJsonPrimitive()){
                values.put(e.getKey(), e.getValue());
            } else {
                processJsonElement(e.getValue());
            }
        }
    }

    private void processJsonPrimitive(JsonPrimitive p) {
        System.out.println("Primitive || :" + p);
    }

}
