package cz.chm4.chmtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.chm4.chmtestapp.navigation.CHMTestAppNavHost
import cz.chm4.chmtestapp.navigation.Screens
import cz.chm4.chmtestapp.theme.CHMTestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    CHMTestAppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                SmallTopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        if (navBackStackEntry?.destination?.route != Screens.SearchList.route) {
                            IconButton(onClick = {
                                scope.launch { navController.navigateUp() }
                            }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                            }
                        } else {
                            null
                        }
                    }
                )
            }
        ) {
            CHMTestAppNavHost(
                navController = navController,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.padding(it)
            )
        }
    }
}
