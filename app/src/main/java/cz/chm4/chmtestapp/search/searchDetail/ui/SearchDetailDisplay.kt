package cz.chm4.chmtestapp.search.searchDetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.chm4.chmtestapp.R
import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.Gender
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.Sport
import cz.chm4.chmtestapp.theme.spacing

@Composable
fun SearchEntityDisplay(entity: SearchEntityBl, modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        EntityBanner(imageUrl = entity.image, name = entity.name, type = entity.type, sport = entity.sport)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        val genderText = when (entity.gender) {
            Gender.MEN -> stringResource(id = R.string.gender_men)
            Gender.WOMEN -> stringResource(id = R.string.gender_women)
            Gender.MIX -> stringResource(id = R.string.gender_mix)
        }
        TextItem(modifier = Modifier.fillMaxWidth(0.6f), label = stringResource(id = R.string.label_category), text = genderText)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        TextItem(modifier = Modifier.fillMaxWidth(0.6f), label = stringResource(id = R.string.label_country), text = entity.country)
    }

}

@Composable
fun TextItem(
    label: String,
    text: String,
    modifier: Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun EntityBanner(
    imageUrl: String?,
    name: String,
    type: EntityType,
    sport: Sport,
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        EntityImage(imageUrl = imageUrl)
        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = MaterialTheme.spacing.medium)) {
            Text(text = name, style = MaterialTheme.typography.displayMedium)
            Divider()
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                val sportName = when(sport) {
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
                Text(text = sportName, style = MaterialTheme.typography.titleMedium)
                val entityTypeText = when (type) {
                    EntityType.COMPETITION -> stringResource(id = R.string.type_competition)
                    EntityType.TEAM -> stringResource(id = R.string.type_team)
                    EntityType.SINGLE_PLAYER -> stringResource(id = R.string.type_player)
                    EntityType.TEAM_PLAYER -> stringResource(id = R.string.type_team_player)
                }
                Text(text = entityTypeText, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun EntityImage(imageUrl: String?) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.item_avatar_desc),
//            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
    } else {
        Box(modifier = Modifier
            .size(120.dp)
            .background(Color.Gray.copy(alpha = 0.35f), CircleShape))
    }
}