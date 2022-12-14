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

// I added latest versions of everything.
//       Material 3 since I want to practice more with it.
//       Use extended Icons instead of creating our own.

// changes after completion of video:
//   X Stop passing MutableState vars into sub-composable vs {simple vars + Lambda}.
//      Improves preview and testability.
//   X Use 'by' delegation - no need for .value Using "=" was necessary since we were passing
//     the MutableState into sub-composables.
//   X Add Hilt - no need for MainActivity to know anything about the view model
//   X Delete use of Theme in each screen vs once in MainActivity

//   X Stop passing NavController into sub-composable vs onAction() Lambda hoisting.
//      Improve testability.
//   X My theme is ugly...  done; well, less ugly.

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
                        NoteListScreen(
                            onCreateClicked = {
                                navController.navigate(Constants.defineNoteCreateRoute())
                            },
                            onDetailClick = {
                                navController.navigate(Constants.noteDetailNavigation(it))
                            }
                        )
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
                                onEditClicked = {
                                    navController.navigate(Constants.callNoteEditRouteWithParam(it))
                                }
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
                                onBackPressed = {navController.popBackStack()  }
                            )
                        }
                    }

                    // Note Create page
                    composable(Constants.defineNoteCreateRoute()) {
                        NoteCreateScreen(
                            onCreated = { navController.popBackStack() }
                        )
                    }

                }
            }
        }
    }
}
