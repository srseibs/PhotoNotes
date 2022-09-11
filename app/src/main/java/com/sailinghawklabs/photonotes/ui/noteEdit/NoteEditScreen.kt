package com.sailinghawklabs.photonotes.ui.noteEdit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sailinghawklabs.photonotes.Constants
import com.sailinghawklabs.photonotes.NotesViewModel
import com.sailinghawklabs.photonotes.PhotoNotesApp
import com.sailinghawklabs.photonotes.model.Note
import com.sailinghawklabs.photonotes.ui.GenericAppBar
import com.sailinghawklabs.photonotes.ui.noteList.NotesFab
import com.sailinghawklabs.photonotes.ui.theme.PhotoNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    noteId: Int,
    navController: NavController,
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    var note by remember { mutableStateOf(Constants.noteDetailPlaceHolder) }

    var currentTitle by remember { mutableStateOf(note.title) }
    var currentNote by remember { mutableStateOf(note.note) }
    var currentImage by remember { mutableStateOf(note.imageUri) }
    var saveButtonVisible by remember { mutableStateOf(false) }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                PhotoNotesApp.getUriPermission(uri)
            }
            currentImage = uri.toString()
            saveButtonVisible = (currentImage != note.imageUri)
        })

    fun setSaveButtonVisibility() {
        saveButtonVisible = !(currentTitle == note.title
                && currentNote == note.note
                && currentImage == note.imageUri)
    }

    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            note = viewModel.getNote(noteId) ?: Constants.noteDetailPlaceHolder
            currentTitle = note.title
            currentNote = note.note
            currentImage = note.imageUri
            saveButtonVisible = false
        }
    }

    PhotoNotesTheme {
        Scaffold(
            topBar = {
                GenericAppBar(
                    title = "Edit Note",
                    onIconClick = {
                        viewModel.updateNote(
                            Note(
                                id = note.id,
                                title = currentTitle,
                                note = currentNote,
                                imageUri = currentImage
                            )
                        )
                        navController.popBackStack()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save note"
                        )
                    },
                    iconState = saveButtonVisible,
                )
            },
            floatingActionButton = {
                NotesFab(
                    contentDescription = "Add Photo",
                    action = {
                        getImageRequest.launch(arrayOf("image/*"))
                    },
                    icon = Icons.Default.Camera
                )
            }

        ) { scaffoldPadding ->
            Column(
                modifier = modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
            )
            {
                if (!currentImage.isNullOrEmpty()) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = Uri.parse(currentImage))
                        ),
                        contentDescription = "Note image",
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .fillMaxWidth()
                            .padding(6.dp),
                        contentScale = ContentScale.Crop,
                    )

                    TextField(
                        value = currentTitle,
                        onValueChange = { value ->
                            currentTitle = value
                            setSaveButtonVisibility()
                        },
                        label = { Text("Title") }
                    )
                    Spacer(Modifier.padding(12.dp))
                    TextField(
                        value = currentNote,
                        onValueChange = { value ->
                            currentNote = value
                            setSaveButtonVisibility()
                        },
                        label = { Text("Content") }
                    )
                }
            }
        }
    }



}