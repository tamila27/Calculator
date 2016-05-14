import static spark.Spark.*;

/**
 * Created by tamila on 5/14/16.
 */
public class Main {
    public static void main(String[] args){
        get("/hello", (req, res) -> {
            return "Hello world!";
        });
    }
}
