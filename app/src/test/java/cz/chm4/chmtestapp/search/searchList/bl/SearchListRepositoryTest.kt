package cz.chm4.chmtestapp.search.searchList.bl

import cz.chm4.chmtestapp.MainCoroutineRule
import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import cz.chm4.chmtestapp.search.common.data.network.IdObject
import cz.chm4.chmtestapp.search.common.data.network.LivesportSearchApi
import cz.chm4.chmtestapp.search.common.data.network.SearchEntity
import cz.chm4.chmtestapp.search.searchList.data.database.SearchResultsDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchListRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var livesportSearchApiMock: LivesportSearchApi
    lateinit var searchResultsDaoMock: SearchResultsDao
    lateinit var searchListRepository: SearchListRepository


    @Before
    fun setUp() {
        livesportSearchApiMock = mockk()
        searchResultsDaoMock = mockk()
        searchListRepository = SearchListRepository(livesportSearchApiMock, searchResultsDaoMock)
    }

    @Test
    fun `search calls proper methods`() = runTest {
        //GIVEN
        val slot = slot<List<SearchEntityRoom>>()
        val player = SearchEntity(
            "1",
            "name1",
            IdObject(1, "MEN"),
            IdObject(1, "COMPETITION"),
            IdObject(1, "FOOTBALL"),
            IdObject(1, "country1"),
            emptyList()
        )
        coEvery { livesportSearchApiMock.search(any()) }  returns listOf(player)
        coEvery { searchResultsDaoMock.deleteAll() } returns Unit
        coEvery { searchResultsDaoMock.insert(capture(slot)) } returns Unit

        //WHEN
        searchListRepository.search("1")

        //THEN
        coVerify { livesportSearchApiMock.search("1") }
        coVerify { searchResultsDaoMock.deleteAll() }
        coVerify { searchResultsDaoMock.insert(any()) }

        val actualPlayer = slot.captured.getOrNull(0)
        assertEquals(player.id, actualPlayer?.id)
        assertEquals(player.name, actualPlayer?.name)
        assertEquals(player.type.name, actualPlayer?.type)
        assertEquals(player.sport.name, actualPlayer?.sport)
        assertEquals(player.gender.name, actualPlayer?.gender)
        assertEquals(player.defaultCountry.name,  actualPlayer?.country)
        assertNull(actualPlayer?.image)
    }

    @Test
    fun `getAll calls proper methods`() = runTest {
        //GIVEN
        coEvery { searchResultsDaoMock.getAll() }  returns flowOf(emptyList())

        //WHEN
        searchListRepository.getAll().collect {}

        //THEN
        coVerify { searchListRepository.getAll() }
    }
}

