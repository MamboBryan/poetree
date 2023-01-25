package screens.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.composables.section.GetStartedSection
import com.mambo.poetree.composables.section.OnBoardingSection
import com.mambo.poetree.data.local.preferences.UserPreferences
import composables.section.AccountSection
import composables.section.AuthNavigator
import composables.section.AuthSection

private enum class Landing {
    GET_STARTED,
    AUTHENTICATION,
    SETUP
}

@Composable
fun LandingScreen() {

    val preferences = UserPreferences()

    val isSignedIn: Boolean by UserPreferences().signedIn.collectAsState(initial = false)
    val hasSetup: Boolean by UserPreferences().isUserSetup.collectAsState(initial = false)

//    val section = preferences.signedIn.combine(preferences.isUserSetup){ signedIn, setup ->
//        mutableStateOf(
//            when {
//                !signedIn -> Landing.AUTHENTICATION
//                !setup -> Landing.SETUP
//                else -> Landing.GET_STARTED
//            }
//        )
//    }.collectAsState(initial = Landing.GET_STARTED)
//
////    var section = sectionFlow

    var section by remember {
        mutableStateOf(
            when {
                !isSignedIn -> Landing.AUTHENTICATION
                !hasSetup -> Landing.SETUP
                else -> Landing.GET_STARTED
            }
        )
    }

    Row {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OnBoardingSection()
        }

        Column(
            modifier = Modifier.padding(if (section == Landing.SETUP) 0.dp else 24.dp).weight(1f),
        ) {

            if (section != Landing.SETUP)
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource("icons/logo.png"), contentDescription = null)
                    Text(text = PoetreeApp().name(), fontWeight = FontWeight.Bold)

                }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(
                    visible = section == Landing.GET_STARTED,
                ) {
                    GetStartedSection {
                        section = Landing.AUTHENTICATION
                    }
                }

                AnimatedVisibility(
                    visible = section == Landing.AUTHENTICATION,
                ) {
                    AuthSection(navigator = AuthNavigator(
                        navigateToHome = {  },
                        navigateToSetup = {
                            section = Landing.SETUP
                        }
                    ))
                }

                AnimatedVisibility(
                    visible = section == Landing.SETUP,
                ) {
                    AccountSection()
                }

            }

        }

    }

}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen()
}
