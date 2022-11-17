package cz.chm4.chmtestapp.search.searchList.ui

import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.Gender
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.SearchRepository
import cz.chm4.chmtestapp.search.common.bl.Sport
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
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
    private lateinit var dataFlow: MutableStateFlow<List<SearchEntityBl>>

    @Before
    fun setUp() {
        repoMock = mockk<SearchRepository>()
        dataFlow = MutableStateFlow(emptyList())
        every { repoMock.getAll() } returns dataFlow
        searchListViewModel = SearchListViewModel(repoMock)
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

    @Test
    fun `properly displays loading during submission`() = runTest {
        val loadingStates = mutableListOf<Boolean>()
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.isLoadingFlow.collect {loadingStates.add(it)}}

        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()

        assertEquals(3, loadingStates.size)
        assertArrayEquals(booleanArrayOf(false, true, false), loadingStates.toBooleanArray())

        job.cancel()
    }

    @Test
    fun `properly emits error event`() = runTest {
        var errorCount = 0
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.hasErrorFlow.collect {errorCount++}}
        coEvery { repoMock.search(any()) } throws  Exception("Network error")

        assertEquals(0, errorCount)

        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()
        assertEquals(1, errorCount)

        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()
        assertEquals(2, errorCount)

        job.cancel()
    }

    @Test
    fun `properly calls search with search text`() = runTest {
        searchListViewModel.setSearchText("Wimbledon")

        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()

        coVerify { repoMock.search("Wimbledon") }
    }

    @Test
    fun `properly parses data`() = runTest {
        val player = SearchEntityBl("1", "name1", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "country1", null)
        var state: Map<Sport, List<SearchListEntity>> = emptyMap()
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.dataFlow.collect {state = it}}

        assertTrue(state.isEmpty())

        dataFlow.emit(listOf(player))
        advanceUntilIdle()

        val result = state[Sport.FOOTBALL]?.get(0)

        assertEquals(player.id, result?.id)
        assertEquals(player.name, result?.name)
        assertEquals(player.type, result?.type)
        assertEquals(player.sport, result?.sport)
        assertEquals(player.image, result?.avatarUrl)

        job.cancel()
    }

    @Test
    fun `properly groups data by sport`() = runTest {
        val multiSportData = listOf(
            SearchEntityBl("1", "name1", Gender.MEN, EntityType.COMPETITION, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("2", "name2", Gender.MEN, EntityType.TEAM, Sport.HOCKEY, "country1", null),
        )

        var state: Map<Sport, List<SearchListEntity>> = emptyMap()
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.dataFlow.collect {state = it}}
        dataFlow.emit(multiSportData)
        advanceUntilIdle()

        val player1 = state[Sport.FOOTBALL]?.get(0)
        val player2 = state[Sport.HOCKEY]?.get(0)

        assertEquals(2, state.keys.size)
        assertEquals(multiSportData[0].id, player1?.id)
        assertEquals(multiSportData[1].id, player2?.id)

        job.cancel()
    }

    @Test
    fun `properly filters data by entity type`() = runTest {
        val singleSportData = listOf(
            SearchEntityBl("1", "name1", Gender.MEN, EntityType.COMPETITION, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("2", "name2", Gender.MEN, EntityType.TEAM, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("3", "name3", Gender.MEN, EntityType.TEAM_PLAYER, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("4", "name4", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "country1", null),
        )
        var state: Map<Sport, List<SearchListEntity>> = emptyMap()
        val job = launch( UnconfinedTestDispatcher()) {searchListViewModel.dataFlow.collect {state = it}}

        dataFlow.emit(singleSportData)
        advanceUntilIdle()

        var players = state[Sport.FOOTBALL]
        assertEquals(4, players?.size)

        searchListViewModel.setEntityFilter(SearchFilter.COMPETITIONS)
        advanceUntilIdle()
        players = state[Sport.FOOTBALL]
        assertEquals(1, players?.size)
        assertEquals("1", players?.get(0)?.id)

        searchListViewModel.setEntityFilter(SearchFilter.PARTICIPANTS)
        advanceUntilIdle()
        players = state[Sport.FOOTBALL]
        assertEquals(3, players?.size)
        assertEquals("2", players?.get(0)?.id)
        assertEquals("3", players?.get(1)?.id)
        assertEquals("4", players?.get(2)?.id)

        job.cancel()
    }
}