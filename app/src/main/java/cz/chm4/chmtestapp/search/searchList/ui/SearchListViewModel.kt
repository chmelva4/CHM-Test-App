package cz.chm4.chmtestapp.search.searchList.ui

import androidx.lifecycle.ViewModel
import cz.chm4.chmtestapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchListViewModel: ViewModel() {

    private val _entityFilterFlow = MutableStateFlow(SelectedEntities.ALL)
    val entityFilterFlow = _entityFilterFlow.asStateFlow()

    private val _searchTextFlow = MutableStateFlow("")
    val searchTextFlow = _searchTextFlow.asStateFlow()


    private val _allDataFlow = MutableStateFlow(mapOf<Sport, List<SearchListEntity>>(
        Sport(1, R.string.sport_football) to listOf<SearchListEntity>(
            SearchListEntity("1", "EvertonFC", "https://picsum.photos/100/100", 1, 1),
            SearchListEntity("2", "Wayne Rooney", "https://picsum.photos/100/100", 1, 4),
        ),
        Sport(2, R.string.sport_hockey) to listOf(
            SearchListEntity("3", "HC Slavia Praha", null, 1, 1),
        )
    ))
    val allDataFlow = _allDataFlow.asStateFlow()

}