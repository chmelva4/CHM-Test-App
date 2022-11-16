package cz.chm4.chmtestapp.search.searchList.ui

import cz.chm4.chmtestapp.search.common.bl.SearchRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchListViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var repoMock: SearchRepository
    private lateinit var searchListViewModel: SearchListViewModel

    @Before
    fun setUp() {
        repoMock = mockk<SearchRepository>()
        searchListViewModel = SearchListViewModel(repoMock)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `properly stores search text in state`() = runTest {
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.searchTextFlow.collect{}}
        assertEquals("", searchListViewModel.searchTextFlow.value)

        searchListViewModel.setSearchText("Wimbledon")
        advanceUntilIdle()
        assertEquals("Wimbledon", searchListViewModel.searchTextFlow.value)

        job.cancel()
    }

    @Test
    fun `properly stores filter value in state`() = runTest {
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.entityFilterFlow.collect{}}
        assertEquals(SearchFilter.ALL, searchListViewModel.entityFilterFlow.value)

        searchListViewModel.setEntityFilter(SearchFilter.COMPETITIONS)
        advanceUntilIdle()
        assertEquals(SearchFilter.COMPETITIONS, searchListViewModel.entityFilterFlow.value)

        searchListViewModel.setEntityFilter(SearchFilter.PARTICIPANTS)
        advanceUntilIdle()
        assertEquals(SearchFilter.PARTICIPANTS, searchListViewModel.entityFilterFlow.value)

        job.cancel()
    }

    fun `properly displays loading during submission`() = runTest {
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.isLoadingFlow.collect{}}
//        every { repoMock.search(any) }
        

        assertEquals(false, searchListViewModel.entityFilterFlow.value)

        searchListViewModel.setEntityFilter(SearchFilter.COMPETITIONS)
        advanceUntilIdle()
        assertEquals(SearchFilter.COMPETITIONS, searchListViewModel.entityFilterFlow.value)

        searchListViewModel.setEntityFilter(SearchFilter.PARTICIPANTS)
        advanceUntilIdle()
        assertEquals(SearchFilter.PARTICIPANTS, searchListViewModel.entityFilterFlow.value)

        job.cancel()
    }
}