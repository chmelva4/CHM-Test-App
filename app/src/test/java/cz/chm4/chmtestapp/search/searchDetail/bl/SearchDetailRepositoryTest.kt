package cz.chm4.chmtestapp.search.searchDetail.bl

import cz.chm4.chmtestapp.MainCoroutineRule
import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.Gender
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.Sport
import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import cz.chm4.chmtestapp.search.searchDetail.data.database.SearchDetailDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchDetailRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var searchDaoMock: SearchDetailDao
    lateinit var searchDetailRepository: SearchDetailRepository

    @Before
    fun setUp() {
        searchDaoMock = mockk()
        searchDetailRepository = SearchDetailRepository(searchDaoMock)
    }

    @Test
    fun `getSearchEntityById returns proper data`() = runTest {
        //GIVEN
        val player = SearchEntityRoom("1", "player1", "MIX", "SINGLE_PLAYER", "TENNIS", "country1", null)
        every { searchDaoMock.getSearchEntityById(any()) } returns flowOf(player)

        //WHEN
        var actualPlayer: SearchEntityBl? = null
        searchDetailRepository.getSearchEntityById("1").collect {actualPlayer = it}
        advanceUntilIdle()

        //THEN
        assertEquals(player.id, actualPlayer?.id)
        assertEquals(player.name, actualPlayer?.name)
        assertEquals(Gender.MIX, actualPlayer?.gender)
        assertEquals(EntityType.SINGLE_PLAYER, actualPlayer?.type)
        assertEquals(Sport.TENNIS, actualPlayer?.sport)
        assertEquals(player.country, actualPlayer?.country)
        assertEquals(player.image, actualPlayer?.image)
    }

    @Test
    fun `getSearchEntityById has proper call param`() = runTest {
        //GIVEN
        val player = SearchEntityRoom("1", "player1", "MIX", "SINGLE_PLAYER", "TENNIS", "country1", null)
        every { searchDaoMock.getSearchEntityById(any()) } returns flowOf(player)

        //WHEN
        searchDetailRepository.getSearchEntityById("1").collect {}
        advanceUntilIdle()

        //THEN
        verify { searchDaoMock.getSearchEntityById("1") }
    }
}