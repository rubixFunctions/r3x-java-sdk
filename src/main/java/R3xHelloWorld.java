import com.google.gson.JsonObject;

public class R3xHelloWorld {
    public static void main(String[] args){
        R3x r3x = new R3x();
        r3x.execute(r3xFunc());
    }

    private static JsonObject r3xFunc(){
        JsonObject res = new JsonObject();
        res.addProperty("message", "Hello r3x");
        System.out.println("R3X func executed -- " + res);
        return res;
    }
}
