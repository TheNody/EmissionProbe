package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.domain.model.RectangularInputData
import com.calculation.tipcalculation.domain.repository.RectangularInputRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RectangularInputRepositoryImpl @Inject constructor() : RectangularInputRepository {

    private val _input = MutableStateFlow<RectangularInputData?>(null)

    override fun setInput(data: RectangularInputData) {
        _input.value = data
    }

    override fun getInput(): StateFlow<RectangularInputData?> = _input

    override fun clear() {
        _input.value = null
    }
}