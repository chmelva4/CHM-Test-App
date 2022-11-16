package cz.chm4.chmtestapp.search.searchDetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.theme.spacing

@Composable
fun SearchEntityDisplay(entity: SearchEntityBl) {

    Row(modifier = Modifier.fillMaxSize()) {
        EntityImage(imageUrl = entity.image)
        Column(modifier = Modifier.fillMaxHeight().weight(1f).padding(horizontal = MaterialTheme.spacing.medium)) {

        }
    }
}