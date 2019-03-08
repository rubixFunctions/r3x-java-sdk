package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        String method = httpExchange.getRequestMethod();
        if ("post".equalsIgnoreCase(method)) {
            handleRequest(httpExchange);
        } else {
            handleError(httpExchange,errorJson("Only POST supported") );
        }
    }

    private void handleRequest(HttpExchange httpExchange) throws IOException{
        InputStream input = httpExchange.getRequestBody();

        StringBuilder sb = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach(sb::append);

        RubixJsonHandler jsonHandler = new RubixJsonHandler();
        if (jsonHandler.isValid(sb.toString())) {
            JsonObject json = jsonHandler.processString(sb.toString());

            String response = String.valueOf(this.handler.execute(json));
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.length());

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            handleError(httpExchange,errorJson("JSON Format Error") );
        }
    }

    private void handleError(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(500, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String errorJson(String message) {
        return String.format("{\"ERROR\":\"%s\"}", message);
    }

}
