package com.sailinghawklabs.photonotes.ui.noteDetail

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sailinghawklabs.photonotes.NotesViewModel
import com.sailinghawklabs.photonotes.ui.GenericAppBar
import com.sailinghawklabs.photonotes.util.Constants
import com.sailinghawklabs.photonotes.util.Constants.noteDetailPlaceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    var note by remember {
        mutableStateOf(noteDetailPlaceHolder)
    }

    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            note = notesViewModel.getNote(noteId) ?: noteDetailPlaceHolder
        }
    }

    Scaffold(
        topBar = {
            GenericAppBar(
                title = note.title,
                onIconClick = {
                    navController.navigate(
                        Constants.callNoteEditRouteWithParam(
                            noteId
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit note"
                    )
                },
                iconState = true
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        )
        {
            note.let { note ->
                if (!note.imageUri.isNullOrEmpty()) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(data = Uri.parse(note.imageUri))
                                .build()
                        ),
                        contentDescription = "Note image",
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                }

                Text(
                    text = note.title,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                )
                Text(
                    text = note.dateUpdated,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = note.note,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp)
                )


            }
        }

    }
}