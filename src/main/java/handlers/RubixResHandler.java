package handlers;

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
        InputStream input = httpExchange.getRequestBody();

        StringBuilder sb = new StringBuilder();
        new BufferedReader(new InputStreamReader(input))
                .lines()
                .forEach((String s) -> sb.append(s));

        RubixJsonHandler jsonHandler = new RubixJsonHandler();
        JsonObject json = jsonHandler.processString(sb.toString());

        String response = String.valueOf(this.handler.execute(json));
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
