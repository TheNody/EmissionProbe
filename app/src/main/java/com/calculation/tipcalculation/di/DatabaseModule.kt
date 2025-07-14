package com.calculation.tipcalculation.di

import android.content.Context
import androidx.room.Room
import com.calculation.tipcalculation.data.local.AppDatabase
import com.calculation.tipcalculation.data.local.dao.ExternalFilterTipDao
import com.calculation.tipcalculation.data.local.dao.ExternalResultDao
import com.calculation.tipcalculation.data.local.dao.FilterTipDao
import com.calculation.tipcalculation.data.local.dao.InternalResultDao
import com.calculation.tipcalculation.data.local.dao.SpeedDao
import com.calculation.tipcalculation.data.repository.ExternalFilterTipRepositoryImpl
import com.calculation.tipcalculation.data.repository.ExternalResultHistoryRepositoryImpl
import com.calculation.tipcalculation.data.repository.FilterTipRepositoryImpl
import com.calculation.tipcalculation.data.repository.InternalResultHistoryRepositoryImpl
import com.calculation.tipcalculation.data.repository.SpeedCountRepositoryImpl
import com.calculation.tipcalculation.domain.repository.ExternalFilterTipRepository
import com.calculation.tipcalculation.domain.repository.ExternalResultHistoryRepository
import com.calculation.tipcalculation.domain.repository.FilterTipRepository
import com.calculation.tipcalculation.domain.repository.InternalResultHistoryRepository
import com.calculation.tipcalculation.domain.repository.SpeedCountRepository
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
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides fun provideFilterTipDao(db: AppDatabase): FilterTipDao = db.filterTipDao()
    @Provides fun provideExternalFilterTipDao(db: AppDatabase): ExternalFilterTipDao = db.externalFilterTipDao()
    @Provides fun provideSpeedDao(db: AppDatabase): SpeedDao = db.speedDao()
    @Provides fun provideInternalResultDao(db: AppDatabase): InternalResultDao = db.internalResultDao()
    @Provides fun provideExternalResultDao(db: AppDatabase): ExternalResultDao = db.externalResultDao()

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
    fun provideInternalResultHistoryRepository(
        dao: InternalResultDao
    ): InternalResultHistoryRepository = InternalResultHistoryRepositoryImpl(dao)

    @Provides
    fun provideExternalResultHistoryRepository(
        dao: ExternalResultDao
    ): ExternalResultHistoryRepository = ExternalResultHistoryRepositoryImpl(dao)
}
