package navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

class NavController constructor(
    private val startDestination: String,
    private var backStackScreens: MutableSet<String> = mutableSetOf()
) {

    // Variable to store the state of the current screen
    private var _currentDestination: MutableState<String> = mutableStateOf(startDestination)
    val currentDestination: State<String> get() = _currentDestination

    // Function to handle the navigation between the screen
    fun navigate(route: String) {
        if (route != _currentDestination.value) {
            if (backStackScreens.contains(_currentDestination.value) && _currentDestination.value != startDestination) {
                backStackScreens.remove(_currentDestination.value)
            }

            if (route == startDestination) {
                backStackScreens = mutableSetOf()
            } else {
                backStackScreens.add(_currentDestination.value)
            }

            _currentDestination.value = route
        }
    }

    // Function to handle the back
    fun navigateUp() {
        if (backStackScreens.isNotEmpty()) {
            _currentDestination.value = backStackScreens.last()
            backStackScreens.remove(_currentDestination.value)
        }
    }
}

/**
 * Composable to remember the state of the navcontroller
 */
@Composable
fun rememberNavigator(
    calculation: @DisallowComposableCalls () -> MutableState<NavController>
): MutableState<NavController> = currentComposer.cache(false, calculation)

@Composable
fun rememberNavController(
    startDestination: String,
    backStackScreens: MutableSet<String> = mutableSetOf()
): MutableState<NavController> = rememberSaveable {
    println("-".repeat(10).plus("> NAVIGATOR REMEMBER => $startDestination"))
    mutableStateOf(NavController(startDestination, backStackScreens))
}