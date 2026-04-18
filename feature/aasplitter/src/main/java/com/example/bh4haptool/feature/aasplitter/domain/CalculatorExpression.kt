package com.example.bh4haptool.feature.aasplitter.domain

import java.math.BigDecimal
import java.math.RoundingMode

object CalculatorExpression {
    private val operators = setOf('+', '-', '*', '/')

    fun evaluate(expressionRaw: String): BigDecimal? {
        val expression = expressionRaw
            .replace('×', '*')
            .replace('÷', '/')
            .replace(" ", "")

        if (expression.isBlank()) {
            return BigDecimal.ZERO
        }

        if (expression.any { it !in operators && !it.isDigit() && it != '.' }) {
            return null
        }

        val tokens = tokenize(expression) ?: return null
        if (tokens.size % 2 == 0) {
            return null
        }

        val reduced = mutableListOf<String>()
        var index = 0
        while (index < tokens.size) {
            val token = tokens[index]
            if (token == "*" || token == "/") {
                if (reduced.isEmpty()) {
                    return null
                }
                val left = reduced.removeLast().toBigDecimalOrNull() ?: return null
                val right = tokens.getOrNull(index + 1)?.toBigDecimalOrNull() ?: return null
                val result = if (token == "*") {
                    left.multiply(right)
                } else {
                    if (right.compareTo(BigDecimal.ZERO) == 0) {
                        return null
                    }
                    left.divide(right, 8, RoundingMode.HALF_UP)
                }
                reduced += result.stripTrailingZeros().toPlainString()
                index += 2
            } else {
                reduced += token
                index += 1
            }
        }

        var result = reduced.first().toBigDecimalOrNull() ?: return null
        index = 1
        while (index < reduced.size) {
            val op = reduced[index]
            val right = reduced.getOrNull(index + 1)?.toBigDecimalOrNull() ?: return null
            result = when (op) {
                "+" -> result.add(right)
                "-" -> result.subtract(right)
                else -> return null
            }
            index += 2
        }

        return result.setScale(2, RoundingMode.HALF_UP)
    }

    fun formatToAmountInput(value: BigDecimal): String {
        return value
            .setScale(2, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
    }

    private fun tokenize(expression: String): List<String>? {
        val tokens = mutableListOf<String>()
        val number = StringBuilder()

        fun flushNumber(): Boolean {
            if (number.isEmpty()) {
                return false
            }
            if (number.count { it == '.' } > 1) {
                return false
            }
            val numberToken = number.toString()
            if (numberToken == ".") {
                return false
            }
            if (numberToken.toBigDecimalOrNull() == null) {
                return false
            }
            tokens += numberToken
            number.clear()
            return true
        }

        expression.forEach { ch ->
            if (ch.isDigit() || ch == '.') {
                number.append(ch)
            } else if (ch in operators) {
                if (!flushNumber()) {
                    return null
                }
                tokens += ch.toString()
            } else {
                return null
            }
        }

        if (!flushNumber()) {
            return null
        }

        return tokens
    }
}
