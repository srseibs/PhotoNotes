package com.sailinghawklabs.photonotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                    startDestination = Constants.NOTES_LIST_ROUTE
                ) {
                    // Notes List
                    composable(Constants.NOTES_LIST_ROUTE) {
                        NoteListScreen(navController, viewModel)
                    }

                    // Note detail page
                    composable(
                        Constants.NOTE_DETAIL_ROUTE,
                        arguments = listOf(navArgument(Constants.NOTES_ARGUMENT_ID) { IntType })
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
                        Constants.NOTE_DETAIL_ROUTE,
                        arguments = listOf(navArgument(Constants.NOTES_ARGUMENT_ID) { IntType })
                    ) { navBackStackEntry ->
                        navBackStackEntry.arguments?.getInt(Constants.NOTES_ARGUMENT_ID)?.let {
                            NoteEditScreen(
                                noteId = it,
                                navController = navController, viewModel = viewModel
                            )
                        }
                    }

                    // Note Create page
                    composable(Constants.NOTE_CREATE_ROUTE) {
                        NoteCreateScreen(navController = navController, viewModel = viewModel)
                    }

                }
            }
        }
    }
}
