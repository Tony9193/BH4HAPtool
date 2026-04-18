package com.example.bh4haptool.feature.aasplitter.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalculatorExpressionTest {

    @Test
    fun evaluate_handlesOperatorPrecedence() {
        val result = CalculatorExpression.evaluate("1+2*3")
        assertEquals("7", CalculatorExpression.formatToAmountInput(result!!))
    }

    @Test
    fun evaluate_handlesDivisionAndScale() {
        val result = CalculatorExpression.evaluate("10/3")
        assertEquals("3.33", CalculatorExpression.formatToAmountInput(result!!))
    }

    @Test
    fun evaluate_returnsNullOnDivisionByZero() {
        val result = CalculatorExpression.evaluate("10/0")
        assertNull(result)
    }

    @Test
    fun evaluate_returnsNullOnInvalidExpression() {
        val result = CalculatorExpression.evaluate("1++2")
        assertNull(result)
    }
}
