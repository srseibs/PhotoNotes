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
import com.sailinghawklabs.photonotes.util.Constants
import dagger.hilt.android.AndroidEntryPoint

// DBTechProjects  Daniel Butler
//  https://www.youtube.com/watch?v=eugou_G_YvY

// things to look at later:
//   X Use 'by' delegation - no need for .value
//   X Add Hilt - no need for MainActivity to know about the view model
//   X Delete use of Theme in each screen vs once in the activity
//   X Passing of MutableState vars into sub-composables vs {simple vars + Lambda}.
//   O Stop passing NavController into sub-composables vs onAction() Lambda hoisting.

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotoNotesTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Constants.defineNoteListRoute()
                ) {

                    // Notes List
                    composable(Constants.defineNoteListRoute()) {
                        NoteListScreen(navController)
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
                                navController = navController
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
                                navController = navController
                            )
                        }
                    }

                    // Note Create page
                    composable(Constants.defineNoteCreateRoute()) {
                        NoteCreateScreen(navController = navController)
                    }

                }
            }
        }
    }
}
