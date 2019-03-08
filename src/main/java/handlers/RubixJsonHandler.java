package handlers;

import com.google.gson.*;


class RubixJsonHandler {

    RubixJsonHandler(){}

    JsonObject processString(String s) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(s);
        return json;
    }

    public boolean isValid(String json){
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            System.out.println("bad json: " + json);
            return false;
        } catch( NullPointerException e) {
            return false;
        }
    }
}
