package com.goit.calculator.model;

import com.goit.calculator.util.ExpressionParser;
import org.junit.Test;

/**
 * Created by tamila on 5/14/16.
 */
public class ExpressionParserTest {

    @Test
    public void testParseExpression() throws Exception {
        String expression = "123.12 + 34 * ( 12 - 10 ) /( 2)";

        ExpressionParser expressionParser = new ExpressionParser();
        System.out.println(expressionParser.calculateExpression(expression));
    }
}