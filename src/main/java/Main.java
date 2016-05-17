import com.goit.calculator.manager.UserManager;
import com.goit.calculator.model.User;
import com.goit.calculator.util.ExpressionParser;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
/**
 * Created by tamila on 5/14/16.
 */
public class Main {
    private static User user;

    public static void main(String[] args){
        staticFileLocation("/public");

        get("/", (request, response) -> {
            HashMap model = new HashMap();

            String userName = request.queryParams("userName");
            String password = request.queryParams("password");
            if(userName != null & password != null){
                user = UserManager.getUserFromDB(userName, password);
            }

            model.put("userName", user==null?"guest":user.getUserName());
            String expression = request.queryParams("expression");

            ExpressionParser expressionParser = new ExpressionParser();
            String calculatingResult;
            try{
                calculatingResult = expressionParser.calculateExpression(expression == null?"":expression).toString();
            } catch(NumberFormatException exception){
                calculatingResult = "Please enter a valid expression for calculation";
            }

            model.put("expression", calculatingResult == null?0:calculatingResult);

            return new ModelAndView(model, "templates/hello.vtl");
        }, new VelocityTemplateEngine());

        get("/registration", (request, response) -> {
            return new ModelAndView(new HashMap(), "templates/registration.vtl");
        }, new VelocityTemplateEngine());

        get("/registration-handler", (request, response) -> {
            User userForRegistration = new User();
            userForRegistration.setUserName(request.queryParams("userName"));
            userForRegistration.setUserLastName(request.queryParams("lastName"));
            userForRegistration.setUserEmail(request.queryParams("userEmail"));
            userForRegistration.setPassword(request.queryParams("password"));

            UserManager.registerUser(userForRegistration);

            user = UserManager.getUserFromDB(userForRegistration.getUserName(), userForRegistration.getPassword());

            HashMap model = new HashMap();
            return new ModelAndView(model, "templates/registration-handler.vtl");
        }, new VelocityTemplateEngine());

        get("/login", (request, response) -> {
            return new ModelAndView(new HashMap(), "templates/login.vtl");
        }, new VelocityTemplateEngine());
    }
}
