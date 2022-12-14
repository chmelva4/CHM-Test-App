package cz.chm4.chmtestapp.search.searchList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cz.chm4.chmtestapp.R
import cz.chm4.chmtestapp.navigation.Screens
import cz.chm4.chmtestapp.search.common.bl.Sport
import cz.chm4.chmtestapp.theme.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchListScreen(navController: NavController, snackbarHostState: SnackbarHostState, viewModel: SearchListViewModel = hiltViewModel()) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    val searchText by viewModel.searchTextFlow.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoadingFlow.collectAsStateWithLifecycle()
    val activeFilter by viewModel.entityFilterFlow.collectAsStateWithLifecycle()
    val data by viewModel.dataFlow.collectAsStateWithLifecycle(emptyMap())

    LaunchedEffect(viewModel) {
        this.launch {
            viewModel.hasErrorFlow.collect {
                val result = snackbarHostState.showSnackbar(
                    ctx.getString(R.string.search_error), ctx.getString(R.string.action_refresh)
                )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.onSearchButtonClicked()
                }
            }
        }
    }

    DisposableEffect(viewModel) {
        viewModel.loadSharedPrefsData()
        onDispose {
            viewModel.saveSharedPrefsData()
        }
    }

    Column(Modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        Column(modifier = Modifier.padding(16.dp).weight(1f)) {

            SearchBar(
                searchText = searchText,
                onSearchTextChanged = viewModel::setSearchText,
                isSearchBtnEnabled = !isLoading && searchText.isNotBlank(),
                onSearchClicked = viewModel::onSearchButtonClicked,
                onDeleteTextClicked = viewModel::deleteSearchText,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Divider()
            FilterChips(selectedEntity = activeFilter, onEntitySelected = viewModel::setEntityFilter, Modifier.fillMaxWidth())

            SearchList(
                data = data,
                onItemClick = {
                    scope.launch { navController.navigate(Screens.SearchItemDetail.route.replace("{id}", it.id)) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    isSearchBtnEnabled: Boolean,
    onSearchClicked: () -> Unit,
    onDeleteTextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val trailingIcon: @Composable () -> Unit = if (searchText.isNotEmpty()) {
            {
                IconButton(onClick = onDeleteTextClicked) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(id = R.string.btn_delete_desc))
                }
            }
        } else {
            {}
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearchClicked()
                }
            ),
            trailingIcon = trailingIcon
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        OutlinedButton(onClick = {
            keyboardController?.hide()
            onSearchClicked()
        }, enabled = isSearchBtnEnabled) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    selectedEntity: SearchFilter,
    onEntitySelected: (SearchFilter) -> Unit,
    modifier: Modifier = Modifier
) {

    val selectedIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = stringResource(id = R.string.filter_active_desc),
            modifier = Modifier.size(FilterChipDefaults.IconSize)
        )
    }

    Row(modifier = modifier) {
        FilterChip(
            selected = selectedEntity == SearchFilter.ALL,
            onClick = { onEntitySelected(SearchFilter.ALL) },
            label = { Text(stringResource(id = R.string.filter_all)) },
            selectedIcon = selectedIcon
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        FilterChip(
            selected = selectedEntity == SearchFilter.COMPETITIONS,
            onClick = { onEntitySelected(SearchFilter.COMPETITIONS) },
            label = { Text(stringResource(id = R.string.filter_competitions)) },
            selectedIcon = selectedIcon
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        FilterChip(
            selected = selectedEntity == SearchFilter.PARTICIPANTS,
            onClick = { onEntitySelected(SearchFilter.PARTICIPANTS) },
            label = { Text(stringResource(id = R.string.filter_participants)) },
            selectedIcon = selectedIcon
        )
    }
}

@Composable
fun SearchList(
    data: Map<Sport, List<SearchListEntity>>,
    onItemClick: (SearchListEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        data.map {
            if (it.value.isNotEmpty())
                item {
                    val sportName = when (it.key) {
                        Sport.FOOTBALL -> stringResource(id = R.string.sport_football)
                        Sport.TENNIS -> stringResource(id = R.string.sport_tennis)
                        Sport.BASKETBALL -> stringResource(id = R.string.sport_basketball)
                        Sport.HOCKEY -> stringResource(id = R.string.sport_hockey)
                        Sport.AMERICAN_FOOTBALL -> stringResource(id = R.string.sport_american_football)
                        Sport.BASEBALL -> stringResource(id = R.string.sport_baseball)
                        Sport.HANDBALL -> stringResource(id = R.string.sport_handball)
                        Sport.RUGBY -> stringResource(id = R.string.sport_rugby)
                        Sport.FLOORBALL -> stringResource(id = R.string.sport_floorball)
                    }
                    SportDivider(text = sportName)
                }
            items(it.value) { item ->
                SportListItem(item = item, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun SportDivider(text: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
    ) {
        Text(text = text, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(MaterialTheme.spacing.xxSmall))
    }
}

@Composable
fun SportListItem(
    item: SearchListEntity,
    onItemClick: (SearchListEntity) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.xSmall)
            .clickable { onItemClick(item) }
    ) {
        if (item.avatarUrl != null) {
            AsyncImage(
                model = item.avatarUrl,
                contentDescription = stringResource(id = R.string.item_avatar_desc),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray.copy(alpha = 0.35f), CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        Text(text = item.name, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}
