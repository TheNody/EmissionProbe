package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.domain.model.RoundInputData
import com.calculation.tipcalculation.domain.repository.RoundInputRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoundInputRepositoryImpl @Inject constructor() : RoundInputRepository {

    private val _input = MutableStateFlow<RoundInputData?>(null)

    override fun setInput(data: RoundInputData) {
        _input.value = data
    }

    override fun getInput(): StateFlow<RoundInputData?> = _input

    override fun clear() {
        _input.value = null
    }
}
