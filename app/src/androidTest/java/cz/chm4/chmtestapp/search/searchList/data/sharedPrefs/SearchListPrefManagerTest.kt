package cz.chm4.chmtestapp.search.searchList.data.sharedPrefs

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cz.chm4.chmtestapp.search.searchList.ui.SearchFilter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchListPrefManagerTest {

    lateinit var searchListPrefManager: SearchListPrefManager

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        searchListPrefManager = SearchListPrefManager("test1", appContext)
    }

    @Test
    fun testSharedPrefs() {
       //GIVEN

        //WHEN
        val first = searchListPrefManager.restoreData()
        searchListPrefManager.saveData("text1", SearchFilter.PARTICIPANTS)
        val second = searchListPrefManager.restoreData()

        //THEN
        assertEquals("", first.first)
        assertEquals(SearchFilter.ALL, first.second)
        assertEquals("text1", second.first)
        assertEquals(SearchFilter.PARTICIPANTS, second.second)
    }
}