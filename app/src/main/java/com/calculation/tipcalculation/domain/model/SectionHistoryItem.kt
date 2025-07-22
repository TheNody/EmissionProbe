package com.calculation.tipcalculation.domain.model

sealed class SectionHistoryItem {
    abstract val timestamp: String

    data class Round(val data: RoundResultHistory) : SectionHistoryItem() {
        override val timestamp: String get() = data.timestamp
    }

    data class Rectangular(val data: RectangularResultHistory) : SectionHistoryItem() {
        override val timestamp: String get() = data.timestamp
    }
}