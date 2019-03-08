package handlers;

import com.google.gson.*;


class RubixJsonHandler {

    RubixJsonHandler(){}

    JsonObject processString(String s) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(s);
        return json;
    }
}
