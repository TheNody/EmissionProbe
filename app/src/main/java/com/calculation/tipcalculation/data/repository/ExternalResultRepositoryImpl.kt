package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.domain.model.ExternalResultData
import com.calculation.tipcalculation.domain.repository.ExternalResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalResultRepositoryImpl @Inject constructor() : ExternalResultRepository {

    private val _result = MutableStateFlow<ExternalResultData?>(null)

    override fun setResult(data: ExternalResultData) {
        _result.value = data
    }

    override fun getResult(): StateFlow<ExternalResultData?> = _result

    override fun clear() {
        _result.value = null
    }
}