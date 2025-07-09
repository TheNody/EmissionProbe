package com.calculation.tipcalculation.di

import android.content.Context
import androidx.room.Room
import com.calculation.tipcalculation.data.local.AppDatabase
import com.calculation.tipcalculation.data.local.dao.*
import com.calculation.tipcalculation.data.repository.*
import com.calculation.tipcalculation.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides fun provideFilterTipDao(db: AppDatabase): FilterTipDao = db.filterTipDao()
    @Provides fun provideExternalFilterTipDao(db: AppDatabase): ExternalFilterTipDao = db.externalFilterTipDao()
    @Provides fun provideSpeedDao(db: AppDatabase): SpeedDao = db.speedDao()
    @Provides fun provideReportDataDao(db: AppDatabase): ReportDataDao = db.reportDataDao()

    @Provides
    fun provideFilterTipRepository(dao: FilterTipDao): FilterTipRepository =
        FilterTipRepositoryImpl(dao)

    @Provides
    fun provideExternalFilterTipRepository(dao: ExternalFilterTipDao): ExternalFilterTipRepository =
        ExternalFilterTipRepositoryImpl(dao)

    @Provides
    fun provideSpeedCountRepository(dao: SpeedDao): SpeedCountRepository =
        SpeedCountRepositoryImpl(dao)

    @Provides
    fun provideReportDataRepository(dao: ReportDataDao): ReportDataRepository =
        ReportDataRepositoryImpl(dao)
}
