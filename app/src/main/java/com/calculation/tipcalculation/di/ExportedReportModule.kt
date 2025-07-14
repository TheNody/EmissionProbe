package com.calculation.tipcalculation.di

import android.content.Context
import com.calculation.tipcalculation.data.local.ExportedReportsStore
import com.calculation.tipcalculation.data.repository.ExportedReportRepositoryImpl
import com.calculation.tipcalculation.domain.repository.ExportedReportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExportedReportModule {

    @Provides
    @Singleton
    fun provideExportedReportsStore(
        @ApplicationContext context: Context
    ): ExportedReportsStore = ExportedReportsStore(context)

    @Provides
    @Singleton
    fun provideExportedReportRepository(
        store: ExportedReportsStore
    ): ExportedReportRepository = ExportedReportRepositoryImpl(store)
}
