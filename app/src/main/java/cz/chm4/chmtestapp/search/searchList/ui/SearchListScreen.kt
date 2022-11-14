package cz.chm4.chmtestapp.search.searchList.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.chm4.chmtestapp.R
import cz.chm4.chmtestapp.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun SearchListScreen() {

    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("Search Text") }
    val isSearchBtnEnabled by remember { mutableStateOf(true) }

    Column(modifier = Modifier.padding(16.dp)) {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = {scope.launch { searchText = it }},
            isSearchBtnEnabled = isSearchBtnEnabled,
            onSearchClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
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
        OutlinedTextField(value = searchText, onValueChange = onSearchTextChanged, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        OutlinedButton(onClick = onSearchClicked, enabled = isSearchBtnEnabled) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}