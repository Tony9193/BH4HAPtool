package com.example.bh4haptool.feature.aasplitter.domain

import java.math.BigDecimal
import java.math.RoundingMode

object MoneyCodec {
    fun parseYuanToCents(raw: String): Long? {
        val normalized = raw.trim().replace(',', '.')
        if (normalized.isBlank()) {
            return null
        }

        val valid = Regex("^\\d+(\\.\\d{1,2})?$")
        if (!valid.matches(normalized)) {
            return null
        }

        return runCatching {
            BigDecimal(normalized)
                .setScale(2, RoundingMode.HALF_UP)
                .movePointRight(2)
                .longValueExact()
        }.getOrNull()
    }

    fun formatCentsToYuan(cents: Long): String {
        val sign = if (cents < 0) "-" else ""
        val abs = kotlin.math.abs(cents)
        val yuan = abs / 100
        val remain = abs % 100
        return "$sign$yuan.${remain.toString().padStart(2, '0')}"
    }
}
