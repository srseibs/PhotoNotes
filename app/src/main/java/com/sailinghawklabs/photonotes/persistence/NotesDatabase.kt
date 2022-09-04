package com.sailinghawklabs.photonotes.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sailinghawklabs.photonotes.model.Note


@Database(version = 1, entities = [Note::class]  )
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getDao(): NotesDao

}