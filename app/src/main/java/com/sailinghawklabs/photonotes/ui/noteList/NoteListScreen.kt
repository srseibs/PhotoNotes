package com.sailinghawklabs.photonotes.ui.noteList

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FolderDelete
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.FolderDelete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sailinghawklabs.photonotes.Constants
import com.sailinghawklabs.photonotes.NotesViewModel
import com.sailinghawklabs.photonotes.model.Note
import com.sailinghawklabs.photonotes.model.getDay
import com.sailinghawklabs.photonotes.model.orPlaceHolder
import com.sailinghawklabs.photonotes.ui.GenericAppBar
import com.sailinghawklabs.photonotes.ui.theme.PhotoNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // TODO make this by
    var searchQuery = remember { mutableStateOf("") }

    // states for the Delete dialog
    var deletePrompt by remember { mutableStateOf("") }
    var notesToDelete by remember { mutableStateOf(listOf<Note>()) }
    var dialogOpenState by remember { mutableStateOf(false) }

    fun showDeleteDialog(notes: List<Note>, prompt: String) {
        deletePrompt = prompt
        notesToDelete = notes
        dialogOpenState = true
    }


    val noteList by viewModel.notes.observeAsState()


    PhotoNotesTheme {
        Scaffold(
            topBar = {
                GenericAppBar(
                    title = "Photo Notes",
                    onIconClick = {
                        if (noteList?.isNotEmpty() == true) {
                            showDeleteDialog(
                                notes = noteList ?: emptyList(),
                                "Are you sure you want to delete all notes?"
                            )
                        } else {
                            Toast.makeText(context, "No notes found", Toast.LENGTH_SHORT).show()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = "Delete notes"
                        )
                    },
                    iconState = remember { mutableStateOf(true) }
                )
            },
            floatingActionButton = {
                NotesFab(
                    contentDescription = "New Note",
                    action = {
                        navController.navigate(Constants.noteCreateRoute())
                    },
                    icon = Icons.Default.Add
                )
            }

        ) { contentPadding ->

            Column(
                modifier = modifier.padding(contentPadding)
            ) {
                SearchBar(query = searchQuery.value, onQueryChanged = { searchQuery.value = it })
                NotesList(
                    notes = noteList.orPlaceHolder(),
                    onDeleteNotes = { notesList, prompt ->
                        showDeleteDialog(notesList, prompt)
                    },
                    query = searchQuery,
                    navController = navController,
                )
                if (dialogOpenState) {
                    DeleteDialog(
                        text = deletePrompt,
                        cancelPressed = {},
                        deletePressed = {
                            notesToDelete.forEach {
                                viewModel.deleteNote(it)
                            }
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .padding(horizontal = 12.dp),
    ) {
        TextField(
            value = query,
            placeholder = { Text(text = "Search...") },
            maxLines = 1,
            onValueChange = { onQueryChanged(it) },
            modifier = Modifier
//                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(),
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.isNotEmpty()
                ) {
                    IconButton(onClick = { onQueryChanged("") }) {
                        Icon(Icons.Outlined.Cancel, contentDescription = "Cancel")
                    }
                }
            }
        )
    }
}

@Composable
fun NotesList(
    notes: List<Note>,
    onDeleteNotes: (notes: List<Note>, prompt: String) -> Unit,
    query: MutableState<String>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var previousHeader = ""
    val queriedNotes = remember(key1 = query) {
        if (query.value.isEmpty()) {
            notes
        } else {
            notes.filter { it.note.contains(query.value) || it.title.contains(query.value) }
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = modifier
    ) {
        itemsIndexed(items = queriedNotes) { index, note ->
            if (note.getDay() != previousHeader) {
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = note.getDay(), style = MaterialTheme.typography.titleSmall)
                }
                Spacer(modifier = Modifier.height(6.dp))

                previousHeader = note.getDay()
            }

            NotesListItem(
                note = note,
                onDelete = {
                    onDeleteNotes(listOf(note), "Are you sure you want to delete this note?")
                },
                noteBackgroundColor = if (index % 2 == 0) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                navController = navController,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesListItem(
    note: Note,
    onDelete: () -> Unit,
    navController: NavController,
    noteBackgroundColor: Color
) {

    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = noteBackgroundColor
        ),
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = {
                        navController.navigate(Constants.noteDetailRouteWithParam(note.id ?: 0))
                    },
                    onLongClick = {
                        if (note.id != 0) {
                            onDelete()
                        }
                    },
                )
        ) {
            Row {
                if (!note.imageUri.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest
                                .Builder(context)
                                .data(Uri.parse(note.imageUri))
                                .build()
                        ),
                        contentDescription = "Image for a note",
                        modifier = Modifier.fillMaxWidth(0.3f),
                        contentScale = ContentScale.Crop,
                    )
                }
                Column() {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = note.note,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = note.dateUpdated,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Composable
fun NotesFab(
    contentDescription: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    action: () -> Unit,
) {
    return FloatingActionButton(
        modifier = modifier,
        onClick = action,
    )
    {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Composable
fun DeleteDialog(
    text: String,
    modifier: Modifier = Modifier,
    cancelPressed: () -> Unit = { },
    deletePressed: () -> Unit,
) {

    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = { showDialog = false },
            title = {
                Text("Delete Note", modifier = Modifier.padding(horizontal = 12.dp))
            },
            text = {
                Text(text)
            },
            confirmButton = {
                TextButton(onClick = deletePressed) {
                    Text("Delete", style = MaterialTheme.typography.labelMedium)
                }
            },
            dismissButton = {
                TextButton(onClick = cancelPressed) {
                    Text("Cancel", style = MaterialTheme.typography.labelSmall)
                }
            },
        )
    }

}


@Preview(showSystemUi = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchPreview() {

    var search by remember { mutableStateOf("Tennis") }

    PhotoNotesTheme() {
        SearchBar(query = search, onQueryChanged = { search = it })
    }

}

@Preview(showSystemUi = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DeleteDialogPreview() {
    PhotoNotesTheme() {
        DeleteDialog(
            text = "How about we delete this note? This area typically contains the supportive text " +
                    "which presents the details regarding the Dialog's purpose."
        )
        {
            // action on delete pressed
        }
    }


}