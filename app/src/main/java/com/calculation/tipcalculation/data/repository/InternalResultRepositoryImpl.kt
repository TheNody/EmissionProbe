package com.calculation.tipcalculation.data.repository

import com.calculation.tipcalculation.domain.model.InternalResultData
import com.calculation.tipcalculation.domain.repository.InternalResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalResultRepositoryImpl @Inject constructor() : InternalResultRepository {

    private val _result = MutableStateFlow<InternalResultData?>(null)

    override fun setResult(data: InternalResultData) {
        _result.value = data
    }

    override fun getResult(): StateFlow<InternalResultData?> = _result

    override fun clear() {
        _result.value = null
    }
}
