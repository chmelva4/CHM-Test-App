package cz.chm4.chmtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cz.chm4.chmtestapp.navigation.CHMTestAppNavHost

import cz.chm4.chmtestapp.theme.CHMTestAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CHMTestApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CHMTestApp() {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState()}

    CHMTestAppTheme {
        Scaffold(snackbarHost = {SnackbarHost(snackbarHostState)}) {
            CHMTestAppNavHost(
                navController = navController,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.padding(it)
            )
        }
    }
}