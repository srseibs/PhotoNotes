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

    const val NOTE_CREATE_ROUTE = "noteCreate"
    const val NOTES_LIST_ROUTE = "notesList"
    const val NOTE_DETAIL_ROUTE = "noteDetail"
    const val NOTE_EDIT_ROUTE = "noteEdit"
    const val NOTES_ARGUMENT_ID = "noteId"

    fun noteCreateRoute() = NOTE_CREATE_ROUTE
    fun noteListRoute() = NOTES_LIST_ROUTE
    fun noteDetailRouteWithParam(id: Int) = "$NOTE_DETAIL_ROUTE/$id"
    fun noteEditRouteWithParam(id: Int) = "$NOTE_EDIT_ROUTE/$id"
}