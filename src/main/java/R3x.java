import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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
            String response = r3x.toString();
            httpExchange.getResponseHeaders().set("Content-Type", "appication/json");
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}


