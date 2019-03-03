package util;

import com.google.gson.*;

import java.util.Map;
import java.util.Set;

public class JsonHandler {

    public JsonHandler(){}

    public void processJsonElement(JsonElement e) {
        if (e.isJsonArray()) {
            processJsonArray(e.getAsJsonArray());
        } else if (e.isJsonNull()) {
            processJsonNull(e.getAsJsonNull());
        } else if (e.isJsonObject()) {
            processJsonObject(e.getAsJsonObject());
        } else if (e.isJsonPrimitive()) {
            processJsonPrimitive(e.getAsJsonPrimitive());
        }
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
            processJsonElement(e.getValue());
        }
    }

    private void processJsonPrimitive(JsonPrimitive p) {
        System.out.println("Primitive || :" + p);
    }

}
