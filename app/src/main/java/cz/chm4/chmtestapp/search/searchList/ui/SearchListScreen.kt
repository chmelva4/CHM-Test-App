package cz.chm4.chmtestapp.search.searchList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.chm4.chmtestapp.R
import cz.chm4.chmtestapp.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun SearchListScreen() {

    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("Search Text") }
    val isSearchBtnEnabled by remember { mutableStateOf(true) }
    var selectedEntity by remember { mutableStateOf(SelectedEntities.ALL) }

    val data = mapOf<Sport, List<SearchListEntity>>(
        Sport(1, R.string.sport_football) to listOf<SearchListEntity>(
            SearchListEntity("1", "EvertonFC", "https://picsum.photos/100/100", 1, 1),
            SearchListEntity("2", "Wayne Rooney", "https://picsum.photos/100/100", 1, 4),
        ),
        Sport(2, R.string.sport_hockey) to listOf(
            SearchListEntity("3", "HC Slavia Praha", null, 1, 1),
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = {scope.launch { searchText = it }},
            isSearchBtnEnabled = isSearchBtnEnabled,
            onSearchClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Divider()
//        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        FilterChips(selectedEntity = selectedEntity, onEntitySelected = {selectedEntity = it}, Modifier.fillMaxWidth())
        SearchList(data = data, onItemClick = {}, modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
    }

    
}


@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    isSearchBtnEnabled: Boolean,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(value = searchText, onValueChange = onSearchTextChanged, maxLines = 1, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        OutlinedButton(onClick = onSearchClicked, enabled = isSearchBtnEnabled) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    selectedEntity: SelectedEntities,
    onEntitySelected: (SelectedEntities) -> Unit,
    modifier: Modifier = Modifier
) {

    val selectedIcon: @Composable ()-> Unit = {
        Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = stringResource(id = R.string.filter_active_desc),
            modifier = Modifier.size(FilterChipDefaults.IconSize)
        )
    }


    Row(modifier = modifier) {
        FilterChip(
            selected = selectedEntity == SelectedEntities.ALL,
            onClick = {onEntitySelected(SelectedEntities.ALL)},
            label = { Text(stringResource(id = R.string.filter_all)) },
            selectedIcon = selectedIcon
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        FilterChip(
            selected = selectedEntity == SelectedEntities.COMPETITIONS,
            onClick = {onEntitySelected(SelectedEntities.COMPETITIONS)},
            label = { Text(stringResource(id = R.string.filter_competitions)) },
            selectedIcon = selectedIcon
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        FilterChip(
            selected = selectedEntity == SelectedEntities.PARTICIPANTS,
            onClick = {onEntitySelected(SelectedEntities.PARTICIPANTS)},
            label = { Text(stringResource(id = R.string.filter_participants)) },
            selectedIcon = selectedIcon
        )
    }
}

@Composable
fun SearchList(
    data: Map<Sport, List<SearchListEntity>>,
    onItemClick:  (SearchListEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        data.map {
            item {
                SportDivider(text = stringResource(id = it.key.name))
            }
            items(it.value) { item ->
                SportListItem(item = item, onItemClick = onItemClick)
            }
        }
    }

}

@Composable
fun SportDivider(text: String) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Black)) {
        Text(text = text, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(MaterialTheme.spacing.xxSmall))
    }
}

@Composable
fun SportListItem(
    item: SearchListEntity,
    onItemClick: (SearchListEntity) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
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
            Box(modifier = Modifier
                .size(40.dp)
                .background(Color.Gray.copy(alpha = 0.35f), CircleShape))
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        Text(text = item.name, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}