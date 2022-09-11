package com.sailinghawklabs.photonotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.photonotes.model.Note
import com.sailinghawklabs.photonotes.persistence.NotesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val db: NotesDao
): ViewModel() {

    val notes: LiveData<List<Note>> = db.getAllNotes()

    suspend fun getNote(noteId: Int): Note? {
        return db.getNoteById(noteId)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.updateNote(note)
        }
    }

    fun createNote(title: String, note: String, imageUri: String?){
        val newNote = Note(title = title, note = note, imageUri = imageUri)
        viewModelScope.launch(Dispatchers.IO) {
            db.insertNote(newNote)
        }

    }

}

//
//@Suppress("UNCHECKED_CAST")
//class NoteViewModelFactory(
//    private val db: NotesDao
//) : ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return NotesViewModel(db) as T
//    }
//}