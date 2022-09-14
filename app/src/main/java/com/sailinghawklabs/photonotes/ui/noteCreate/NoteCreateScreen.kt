package com.sailinghawklabs.photonotes.ui.noteCreate

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sailinghawklabs.photonotes.NotesViewModel
import com.sailinghawklabs.photonotes.ui.GenericAppBar
import com.sailinghawklabs.photonotes.ui.noteList.NotesFab
import com.sailinghawklabs.photonotes.ui.theme.PhotoNotesTheme


//fun getUriPermission(uri: Uri, appContext: Context) {
//    appContext.contentResolver
//        .takePersistableUriPermission(uri,
//        Intent.FLAG_GRANT_READ_URI_PERMISSION)
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCreateScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel = hiltViewModel(),
) {

    var currentTitle by remember { mutableStateOf("") }
    var currentNote by remember { mutableStateOf("") }
    var currentImage by remember { mutableStateOf("") }
    var saveButtonVisible by remember { mutableStateOf(false) }

    fun setSaveButtonVisibility() {
        saveButtonVisible =
            (currentTitle.isNotEmpty() && currentNote.isNotEmpty())
    }

    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            notesViewModel.requestUriPermission(uri)
//            getUriPermission(uri, appContext)
        }
        currentImage = uri.toString()
    }

    PhotoNotesTheme {
        Scaffold(
            topBar = {
                GenericAppBar(
                    title = "Create Note",
                    onIconClick = {
                        notesViewModel.createNote(
                            title = currentTitle,
                            note = currentNote,
                            imageUri = currentImage
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
                if (currentImage.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = Uri.parse(currentImage))
                                .build()
                        ),
                        contentDescription = "Note image",
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .fillMaxWidth()
                            .padding(6.dp),
                        contentScale = ContentScale.Crop,
                    )
                }

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
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(),
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