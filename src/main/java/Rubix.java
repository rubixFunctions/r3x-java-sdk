import com.sun.net.httpserver.HttpServer;
import handlers.RubixHandler;
import handlers.RubixResHandler;


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
        RubixResHandler resHandler = new RubixResHandler(this.handler);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", resHandler);
        server.setExecutor(null);
        server.start();
    }
}


