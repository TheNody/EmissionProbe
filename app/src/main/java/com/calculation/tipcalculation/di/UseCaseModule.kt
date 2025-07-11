package com.calculation.tipcalculation.di

import com.calculation.tipcalculation.domain.repository.*
import com.calculation.tipcalculation.domain.usecase.external_filter.*
import com.calculation.tipcalculation.domain.usecase.internal_filter.*
import com.calculation.tipcalculation.domain.usecase.internal_result.ClearInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history.DeleteInternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history.GetInternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.GetInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.internal_result_history.InsertInternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal_result.SetInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.report_data.*
import com.calculation.tipcalculation.domain.usecase.speed_count.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // External Filter UseCases
    @Provides
    @Singleton
    fun provideInsertExternalTipUseCase(
        repository: ExternalFilterTipRepository
    ): InsertExternalTipUseCase = InsertExternalTipUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteExternalTipUseCase(
        repository: ExternalFilterTipRepository
    ): DeleteExternalTipUseCase = DeleteExternalTipUseCase(repository)

    @Provides
    @Singleton
    fun provideGetExternalTipsUseCase(
        repository: ExternalFilterTipRepository
    ): GetExternalTipsUseCase = GetExternalTipsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetExternalTipsSyncUseCase(
        repository: ExternalFilterTipRepository
    ): GetExternalTipsSyncUseCase = GetExternalTipsSyncUseCase(repository)

    // Internal Filter UseCases
    @Provides
    @Singleton
    fun provideInsertFilterTipUseCase(
        repository: FilterTipRepository
    ): InsertFilterTipUseCase = InsertFilterTipUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteFilterTipUseCase(
        repository: FilterTipRepository
    ): DeleteFilterTipUseCase = DeleteFilterTipUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFilterTipsUseCase(
        repository: FilterTipRepository
    ): GetFilterTipsUseCase = GetFilterTipsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFilterTipsSyncUseCase(
        repository: FilterTipRepository
    ): GetFilterTipsSyncUseCase = GetFilterTipsSyncUseCase(repository)

    // Report Data UseCases
    @Provides
    @Singleton
    fun provideInsertReportUseCase(
        repository: ReportDataRepository
    ): InsertReportUseCase = InsertReportUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteReportUseCase(
        repository: ReportDataRepository
    ): DeleteReportUseCase = DeleteReportUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllReportsUseCase(
        repository: ReportDataRepository
    ): GetAllReportsUseCase = GetAllReportsUseCase(repository)

    @Provides
    @Singleton
    fun provideFindReportUseCase(
        repository: ReportDataRepository
    ): FindReportUseCase = FindReportUseCase(repository)

    // Speed Count UseCases
    @Provides
    @Singleton
    fun provideInsertSpeedUseCase(
        repository: SpeedCountRepository
    ): InsertSpeedUseCase = InsertSpeedUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteSpeedUseCase(
        repository: SpeedCountRepository
    ): DeleteSpeedUseCase = DeleteSpeedUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSpeedUseCase(
        repository: SpeedCountRepository
    ): GetSpeedUseCase = GetSpeedUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateSpeedUseCase(
        repository: SpeedCountRepository
    ): UpdateSpeedUseCase = UpdateSpeedUseCase(repository)

    //Data UseCases
    @Provides
    @Singleton
    fun provideSetInternalResultUseCase(
        repository: InternalResultRepository
    ): SetInternalResultUseCase = SetInternalResultUseCase(repository)

    @Provides
    @Singleton
    fun provideGetInternalResultUseCase(
        repository: InternalResultRepository
    ): GetInternalResultUseCase = GetInternalResultUseCase(repository)

    @Provides
    @Singleton
    fun provideClearInternalResultUseCase(
        repository: InternalResultRepository
    ): ClearInternalResultUseCase = ClearInternalResultUseCase(repository)

    //Result Internal UseCases
    @Provides
    @Singleton
    fun provideInsertInternalResultHistoryUseCase(
        repository: InternalResultHistoryRepository
    ): InsertInternalResultHistoryUseCase = InsertInternalResultHistoryUseCase(repository)

    @Provides
    @Singleton
    fun provideGetInternalResultHistoryUseCase(
        repository: InternalResultHistoryRepository
    ): GetInternalResultHistoryUseCase = GetInternalResultHistoryUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteInternalResultHistoryUseCase(
        repository: InternalResultHistoryRepository
    ): DeleteInternalResultHistoryUseCase = DeleteInternalResultHistoryUseCase(repository)

    //Validation Calculate UseCases
    @Provides
    @Singleton
    fun provideValidateInternalCalculationUseCase(
        getSpeedUseCase: GetSpeedUseCase,
        getFilterTipsSyncUseCase: GetFilterTipsSyncUseCase
    ): ValidateInternalCalculationUseCase {
        return ValidateInternalCalculationUseCase(
            getSpeedUseCase = getSpeedUseCase,
            getFilterTipsSyncUseCase = getFilterTipsSyncUseCase
        )
    }

    @Provides
    @Singleton
    fun provideValidateExternalCalculationUseCase(
        getSpeedUseCase: GetSpeedUseCase,
        getExternalTipsSyncUseCase: GetExternalTipsSyncUseCase
    ): ValidateExternalCalculationUseCase {
        return ValidateExternalCalculationUseCase(
            getSpeedUseCase = getSpeedUseCase,
            getExternalTipsSyncUseCase = getExternalTipsSyncUseCase
        )
    }
}
