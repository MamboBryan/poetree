package com.mambo.poetree.features.getstarted

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.helpers.MobileScreen
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
private fun ActionRows(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onFinish: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val count = pagerState.pageCount

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {

        AnimatedVisibility(
            visible = pagerState.currentPage != 0,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            OutlinedButton(
                border = BorderStroke(0.4.dp, color = MaterialTheme.colors.primary),
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(pagerState.currentPage - 1)
                    }
                }) {
                Text(text = "Back")
            }
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            pagerState = pagerState,
        )

        Button(modifier = Modifier.align(Alignment.CenterEnd), onClick = {
            scope.launch {
                if (pagerState.currentPage == count - 1)
                    onFinish.invoke()
                else
                    pagerState.scrollToPage(pagerState.currentPage + 1)
            }
        }) {
            Text(modifier = Modifier, text = "Next")
        }

    }

}

private fun NavController.navigateToLanding() {
    UserPreferences().userHasOnBoarded()
    navigate(MobileScreen.Landing.route){
        popUpTo(MobileScreen.Landing.route){ inclusive = true }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalUnitApi::class)
@Composable
fun GetStartedScreen(
    navController: NavController
) {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = PoetreeApp().name(),
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(18f, TextUnitType.Sp)
            )
            TextButton(onClick = { navController.navigateToLanding() }) {
                Text(text = "Skip")
            }
        }

        HorizontalPager(modifier = Modifier.weight(1f), count = 3, state = pagerState) { page ->
            val (title, description) = PoetreeApp().onBoardingDetails()[page]
            val id = when (page) {
                0 -> R.drawable.reader
                1 -> R.drawable.poet
                else -> R.drawable.community
            }

            OnBoardItem(id = id, title = title, description = description)

        }

        ActionRows(pagerState = pagerState) { navController.navigateToLanding() }

    }

}

@Preview
@Composable
fun GetStartedScreenPreview() {
    val context = LocalContext.current
    GetStartedScreen(navController = NavController(context = context))
}