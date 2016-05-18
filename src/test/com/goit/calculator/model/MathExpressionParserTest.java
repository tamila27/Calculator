package com.goit.calculator.model;

import com.goit.calculator.util.MathExpressionParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by tamila on 5/14/16.
 */
public class MathExpressionParserTest {

    @Test
    public void testParseExpression() throws Exception {
        String expression = "123.12 + 34 * ( 12 - 10 ) /( 2)";
        Double expectedResult = 157.12;

        MathExpressionParser mathExpressionParser = new MathExpressionParser();
        Assert.assertEquals(expectedResult, mathExpressionParser.calculateExpression(expression));
    }

    @Test
    public void testParseExpression2() throws Exception {
        String expression = " + 34 * ( 12 - 10 ) /( 2)";
        Double expectedResult = 34.0;

        MathExpressionParser mathExpressionParser = new MathExpressionParser();
        Assert.assertEquals(expectedResult, mathExpressionParser.calculateExpression(expression));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseExpression3() throws Exception {
        String expression = "(*4)153+53";

        MathExpressionParser mathExpressionParser = new MathExpressionParser();
        mathExpressionParser.calculateExpression(expression);
    }

    @Test(expected = NumberFormatException.class)
    public void testParseExpression4() throws Exception {
        String expression = "25+89+a-b";

        MathExpressionParser mathExpressionParser = new MathExpressionParser();
        mathExpressionParser.calculateExpression(expression);
    }

    @Test
    public void testParseExpression5() throws Exception {
        String expression = "-100 -200*5";
        Double expectedResult = -1100.0;

        MathExpressionParser mathExpressionParser = new MathExpressionParser();
        Assert.assertEquals(expectedResult, mathExpressionParser.calculateExpression(expression));
    }

}