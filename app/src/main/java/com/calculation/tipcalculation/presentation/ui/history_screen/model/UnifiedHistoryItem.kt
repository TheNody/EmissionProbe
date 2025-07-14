package com.calculation.tipcalculation.presentation.ui.history_screen.model

import com.calculation.tipcalculation.domain.model.ExternalResultHistory
import com.calculation.tipcalculation.domain.model.InternalResultHistory

enum class HistoryType {
    INTERNAL, EXTERNAL
}

data class UnifiedHistoryItem(
    val type: HistoryType,
    val timestamp: String,
    val internalData: InternalResultHistory? = null,
    val externalData: ExternalResultHistory? = null
)