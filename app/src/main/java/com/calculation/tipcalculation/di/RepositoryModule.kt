package com.calculation.tipcalculation.di

import com.calculation.tipcalculation.data.repository.InternalResultRepositoryImpl
import com.calculation.tipcalculation.domain.repository.InternalResultRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindInternalResultRepository(
        impl: InternalResultRepositoryImpl
    ): InternalResultRepository
}
