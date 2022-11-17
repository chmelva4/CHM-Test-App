package cz.chm4.chmtestapp.navigation


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.chm4.chmtestapp.search.searchDetail.ui.SearchDetailScreen
import cz.chm4.chmtestapp.search.searchList.ui.SearchListScreen

@Composable
fun CHMTestAppNavHost(navController: NavHostController, snackbarHostState: SnackbarHostState, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screens.SearchList.route, modifier= modifier ) {

        composable(Screens.SearchList.route) {
            SearchListScreen(navController, snackbarHostState)
        }

        composable(Screens.SearchItemDetail.route) {
            SearchDetailScreen(navController, it.arguments?.getString("id")?:"")
        }
    }
}