package com.calculation.tipcalculation.di

import com.calculation.tipcalculation.data.repository.CalculatorRepositoryImpl
import com.calculation.tipcalculation.domain.repository.CalculatorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCalculatorRepository(
        impl: CalculatorRepositoryImpl
    ): CalculatorRepository
}