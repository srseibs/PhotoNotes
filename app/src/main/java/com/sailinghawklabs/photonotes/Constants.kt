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

    const val NOTES_ARGUMENT_ID = "noteId"

    private const val NOTE_CREATE_ROUTE = "noteCreate"
    private const val NOTES_LIST_ROUTE = "notesList"
    private const val NOTE_DETAIL_ROUTE = "noteDetail"
    private const val NOTE_EDIT_ROUTE = "noteEdit"

    fun defineNoteCreateRoute() = NOTE_CREATE_ROUTE
    fun defineNoteListRoute() = NOTES_LIST_ROUTE
    fun defineNoteDetailRoute() = "$NOTE_DETAIL_ROUTE/{$NOTES_ARGUMENT_ID}"
    fun defineNoteEditRoute() = "$NOTE_EDIT_ROUTE/{$NOTES_ARGUMENT_ID}"

    fun noteDetailNavigation(id: Int) = "$NOTE_DETAIL_ROUTE/$id"
    fun callNoteEditRouteWithParam(id: Int) = "$NOTE_EDIT_ROUTE/$id"
}