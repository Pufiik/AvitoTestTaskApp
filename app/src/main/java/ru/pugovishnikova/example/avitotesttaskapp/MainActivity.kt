package ru.pugovishnikova.example.avitotesttaskapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.outlined.Downloading
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.pugovishnikova.example.avitotesttaskapp.navigation.BottomNavigationItem
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.DownloadScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.DownloadViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListEvent
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.ObserveAsEvents
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.toString
import ru.pugovishnikova.example.avitotesttaskapp.presentation.track_details.TrackDetailScreen
import ru.pugovishnikova.example.avitotesttaskapp.ui.theme.AvitoTestTaskAppTheme
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = listOf(
            BottomNavigationItem(
                title = Utils.getDownloadScreen(),
                selectedIcon = Icons.Filled.Downloading,
                unSelectedIcon = Icons.Outlined.Downloading
            ),
            BottomNavigationItem(
                title = Utils.getTrackListScreen(),
                selectedIcon = Icons.Filled.MusicNote,
                unSelectedIcon = Icons.Outlined.MusicNote
            )
        )


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        enableEdgeToEdge()
        setContent {
            AvitoTestTaskAppTheme {
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(1)
                }

                val controller = rememberNavController()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        controller.navigate(item.title)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unSelectedIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val trackViewModel = koinViewModel<TrackViewModel>()
                    val state = trackViewModel.state.collectAsStateWithLifecycle()
                    val context = LocalContext.current

                    ObserveAsEvents(events = trackViewModel.events) { event ->
                        when (event) {
                            is TrackListEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    event.error.toString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    NavHost(
                        navController = controller,
                        startDestination = Utils.getTrackListScreen()
                    ) {
                        composable(Utils.getTrackListScreen()) {
                            TrackListScreen(
                                state = state.value,
                                onAction = { action ->
                                    trackViewModel.onAction(action)
                                },
                                onClick = { controller.navigate(Utils.getTrackDetailScreen()) }
                            )
                        }
                        composable(Utils.getTrackDetailScreen()) {
                            TrackDetailScreen(
                                state = state.value,
                                onAction = { action ->
                                    trackViewModel.onAction(action)
                                },
                                onClick = controller::popBackStack,
                                modifier = Modifier.padding(innerPadding)
                            ) { controller.navigate(Utils.getTrackListScreen()) }
                        }
                        composable(Utils.getDownloadScreen()) {
                            val downLoadViewModel = koinViewModel<DownloadViewModel>()
                            val downloadState = downLoadViewModel.state.collectAsStateWithLifecycle()
                            DownloadScreen(
                                state = downloadState.value,
                                onAction = { action ->
                                    downLoadViewModel.onAction(action)
                                },
                                onClick = { controller.navigate(Utils.getTrackDetailScreen()) }
                            )
                        }
                    }
                }
            }
        }
    }
}
