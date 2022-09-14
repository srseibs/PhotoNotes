package com.sailinghawklabs.photonotes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sailinghawklabs.photonotes.util.Constants
import com.sailinghawklabs.photonotes.persistence.NotesDao
import com.sailinghawklabs.photonotes.persistence.NotesDatabase
import com.sailinghawklabs.photonotes.util.UriPermissionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


@Provides
@Singleton
fun provideNotesDatabase(app: Application) : NotesDatabase =
    Room.databaseBuilder(
        app.applicationContext,
        NotesDatabase::class.java,
        Constants.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()


@Provides
@Singleton
fun getDao(db: NotesDatabase) : NotesDao =  db.getDao()

@Provides
@Singleton
fun provideUriPermissionHelper(@ApplicationContext context: Context) = UriPermissionHelper(context)



}