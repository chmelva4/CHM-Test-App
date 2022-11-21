package cz.chm4.chmtestapp.search.searchList.data.sharedPrefs

import android.content.Context
import cz.chm4.chmtestapp.search.searchList.ui.SearchFilter

class SearchListPrefManager(
    private val context: Context
) {

    fun saveData(searchText: String, searchFilter: SearchFilter) {
        val sp = context.getSharedPreferences(SearchListSharedPrefConstants.FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(SearchListSharedPrefConstants.SEARCH_TEXT_KEY, searchText)
        editor.putString(SearchListSharedPrefConstants.FILTER_KEY, searchFilter.toString())
        editor.apply()
    }

    fun restoreData(): Pair<String, SearchFilter> {
        val sp = context.getSharedPreferences(SearchListSharedPrefConstants.FILE_NAME, Context.MODE_PRIVATE)
        val searchText = sp.getString(SearchListSharedPrefConstants.SEARCH_TEXT_KEY, "") ?: ""
        val searchFilter = SearchFilter.valueOf(sp.getString(SearchListSharedPrefConstants.FILTER_KEY, SearchFilter.ALL.toString()) ?: SearchFilter.ALL.toString())
        return Pair(searchText, searchFilter)
    }
}
