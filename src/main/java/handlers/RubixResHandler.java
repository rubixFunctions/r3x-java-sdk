package handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class RubixResHandler implements HttpHandler {
    private RubixHandler handler;

    public RubixResHandler(RubixHandler r3x){
        this.handler = r3x;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream input = httpExchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach((String s) -> sb.append(s));
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(sb.toString());
        String response = String.valueOf(this.handler.execute(json));
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
