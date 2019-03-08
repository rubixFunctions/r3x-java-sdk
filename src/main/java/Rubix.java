import com.sun.net.httpserver.HttpServer;
import handlers.RubixHandler;
import handlers.RubixResHandler;


import java.io.*;
import java.net.InetSocketAddress;
import java.util.Optional;


public class Rubix {

    private int PORT;
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
        PORT = Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElseThrow(
                () -> new IOException("PORT is not set in the environment")));
        RubixResHandler resHandler = new RubixResHandler(this.handler);
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", resHandler);
        server.setExecutor(null);
        server.start();
    }
}


