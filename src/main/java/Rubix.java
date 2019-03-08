import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;


public class Rubix {

    private RubixHandler handler;

    public void setHandler(final RubixHandler handler){
        this.handler = handler;
    }

    public void runServer(){
        try {
            HTTPStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void HTTPStream() throws IOException {
        R3xResHandler resHandler = new R3xResHandler(this.handler);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", resHandler);
        server.setExecutor(null);
        server.start();
    }

    public class R3xResHandler implements HttpHandler {
        private RubixHandler handler;

        R3xResHandler(RubixHandler r3x){
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
}


