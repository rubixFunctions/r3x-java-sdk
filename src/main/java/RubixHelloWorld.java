import com.google.gson.JsonObject;

/*
Test example, to be used for local dev of SDK
 */
public class RubixHelloWorld {

    public static void main(String[] args){
        Rubix r3x = new Rubix();

        r3x.setHandler(json -> {
            JsonObject res = new JsonObject();
            res.addProperty("message", "Hello r3x");
            return res;
        });

        r3x.runServer();
    }
}
