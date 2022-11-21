package cz.chm4.chmtestapp.search.searchDetail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.theme.spacing

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchDetailScreen(
    navController: NavController,
    searchEntityId: String,
    viewModel: SearchDetailViewModel = hiltViewModel()
) {
    val entity by viewModel.getSearchEntity(searchEntityId).collectAsStateWithLifecycle(SearchEntityBl.EmptyEntity)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
    ) {
        SearchEntityDisplay(entity = entity, modifier = Modifier.fillMaxSize())
    }
}
