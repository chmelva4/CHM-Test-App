package cz.chm4.chmtestapp.search.searchDetail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SearchDetailScreen(
    navController: NavController,
    searchEntityId: String,
    viewModel: SearchDetailViewModel = hiltViewModel()
) {
    Text(text = "Im search detail with id: $searchEntityId")
}