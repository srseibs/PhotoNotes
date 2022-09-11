package com.sailinghawklabs.photonotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sailinghawklabs.photonotes.ui.noteCreate.NoteCreateScreen
import com.sailinghawklabs.photonotes.ui.noteDetail.NoteDetailScreen
import com.sailinghawklabs.photonotes.ui.noteEdit.NoteEditScreen
import com.sailinghawklabs.photonotes.ui.noteList.NoteListScreen
import com.sailinghawklabs.photonotes.ui.theme.PhotoNotesTheme

// DBTechProjects  Daniel Butler
//  https://www.youtube.com/watch?v=eugou_G_YvY

// things to look at later:
//   + Use 'by' delegation
//   + Add Hilt
//   + Use of Theme in each screen vs once in the activity
//   + Passing of MutableState vars into sub-composables vs simple vars.
//   + Passing NavController into sub-composables vs onExit() Lambda hoisting.

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = NoteViewModelFactory(PhotoNotesApp.getDao()).create(NotesViewModel::class.java)

        setContent {
            PhotoNotesTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Constants.defineNoteListRoute()
                ) {

                    // Notes List
                    composable(Constants.defineNoteListRoute()) {
                        NoteListScreen(navController, viewModel)
                    }

                    // Note detail page
                    composable(
                        Constants.defineNoteDetailRoute(),
                        arguments = listOf(
                            navArgument(Constants.NOTES_ARGUMENT_ID) { type = IntType }
                        )
                    ) { navBackStackEntry ->
                        navBackStackEntry.arguments?.getInt(Constants.NOTES_ARGUMENT_ID)?.let {
                            NoteDetailScreen(
                                noteId = it,
                                navController = navController, viewModel = viewModel
                            )
                        }
                    }

                    // Note edit page
                    composable(
                        Constants.defineNoteEditRoute(),
                        arguments = listOf(
                            navArgument(Constants.NOTES_ARGUMENT_ID) { type = IntType }
                        )
                    ) { navBackStackEntry ->
                        navBackStackEntry.arguments?.getInt(Constants.NOTES_ARGUMENT_ID)?.let {
                            NoteEditScreen(
                                noteId = it,
                                navController = navController, viewModel = viewModel
                            )
                        }
                    }

                    // Note Create page
                    composable(Constants.defineNoteCreateRoute()) {
                        NoteCreateScreen(navController = navController, viewModel = viewModel)
                    }

                }
            }
        }
    }
}
