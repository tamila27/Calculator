package com.goit.calculator.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tamila on 5/14/16.
 */
public class MathExpressionParser {

    public List<String> parseExpression(String expression){
        StringTokenizer stringTokenizer = new StringTokenizer(expression, "+-*/()", true);
        List<String> parsedExpression = new ArrayList<>();
        while(stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken().trim();
            if(!token.equals("")){
                parsedExpression.add(token);
            }
        }
        return parsedExpression;
    }

    private boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }

    private int getOperatorPriority(String operator) {
        if(operator.equals("*") || operator.equals("/")) {
            return 2;
        } else if(operator.equals("+") || operator.equals("-")) {
            return 1;
        } else {
            return 0;
        }
    }

    private void calculateSimpleExpression(LinkedList<Double> numbers, String operator) throws NullPointerException, IllegalArgumentException {
        if(numbers == null || operator == null) {
            throw new NullPointerException();
        }

        if( numbers.size() < 2 && !(operator.equals("+") || operator.equals("-")) ){
            throw new IllegalArgumentException();
        }

        Double someOne = ( numbers.size() > 0) ? numbers.removeLast() : 0.0;
        Double someTwo = ( numbers.size() > 0) ? numbers.removeLast() : 0.0;

        switch(operator) {
            case "+":
                numbers.add(someTwo + someOne);
                break;
            case "-":
                numbers.add(someTwo - someOne);
                break;
            case "*":
                numbers.add(someTwo * someOne);
                break;
            case "/":
                numbers.add(someTwo / someOne);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Double calculateExpression(String s) throws NumberFormatException, IllegalArgumentException, NullPointerException{

        if(s == null || s.equals(""))
            return 0.0;

        LinkedList<Double> numbers = new LinkedList<>();
        LinkedList<String> operators = new LinkedList<>();

        List<String> tokensList = parseExpression(s);
        for ( String token: tokensList) {
            if(token.equals("(")) {
                operators.add("(");
            } else if (token.equals(")")) {
                while(!operators.getLast().equals("(")) {
                    calculateSimpleExpression(numbers, operators.removeLast());
                }
                operators.removeLast();
            } else if (isOperator(token)) {
                while(!operators.isEmpty() &&
                        getOperatorPriority(operators.getLast()) >= getOperatorPriority(token)) {
                    calculateSimpleExpression(numbers, operators.removeLast());
                }
                operators.add(token);
            } else {
                try {
                    numbers.add(Double.parseDouble(token));
                } catch (NumberFormatException exception){
                    throw exception;
                }
            }
        }

        while(!operators.isEmpty()) {
            calculateSimpleExpression(numbers, operators.removeLast());
        }

        return numbers.get(0);
    }

}
