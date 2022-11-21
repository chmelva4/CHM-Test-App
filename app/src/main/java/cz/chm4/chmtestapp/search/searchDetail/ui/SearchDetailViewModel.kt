package cz.chm4.chmtestapp.search.searchDetail.ui

import androidx.lifecycle.ViewModel
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.searchDetail.bl.SearchDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val searchDetailRepository: SearchDetailRepository
) : ViewModel() {

    fun getSearchEntity(id: String): Flow<SearchEntityBl> = searchDetailRepository.getSearchEntityById(id)
}
