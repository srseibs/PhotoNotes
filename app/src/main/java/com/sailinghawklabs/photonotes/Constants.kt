package com.sailinghawklabs.photonotes

import com.sailinghawklabs.photonotes.model.Note

object Constants {
    const val TABLE_NAME = "notes"
    const val DATABASE_NAME = "notesDatabase.db"

    val noteDetailPlaceHolder = Note(
        note = "Cannot find note details",
        id = 0,
        title = "No Details!"
    )

    fun noteCreateRoute() = "noteCreate"
    fun noteDetailRoute(noteId: Int) = "noteDetail/$noteId"
    fun noteEditRoute(noteId: Int) = "noteEdit/$noteId"
}