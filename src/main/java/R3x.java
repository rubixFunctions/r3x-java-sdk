import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class R3x {

    // Handle User Function
    public void execute(JsonObject r3x) {
        try {
            HTTPStream(r3x);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void HTTPStream(JsonObject r3x) throws IOException {
        R3xResHandler resHandler = new R3xResHandler(r3x);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", resHandler);
        server.setExecutor(null);
        server.start();
    }

    static class R3xResHandler implements HttpHandler {
        JsonObject r3x;

        R3xResHandler(JsonObject r3x){
            this.r3x = r3x;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            InputStream input = httpExchange.getRequestBody();
            StringBuilder sb = new StringBuilder();
            new BufferedReader(new InputStreamReader(input))
                    .lines()
                    .forEach((String s) -> sb.append(s));
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(sb.toString());
            System.out.println(jsonTree);


            JsonHandler jh = new JsonHandler();

            jh.processJsonElement(jsonTree);


            String response = r3x.toString();
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }
}


