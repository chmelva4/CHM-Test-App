package cz.chm4.chmtestapp.navigation

sealed class Screens(
    val route: String
) {
    object SearchList: Screens("searchList")
    object SearchItemDetail: Screens("searchItem/{id}")
}
