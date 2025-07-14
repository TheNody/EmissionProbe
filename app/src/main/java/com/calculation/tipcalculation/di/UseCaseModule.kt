package com.calculation.tipcalculation.di

import com.calculation.tipcalculation.domain.repository.*
import com.calculation.tipcalculation.domain.usecase.external.export_excel.AddExportedReportUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.ExportExternalResultToExcelUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.ExternalExcelExporter
import com.calculation.tipcalculation.domain.usecase.external.export_excel.GetExportedReportsUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.InvalidateExportedReportsUseCase
import com.calculation.tipcalculation.domain.usecase.external.export_excel.RemoveExportedReportUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.DeleteExternalTipUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.GetExternalTipsSyncUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.GetExternalTipsUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.InsertExternalTipUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_filter.ValidateExternalCalculationUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.ClearExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.GetExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.SetExternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.DeleteAllExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.DeleteExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.GetExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.external.external_result.external_result_history.InsertExternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_filter.DeleteFilterTipUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_filter.GetFilterTipsSyncUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_filter.GetFilterTipsUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_filter.InsertFilterTipUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_filter.ValidateInternalCalculationUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.ClearInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history.DeleteInternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history.GetInternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.GetInternalResultUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.internal_result_history.InsertInternalResultHistoryUseCase
import com.calculation.tipcalculation.domain.usecase.internal.internal_result.SetInternalResultUseCase
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

    // External Result UseCases
    @Provides
    @Singleton
    fun provideSetExternalResultUseCase(
        repository: ExternalResultRepository
    ): SetExternalResultUseCase = SetExternalResultUseCase(repository)

    @Provides
    @Singleton
    fun provideGetExternalResultUseCase(
        repository: ExternalResultRepository
    ): GetExternalResultUseCase = GetExternalResultUseCase(repository)

    @Provides
    @Singleton
    fun provideClearExternalResultUseCase(
        repository: ExternalResultRepository
    ): ClearExternalResultUseCase = ClearExternalResultUseCase(repository)

    // External Result History UseCases
    @Provides
    @Singleton
    fun provideInsertExternalResultHistoryUseCase(
        repository: ExternalResultHistoryRepository
    ): InsertExternalResultHistoryUseCase = InsertExternalResultHistoryUseCase(repository)

    @Provides
    @Singleton
    fun provideGetExternalResultHistoryUseCase(
        repository: ExternalResultHistoryRepository
    ): GetExternalResultHistoryUseCase = GetExternalResultHistoryUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteExternalResultHistoryUseCase(
        repository: ExternalResultHistoryRepository
    ): DeleteExternalResultHistoryUseCase = DeleteExternalResultHistoryUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteAllExternalResultHistoryUseCase(
        repository: ExternalResultHistoryRepository
    ): DeleteAllExternalResultHistoryUseCase = DeleteAllExternalResultHistoryUseCase(repository)

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

    // Excel Export UseCases
    @Provides
    @Singleton
    fun provideExternalExcelExporter(): ExternalExcelExporter = ExternalExcelExporter()

    @Provides
    @Singleton
    fun provideExportExternalResultToExcelUseCase(
        exporter: ExternalExcelExporter
    ): ExportExternalResultToExcelUseCase = ExportExternalResultToExcelUseCase(exporter)

    @Provides
    @Singleton
    fun provideAddExportedReportUseCase(
        repository: ExportedReportRepository
    ): AddExportedReportUseCase = AddExportedReportUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveExportedReportUseCase(
        repository: ExportedReportRepository
    ): RemoveExportedReportUseCase = RemoveExportedReportUseCase(repository)

    @Provides
    @Singleton
    fun provideGetExportedReportsUseCase(
        repository: ExportedReportRepository
    ): GetExportedReportsUseCase = GetExportedReportsUseCase(repository)

    @Provides
    @Singleton
    fun provideInvalidateExportedReportsUseCase(
        repository: ExportedReportRepository
    ): InvalidateExportedReportsUseCase = InvalidateExportedReportsUseCase(repository)
}
