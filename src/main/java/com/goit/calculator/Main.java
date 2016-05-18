package com.goit.calculator;

import com.goit.calculator.manager.UserManager;
import com.goit.calculator.model.User;
import com.goit.calculator.util.MathExpressionParser;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

import static spark.Spark.get;
/**
 * Created by tamila on 5/14/16.
 */
public class Main {
    private static User user;

    public static void main(String[] args){
        get("/", (request, response) -> {
            HashMap model = new HashMap();

            String userName = request.queryParams("userName");
            String password = request.queryParams("password");
            if(!isBlank(userName) & !isBlank(password)){
                user = UserManager.getUserFromDB(userName, password);
            }

            model.put("userName", user==null?"guest":user.getUserName());
            String expression = request.queryParams("expression");

            MathExpressionParser mathExpressionParser = new MathExpressionParser();
            String calculatingResult;
            try{
                calculatingResult = mathExpressionParser.calculateExpression(isBlank(expression)?"":expression).toString();
            } catch( NullPointerException | IllegalArgumentException exception){
                calculatingResult = "Please enter a valid expression";
            }

            model.put("expression", isBlank(calculatingResult)?0:calculatingResult);

            return new ModelAndView(model, "templates/main.vtl");
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

    public static boolean isBlank(String s) {
        return s == null ||"".equals(s.trim());
    }
}
