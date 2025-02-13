package ru.pugovishnikova.example.avitotesttaskapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.pugovishnikova.example.avitotesttaskapp.navigation.AdaptiveTrackListDetailPane
import ru.pugovishnikova.example.avitotesttaskapp.ui.theme.AvitoTestTaskAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            AvitoTestTaskAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdaptiveTrackListDetailPane(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
