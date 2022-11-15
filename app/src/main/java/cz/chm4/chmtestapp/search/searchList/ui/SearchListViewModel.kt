package cz.chm4.chmtestapp.search.searchList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.chm4.chmtestapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

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

    val dataFlow = combine(entityFilterFlow, _allDataFlow) { filter, data ->
        when (filter) {
            SelectedEntities.ALL -> data
            SelectedEntities.COMPETITIONS -> data.mapValues { it.value.filter { item -> item.type == 1 } }
            SelectedEntities.PARTICIPANTS -> data.mapValues { it.value.filter { item -> item.type > 1 } }
        }
    }

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _hasErrorFlow = MutableStateFlow(false)
    val hasErrorFlow = _hasErrorFlow.asStateFlow()


    fun setSearchText(text: String) {
        viewModelScope.launch {
            _searchTextFlow.emit(text)
        }
    }

    fun setEntityFilter(selectedEntity: SelectedEntities) {
        viewModelScope.launch {
            _entityFilterFlow.emit(selectedEntity)
        }
    }

    fun onSearchButtonClicked() {
        viewModelScope.launch {
            _isLoadingFlow.emit(true)
            delay(1500)
            _searchTextFlow.emit("")
            _isLoadingFlow.emit(false)
        }
    }


}