package navigation

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: String?
) {
    object Landing : NavigationItem("landing", "Landing", null)
    object Home : NavigationItem("home", "Home", null)
}