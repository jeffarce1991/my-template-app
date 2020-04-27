package com.jeff.project420.database

import android.app.Application
import androidx.room.Room
import com.jeff.project420.R
import com.jeff.project420.database.room.AppDatabase
import com.jeff.project420.database.room.dao.PhotoDao
import com.jeff.project420.database.usecase.local.LocalUseCaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalUseCaseModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
            AppDatabase::class.java,
            application.getString(R.string.db_name))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAssignmentDao(appDatabase: AppDatabase): PhotoDao {
        return appDatabase.photoDao()
    }
}