package cz.chm4.chmtestapp.search.searchDetail.ui

import cz.chm4.chmtestapp.MainCoroutineRule
import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.Gender
import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.Sport
import cz.chm4.chmtestapp.search.searchDetail.bl.SearchDetailRepository
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
class SearchDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var repositoryMock: SearchDetailRepository
    lateinit var searchDetailViewModel: SearchDetailViewModel

    @Before
    fun setUp() {
        repositoryMock = mockk()
        searchDetailViewModel = SearchDetailViewModel(repositoryMock)
    }

    @Test
    fun `returns proper data`() = runTest {
        //GIVEN
        val player = SearchEntityBl("1", "player1", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "country1", null)
        every { repositoryMock.getSearchEntityById(any()) } returns flowOf(player)


        //WHEN
        var actualPlayer: SearchEntityBl? = null
        searchDetailViewModel.getSearchEntity("1").collect { actualPlayer = it}
        advanceUntilIdle()

        //THEN
        assertEquals(player, actualPlayer)
    }

    @Test
    fun `calls with proper argument`() = runTest {
        //GIVEN
        val player = SearchEntityBl("1", "player1", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "country1", null)
        every { repositoryMock.getSearchEntityById(any()) } returns flowOf(player)


        //WHEN
        searchDetailViewModel.getSearchEntity("1").collect { }
        advanceUntilIdle()

        //THEN
        verify { repositoryMock.getSearchEntityById("1") }
    }
}