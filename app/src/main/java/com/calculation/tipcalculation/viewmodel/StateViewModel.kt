package com.calculation.tipcalculation.viewmodel

/*class StateViewModel(application: Application) : AndroidViewModel(application) {

    val speeds = mutableStateListOf<String>()
    var data by mutableStateOf(CalculationData())
    var enteredSpeedCount by mutableStateOf("")

    private val speedDao: SpeedDao = AppDatabase.getDatabase(application).speedDao()

    init {
        viewModelScope.launch {
            val speedEntity = speedDao.getSpeed()
            if (speedEntity != null) {
                setSpeedCount(speedEntity.speedCount)
            }
        }
    }

    fun resetValues() {
        data = CalculationData()
    }

    fun setSpeedCount(count: Int) {
        speeds.addAll(List(count) { "" })
        enteredSpeedCount = count.toString()
        viewModelScope.launch {
            speedDao.insert(SpeedCount(speedCount = count))
        }
        Log.d("StateViewModel", "Speed count set to $count")
    }

    fun deleteSpeedCount() {
        speeds.clear()
        enteredSpeedCount = ""
        viewModelScope.launch {
            speedDao.deleteAll()
        }
        Log.d("StateViewModel", "Speed count deleted")
    }
}*/
