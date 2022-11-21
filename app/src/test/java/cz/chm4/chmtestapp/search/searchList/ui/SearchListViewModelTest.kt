package cz.chm4.chmtestapp.search.searchList.ui

import cz.chm4.chmtestapp.MainCoroutineRule
import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.Gender
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.Sport
import cz.chm4.chmtestapp.search.searchList.bl.SearchListRepository
import cz.chm4.chmtestapp.search.searchList.data.sharedPrefs.SearchListPrefManager
import io.mockk.Answer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchListViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var repoMock: SearchListRepository
    private lateinit var prefMock: SearchListPrefManager
    private lateinit var searchListViewModel: SearchListViewModel
    private lateinit var dataFlow: MutableStateFlow<List<SearchEntityBl>>

    @Before
    fun setUp() {
        repoMock = mockk<SearchListRepository>()
        prefMock = mockk<SearchListPrefManager>()
        dataFlow = MutableStateFlow(emptyList())
        every { repoMock.getAll() } returns dataFlow
        searchListViewModel = SearchListViewModel(repoMock, prefMock)
    }

    @Test
    fun `properly stores search text in state`() = runTest {
        // GIVEN
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.searchTextFlow.collect {} }

        // WHEN
        val start = searchListViewModel.searchTextFlow.value
        searchListViewModel.setSearchText("Wimbledon")
        advanceUntilIdle()

        // THEN
        assertEquals("", start)
        assertEquals("Wimbledon", searchListViewModel.searchTextFlow.value)

        job.cancel()
    }

    @Test
    fun `properly stores filter value in state`() = runTest {
        // GIVEN
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.entityFilterFlow.collect {} }

        // WHEN
        val start = searchListViewModel.entityFilterFlow.value
        searchListViewModel.setEntityFilter(SearchFilter.COMPETITIONS)
        advanceUntilIdle()

        // THEN
        assertEquals(SearchFilter.ALL, start)
        assertEquals(SearchFilter.COMPETITIONS, searchListViewModel.entityFilterFlow.value)

        job.cancel()
    }

    @Test
    fun `properly displays loading during submission`() = runTest {
        // GIVEN
        val loadingStates = mutableListOf<Boolean>()
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.isLoadingFlow.collect { loadingStates.add(it) } }

        // WHEN
        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()

        // THEN
        assertEquals(3, loadingStates.size)
        assertArrayEquals(booleanArrayOf(false, true, false), loadingStates.toBooleanArray())

        job.cancel()
    }

    @Test
    fun `properly emits error event`() = runTest {
        // GIVEN
        var errorCount = 0
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.hasErrorFlow.collect { errorCount++ } }
        coEvery { repoMock.search(any()) } throws Exception("Network error")

        // WHEN
        val start = errorCount
        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()

        // THEN
        assertEquals(0, start)
        assertEquals(1, errorCount)

        job.cancel()
    }

    @Test
    fun `properly calls search with search text`() = runTest {
        // GIVEN
        searchListViewModel.setSearchText("Wimbledon")

        // WHEN
        searchListViewModel.onSearchButtonClicked()
        advanceUntilIdle()

        // THEN
        coVerify { repoMock.search("Wimbledon") }
    }

    @Test
    fun `properly parses data`() = runTest {
        // GIVEN
        val player = SearchEntityBl("1", "name1", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "country1", null)
        var state: Map<Sport, List<SearchListEntity>> = emptyMap()
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.dataFlow.collect { state = it } }

        // WHEN
        val start = state.isEmpty()
        dataFlow.emit(listOf(player))
        advanceUntilIdle()

        // THEN
        assertTrue(start)
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
        // GIVEN
        val multiSportData = listOf(
            SearchEntityBl("1", "name1", Gender.MEN, EntityType.COMPETITION, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("2", "name2", Gender.MEN, EntityType.TEAM, Sport.HOCKEY, "country1", null),
        )
        var state: Map<Sport, List<SearchListEntity>> = emptyMap()
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.dataFlow.collect { state = it } }

        // WHEN
        dataFlow.emit(multiSportData)
        advanceUntilIdle()

        // THEN
        val player1 = state[Sport.FOOTBALL]?.get(0)
        val player2 = state[Sport.HOCKEY]?.get(0)

        assertEquals(2, state.keys.size)
        assertEquals(multiSportData[0].id, player1?.id)
        assertEquals(multiSportData[1].id, player2?.id)

        job.cancel()
    }

    @Test
    fun `properly filters data by entity type`() = runTest {
        // GIVEN
        val singleSportData = listOf(
            SearchEntityBl("1", "name1", Gender.MEN, EntityType.COMPETITION, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("2", "name2", Gender.MEN, EntityType.TEAM, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("3", "name3", Gender.MEN, EntityType.TEAM_PLAYER, Sport.FOOTBALL, "country1", null),
            SearchEntityBl("4", "name4", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "country1", null),
        )
        var state: Map<Sport, List<SearchListEntity>> = emptyMap()
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.dataFlow.collect { state = it } }

        // WHEN
        dataFlow.emit(singleSportData)
        advanceUntilIdle()
        val all = state[Sport.FOOTBALL]

        searchListViewModel.setEntityFilter(SearchFilter.COMPETITIONS)
        advanceUntilIdle()
        val competitiona = state[Sport.FOOTBALL]

        searchListViewModel.setEntityFilter(SearchFilter.PARTICIPANTS)
        advanceUntilIdle()
        val players = state[Sport.FOOTBALL]

        // THEN
        assertEquals(4, all?.size)

        assertEquals(1, competitiona?.size)
        assertEquals("1", competitiona?.get(0)?.id)

        assertEquals(3, players?.size)
        assertEquals("2", players?.get(0)?.id)
        assertEquals("3", players?.get(1)?.id)
        assertEquals("4", players?.get(2)?.id)

        job.cancel()
    }

    @Test
    fun `properly loads search text and filter from manager`() = runTest {
        // GIVEN
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.entityFilterFlow.collect {} }
        val job2 = launch(UnconfinedTestDispatcher()) { searchListViewModel.searchTextFlow.collect {} }
        every { prefMock.restoreData() } returns Pair("text1", SearchFilter.PARTICIPANTS)

        // WHEN
        val start = searchListViewModel.entityFilterFlow.value
        searchListViewModel.loadSharedPrefsData()
        advanceUntilIdle()

        // THEN
        assertEquals(SearchFilter.ALL, start)
        assertEquals(SearchFilter.PARTICIPANTS, searchListViewModel.entityFilterFlow.value)
        assertEquals("text1", searchListViewModel.searchTextFlow.value)

        job.cancel()
        job2.cancel()
    }

    @Test
    fun `properly stores search text and filter from manager`() = runTest {
        // GIVEN
        val job = launch(UnconfinedTestDispatcher()) { searchListViewModel.entityFilterFlow.collect {} }
        val job2 = launch(UnconfinedTestDispatcher()) { searchListViewModel.searchTextFlow.collect {} }
        every { prefMock.saveData(any(), any()) } returns
        searchListViewModel.setSearchText("text1")
        searchListViewModel.setEntityFilter(SearchFilter.PARTICIPANTS)

        // WHEN
        advanceUntilIdle()
        searchListViewModel.saveSharedPrefsData()
        advanceUntilIdle()

        // THEN
       verify { prefMock.saveData("text1", SearchFilter.PARTICIPANTS) }

        job.cancel()
        job2.cancel()
    }
}
