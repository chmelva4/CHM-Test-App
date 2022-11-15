package cz.chm4.chmtestapp.search.searchList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.SearchRepository
import cz.chm4.chmtestapp.search.common.bl.Sport
import cz.chm4.chmtestapp.search.common.data.network.liveSportApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SearchListViewModel: ViewModel() {

    private val repository = SearchRepository(liveSportApi)

    private val _entityFilterFlow = MutableStateFlow(SearchFilter.ALL)
    val entityFilterFlow = _entityFilterFlow.asStateFlow()

    private val _searchTextFlow = MutableStateFlow("")
    val searchTextFlow = _searchTextFlow.asStateFlow()


    private val _allDataFlow = MutableStateFlow<Map<Sport, List<SearchListEntity>>>(mapOf())

    val dataFlow = combine(entityFilterFlow, _allDataFlow) { filter, data ->
        when (filter) {
            SearchFilter.ALL -> data
            SearchFilter.COMPETITIONS -> data.mapValues { it.value.filter { item -> item.type == EntityType.COMPETITION } }
            SearchFilter.PARTICIPANTS -> data.mapValues { it.value.filter { item -> item.type != EntityType.COMPETITION} }
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

    fun setEntityFilter(selectedEntity: SearchFilter) {
        viewModelScope.launch {
            _entityFilterFlow.emit(selectedEntity)
        }
    }

    fun onSearchButtonClicked() {
        viewModelScope.launch {
           _isLoadingFlow.emit(true)

            val res = repository.search(searchTextFlow.value, entityFilterFlow.value)
            val data = res.map { SearchListEntity(it.id, it.name, it.image, it.sport, it.type) }.groupBy { it.sport }
            _allDataFlow.emit(data)
            _isLoadingFlow.emit(false)
        }
    }


}